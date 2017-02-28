/* nfa.h --- Header file for the NFA implementation
 */

#ifndef NFA_H
#define NFA_H

#include <stdio.h>

/* Description
 *
 * An NFA is a structure with two links: one to its start state and the other
 * to its unique accepting state. (Each NFA has exactly one accepting state,
 * which could coincide with its start state.)  The NFA struct also contains
 * a sequence number (unsigned integer) and a string used for printing
 * purposes.
 *
 * Each state is itself a structure that includes any number of epsilon
 * transitions (epsilon moves) but AT MOST ONE non-epsilon transition out of
 * the state.  If the state is accepting, then it includes a pointer to the
 * NFA containing it (the accepting state is said to be BOUND to the NFA).
 * An accepting state can only be bound to one NFA at a time.  Rejecting
 * states are not bound to any NFA.

/* A state.
 *
 * struct state is declared in the file nfa.c
 */
typedef struct state STATE_REC, * STATE;

/* A set of states (currently implemented as a simple linked list).
 *
 * struct ss is declared in the file nfa.c
 */
typedef struct ss STATE_SET_NODE, *STATE_SET;

/* An NFA.
 *
 * struct nfa is declared in the file nfa.c
 */
typedef struct nfa NFA_REC, *NFA;

/* A list of NFAs.
 *
 * struct nfal is declared in the file nfa.c
 */
typedef struct nfal NFA_LIST_NODE, *NFA_LIST;

// Prototypes

/* new_state() returns a new dynamically allocated state with no transitions
 * and that is not bound to any NFA.
 * A state can have any number of epsilon transitions but at most one
 * non-epsilon transition leaving it. To make transitions, see below.
 */
STATE new_state();

/* make_transition() produces a transition on letter from the src state to the
 * dest state. This is not an epsilon transition. Any previous non-epsilon
 * transition from the src state is overwritten.
 */
void make_transition(STATE src, int letter, const STATE dest);

/* add_epsilon_move() adds an epsilon transition from src to dest.
 */
void add_epsilon_move(STATE src, const STATE dest);

/* new_nfa() returns a newly allocated NFA with the given start state and
 * accept state (an NFA must have exactly one of each), as well as the given
 * sequence number and regex string.
 * This function also binds the given accepting state with the new NFA. (Only
 * the accepting state is bound to the NFA; the rejecting states are unbound.)
 * The sequence number can be any unsigned integer, and it is used to sort
 * the NFAs in an NFA_LIST. It typically matches the line number of the
 * corresponding regular expression in the input file.
 * The regex string can be any non-volatile string but is typically the
 * regular expression from which the NFA was built. It is used for printing
 * purposes only and does not affect the execution of the NFA.
 */
NFA new_nfa(unsigned int sequence_num, char * regex,
            const STATE start_state, STATE accept_state);

/* get_start_state() returns the start state of the given NFA.
 */
STATE get_start_state(const NFA nfa);

/* get_accept_state() returns the (unique) accepting state of the given NFA.
 */
STATE get_accept_state(const NFA nfa);

/* get_regex() returns the regex string of the given NFA.
 */
char * get_regex(const NFA nfa);

/* set_regex() sets the regex string of the given NFA to the given regex.
 * The old regex string is de-allocated.
 */
void set_regex(NFA nfa, char * regex);

/* free_nfa() de-allocates the given NFA. No states are destroyed, but the
 * NFA's accepting state is unbound from the NFA, effectively making it a
 * rejecting state.
 */
void free_nfa(NFA nfa);

/* add_nfa() addes the given NFA to the given NFA_LIST. The NFAs in the list
 * are maintained in ascending order by sequence number.
 */
void add_nfa(NFA_LIST *nfal, const NFA nfa);

/* free_state_set() de-allocates the given set of states.
 */
void free_state_set(STATE_SET ss);

/* execute_init() returns the union of the epsilon-closures of the start states
 * of all the NFAs in the given NFA_LIST. It is used to initialize the
 * set-of-states simulation of the NFAs.
 */
STATE_SET execute_init(NFA_LIST nfas);

/* execute_transition() takes a state set ss and a symbol c and returns the
 * state set obtained from ss by transition on c. The state set ss is
 * destroyed in the process.
 */
STATE_SET execute_transition(STATE_SET ss, int c);

/* print_matches() takes a state set ss, a character count, an output stream,
 * and a format string as parameters. For each state in ss that is accepting,
 * it prints (to the stream out) the sequence number and regex string of the
 * NFA to which the accepting state is bound. The printing is done via a call
 * to fprintf(out, format_str, sequence_num, regex), where sequence_num and
 * regex are respectively the sequence number and regex string of the NFA.
 * The format string should thus have slots "%d" (or "%u") and "%s" in that
 * order, with no other slots. A newline is appending to the end of the
 * printed string.
 */
void print_matches(STATE_SET ss, int char_count,
                   FILE *out, const char *format_str);

#endif
