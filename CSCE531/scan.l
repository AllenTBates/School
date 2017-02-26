/* Lexical scanner for baby lex */

%{
#include <stdlib.h>
#include <assert.h>
#include "defs.h"
#include "nfa.h"
#include "parse.tab.h"
%}

    int yywrap();

metachar        [*|?+()\n]
string_literal  \"[^"]\"
empty_string    \"\"
special_char    \\[nrt]
ascii_code      \\[1-9][0-9]*
literal_char    \\.

%%

{metachar}      { return yytext[0]; }

{string_literal}{ return 

{empty_string}  { return EMPTY_STRING; }

{special_char}  { switch (yytext[1]) {
                    case 'n':
                      yylval.yyint = '\n';
                      break;
                    case 'r':
                      yylval.yyint = '\r';
                      break;
                    case 't':
                      yylval.yyint = '\t';
                      break;
                    default:
                      assert(FALSE);
                  }
                  return SYMBOL;
                }

{ascii_code}    { yylval.yyint = atoi(yytext+1);
                  return SYMBOL;
                }

{literal_char}  { yylval.yyint = yytext[1];
                  return SYMBOL;
                }

.               { yylval.yyint = yytext[0];
                  return SYMBOL;
                }

%%

char* strip_quotes(char* str) {
	return ;
}

int yywrap()
{
    return 1;
}
