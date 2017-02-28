/* Baby lex */

/* parse.y
 *
 * Grammer rules for bison.
 * Includes the lexical analyzer routine yylex().
 */


%{
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <assert.h>
#include "nfa.h"
#define YYDEBUG 1

NFA_LIST the_nfa_list = NULL;
unsigned int seq_num = 1;

int yylex();
char * char_to_string(int c);
int yyerror(char *s);
NFA build_or_nfa(NFA nfa1, NFA nfa2);
NFA build_concat_nfa(NFA first, NFA last);
NFA build_star_nfa(NFA nfa);
NFA build_basic_nfa(int c);
char * concat(const char * str1, const char * str2);
char * char_to_string(int c);
%}

%union {
    int    yyint;
    char * yystr;
    NFA    yynfa;
}

%type  <yynfa> regex concat_regex star_regex primary_regex
%token <yyint> SYMBOL
%token <yystr> STRING_LITERAL
%token EMPTY_STRING

%%

session
    : eval
    ;

eval
    : eval line
    | /* empty */
    ;

line
    : regex '\n'               { add_nfa(&the_nfa_list,$1); seq_num++; }
    ;

regex
    : regex '|' concat_regex   { $$ = build_or_nfa($1,$3); }
    | concat_regex
    ;

concat_regex
    : concat_regex star_regex  { $$ = build_concat_nfa($1,$2); }
    | star_regex
    ;

star_regex
    : star_regex '*'           { $$ = build_star_nfa($1); }
    | primary_regex
    ;

primary_regex
	: '(' regex ')'      		{ set_regex($2,concat("(",concat(get_regex($2),")")));
                                 $$ = $2; }
    | STRING_LITERAL		    
    | EMPTY_STRING             { $$ = build_basic_nfa('\0'); }
    | SYMBOL                   { $$ = build_basic_nfa($1); }
    ;

%%

// Build and return the disjunction (OR) of two given NFAs
NFA build_or_nfa(NFA nfa1, NFA nfa2)
{
    STATE new_start = new_state();
    STATE new_accept = new_state();
    char * regex = concat(get_regex(nfa1),concat("|",get_regex(nfa2)));
    add_epsilon_move(new_start, get_start_state(nfa1));
    add_epsilon_move(new_start, get_start_state(nfa2));
    add_epsilon_move(get_accept_state(nfa1), new_accept);
    add_epsilon_move(get_accept_state(nfa2), new_accept);
    free_nfa(nfa1);
    free_nfa(nfa2);
    return new_nfa(seq_num, regex, new_start, new_accept);
}

// Build and return the concatenation of two NFAs
NFA build_concat_nfa(NFA first, NFA last)
{
    STATE start = get_start_state(first);
    STATE accept = get_accept_state(last);
    char * regex = concat(get_regex(first),get_regex(last));
    add_epsilon_move(get_accept_state(first), get_start_state(last));
    free_nfa(first);
    free_nfa(last);
    return new_nfa(seq_num, regex, start, accept);
}

// Build and return the Kleene *-closure of an NFA
NFA build_star_nfa(NFA nfa)
{
    STATE old_start = get_start_state(nfa);
    STATE new_start = new_state();
    STATE old_accept = get_accept_state(nfa);
    char * regex = concat(get_regex(nfa),"*");
    free_nfa(nfa);
    add_epsilon_move(new_start, old_start);
    add_epsilon_move(old_accept, new_start);
    return new_nfa(seq_num, regex, new_start, new_start);
}

// Build and return an NFA that accepts a single symbol c
// (or the empty string if c=='\0')
NFA build_basic_nfa(int c)
{
    STATE start = new_state();
    STATE accept;

    if (c == '\0') // want to return a one-state NFA for the empty string
        return new_nfa(seq_num, "\"\"", start, start);

        // else, build an NFA to accept a single symbol
    accept = new_state();
    make_transition(start, c, accept);
    return new_nfa(seq_num, char_to_string(c), start, accept);
}

// Called if there is a syntax error
int yyerror(char *s)
{
    fprintf(stderr, "%s\n", s);
    return 0;
}

// Return the concatenation of two strings
char * concat(const char * str1, const char * str2)
{
    int l1 = strlen(str1);
    int l2 = strlen(str2);
    char * ret = (char *)malloc(l1+l2+1);
    assert(ret != NULL);
    strcpy(ret, str1);
    strcat(ret, str2);
    return ret;
}

// Return a C-style string constant (without quotes) for the given character
char * char_to_string(int c)
{
    char * ret;

        // Characters that require a preceding backslash to be taken literally
    if (c=='\n'||c=='\r'||c=='\t'||c=='\\'||c=='*'||c=='|'||c=='('||c==')') {
        ret = (char *)malloc(3);
	assert(ret != NULL);
	ret[0] = '\\';
	ret[1] = (c=='\n'?'n':c=='\r'?'r':c=='\t'?'t':c);
	ret[2] = '\0';
    }
    else { // Everything else
        ret = (char *)malloc(2);
        assert(ret != NULL);
        ret[0] = c;
        ret[1] = '\0';
    }
    return ret;
}
