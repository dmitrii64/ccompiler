%{
  import java.io.*;
  import edu.eltech.moevm.*;
  import edu.eltech.moevm.common.*;
  import edu.eltech.moevm.parsing_tree.*;
%}
      
%token IDENTIFIER CONSTANT STRING_LITERAL SIZEOF
%token INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP

%token STATIC
%token CHAR SHORT INT LONG FLOAT DOUBLE VOID COMPLEX BOOL

%token RE IM MOD PRINT

%token IF ELSE WHILE DO FOR GOTO BREAK RETURN

%token SEMICOLON BRACELEFT BRACERIGHT COMMA COLON EQUAL RBLEFT RBRIGHT BRACKETLEFT
%token BRACKETRIGHT DOT AMP EXCL MINUS PLUS STAR SLASH PERCENT LESS GREATER
%token CARET BAR QUESTION NUMBER_SIGN


%start root
%%

primary_expression
	: IDENTIFIER                { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, (PTLeaf)$1.obj)); }
	| CONSTANT                  { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, (PTLeaf)$1.obj)); }
	| STRING_LITERAL            { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, (PTLeaf)$1.obj)); }
	| RBLEFT expression RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	;

postfix_expression
	: primary_expression                                         { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj)); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| postfix_expression RBLEFT RBRIGHT                          { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| postfix_expression INC_OP                                  { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj)); }
	| postfix_expression DEC_OP                                  { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj)); }
	;

argument_expression_list
	: assignment_expression                                { $$ = new ParserVal(new PTNode(Nonterminals.ARGUMENT_EXPRESSION_LIST, (PTElement)$1.obj)); }
	| argument_expression_list COMMA assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.ARGUMENT_EXPRESSION_LIST, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

unary_expression
	: postfix_expression                   { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTElement)$1.obj)); }
	| INC_OP unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj)); }
	| DEC_OP unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj)); }
	| MINUS cast_expression                { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj)); }
	| EXCL cast_expression                 { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj)); }
	| SIZEOF unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj)); }
	| SIZEOF RBLEFT type_specifier RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| RE RBLEFT CONSTANT RBRIGHT           { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| RE RBLEFT IDENTIFIER RBRIGHT         { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| IM RBLEFT CONSTANT RBRIGHT           { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| IM RBLEFT IDENTIFIER RBRIGHT         { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| MOD RBLEFT CONSTANT RBRIGHT          { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| MOD RBLEFT IDENTIFIER RBRIGHT        { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| PRINT RBLEFT STRING_LITERAL RBRIGHT  { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	| PRINT RBLEFT IDENTIFIER RBRIGHT      { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	;

cast_expression
	: unary_expression                              { $$ = new ParserVal(new PTNode(Nonterminals.CAST_EXPRESSION, (PTElement)$1.obj)); }
	| RBLEFT type_specifier RBRIGHT cast_expression { $$ = new ParserVal(new PTNode(Nonterminals.CAST_EXPRESSION, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj, (PTElement)$4.obj)); }
	;

multiplicative_expression
	: cast_expression                                   { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj)); }
	| multiplicative_expression STAR cast_expression    { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| multiplicative_expression SLASH cast_expression   { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| multiplicative_expression PERCENT cast_expression { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

additive_expression
	: multiplicative_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj)); }
	| additive_expression PLUS multiplicative_expression  { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| additive_expression MINUS multiplicative_expression { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

shift_expression
	: additive_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj)); }
	| shift_expression LEFT_OP additive_expression  { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| shift_expression RIGHT_OP additive_expression { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

relational_expression
	: shift_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj)); }
	| relational_expression LESS shift_expression    { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| relational_expression GREATER shift_expression { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| relational_expression LE_OP shift_expression   { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| relational_expression GE_OP shift_expression   { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

equality_expression
	: relational_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj)); }
	| equality_expression EQ_OP relational_expression { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	| equality_expression NE_OP relational_expression { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

and_expression
	: equality_expression                    { $$ = new ParserVal(new PTNode(Nonterminals.AND_EXPRESSION, (PTElement)$1.obj)); }
	| and_expression AMP equality_expression { $$ = new ParserVal(new PTNode(Nonterminals.AND_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

exclusive_or_expression
	: and_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.EXCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj)); }
	| exclusive_or_expression CARET and_expression { $$ = new ParserVal(new PTNode(Nonterminals.EXCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { $$ = new ParserVal(new PTNode(Nonterminals.INCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj)); }
	| inclusive_or_expression BAR exclusive_or_expression { $$ = new ParserVal(new PTNode(Nonterminals.INCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

logical_and_expression
	: inclusive_or_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_AND_EXPRESSION, (PTElement)$1.obj)); }
	| logical_and_expression AND_OP inclusive_or_expression { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_AND_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

logical_or_expression
	: logical_and_expression                             { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_OR_EXPRESSION, (PTElement)$1.obj)); }
	| logical_or_expression OR_OP logical_and_expression { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_OR_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

conditional_expression
	: logical_or_expression                                                  { $$ = new ParserVal(new PTNode(Nonterminals.CONDITIONAL_EXPRESSION, (PTElement)$1.obj)); }
	| logical_or_expression QUESTION expression COLON conditional_expression { $$ = new ParserVal(new PTNode(Nonterminals.CONDITIONAL_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj, (PTElement)$5.obj)); }
	;

assignment_expression
	: conditional_expression                         { $$ = new ParserVal(new PTNode(Nonterminals.ASSIGNMENT_EXPRESSION, (PTElement)$1.obj)); }
	| postfix_expression EQUAL assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.ASSIGNMENT_EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

expression
	: assignment_expression                  { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION, (PTElement)$1.obj)); }
	| expression COMMA assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

constant_expression
	: conditional_expression { $$ = new ParserVal(new PTNode(Nonterminals.CONSTANT_EXPRESSION, (PTElement)$1.obj)); }
	;

declaration
	: declaration_specifiers SEMICOLON                      { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION, (PTElement)$1.obj, (PTLeaf)$2.obj)); }
	| declaration_specifiers init_declarator_list SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	;

declaration_specifiers
	: type_specifier                        { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_SPECIFIERS, (PTElement)$1.obj)); }
	| type_specifier declaration_specifiers { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_SPECIFIERS, (PTElement)$1.obj, (PTElement)$2.obj)); }
	;

init_declarator_list
	: init_declarator                            { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR_LIST, (PTElement)$1.obj)); }
	| init_declarator_list COMMA init_declarator { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR_LIST, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

init_declarator
	: direct_declarator                   { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR, (PTElement)$1.obj)); }
	| direct_declarator EQUAL initializer { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

type_specifier
	: VOID    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| COMPLEX { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| CHAR    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| SHORT   { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| INT     { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| LONG    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| FLOAT   { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| DOUBLE  { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	| BOOL    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, (PTLeaf)$1.obj)); }
	;

direct_declarator
	: IDENTIFIER                                                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTLeaf)$1.obj)); }
	| RBLEFT direct_declarator RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	| direct_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| direct_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	;

parameter_list
	: parameter_declaration                      { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_LIST, (PTElement)$1.obj)); }
	| parameter_list COMMA parameter_declaration { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_LIST, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

parameter_declaration
	: declaration_specifiers direct_declarator   { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj)); }
	| declaration_specifiers abstract_declarator { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj)); }
	| declaration_specifiers                     { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj)); }
	;

identifier_list
	: IDENTIFIER                       { $$ = new ParserVal(new PTNode(Nonterminals.IDENTIFIER_LIST, (PTLeaf)$1.obj)); }
	| identifier_list COMMA IDENTIFIER { $$ = new ParserVal(new PTNode(Nonterminals.IDENTIFIER_LIST, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	;

abstract_declarator
	: direct_abstract_declarator { $$ = new ParserVal(new PTNode(Nonterminals.ABSTRACT_DECLARATOR, (PTElement)$1.obj)); }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| BRACKETLEFT BRACKETRIGHT                                                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTLeaf)$1.obj, (PTLeaf)$2.obj)); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	| RBLEFT RBRIGHT                                                          { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTLeaf)$1.obj, (PTLeaf)$2.obj)); }
	| RBLEFT parameter_list RBRIGHT                                           { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	| direct_abstract_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	;

initializer
	: assignment_expression                       { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, (PTElement)$1.obj)); }
	| BRACELEFT initializer_list BRACERIGHT       { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj)); }
	;

initializer_list
	: initializer                        { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER_LIST, (PTElement)$1.obj)); }
	| initializer_list COMMA initializer { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER_LIST, (PTElement)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

statement
	: labeled_statement    { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	| compound_statement   { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	| expression_statement { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	| selection_statement  { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	| iteration_statement  { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	| jump_statement       { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT, (PTElement)$1.obj)); }
	;

labeled_statement
	: IDENTIFIER NUMBER_SIGN statement { $$ = new ParserVal(new PTNode(Nonterminals.LABELED_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj)); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj)); }
	| BRACELEFT statement_list BRACERIGHT                  { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| BRACELEFT declaration_list BRACERIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj)); }
	;

declaration_list
	: declaration                  { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_LIST, (PTElement)$1.obj)); }
	| declaration_list declaration { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_LIST, (PTElement)$1.obj, (PTElement)$2.obj)); }
	;

statement_list
	: statement                { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT_LIST, (PTElement)$1.obj)); }
	| statement_list statement { $$ = new ParserVal(new PTNode(Nonterminals.STATEMENT_LIST, (PTElement)$1.obj, (PTElement)$2.obj)); }
	;

expression_statement
	: SEMICOLON            { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION_STATEMENT, (PTLeaf)$1.obj)); }
	| expression SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION_STATEMENT, (PTElement)$1.obj, (PTLeaf)$2.obj)); }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { $$ = new ParserVal(new PTNode(Nonterminals.SELECTION_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj, (PTElement)$5.obj, (PTLeaf)$6.obj, (PTElement)$7.obj)); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTLeaf)$4.obj, (PTElement)$5.obj)); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj, (PTLeaf)$4.obj, (PTElement)$5.obj, (PTLeaf)$6.obj, (PTLeaf)$7.obj)); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTElement)$3.obj, (PTElement)$4.obj, (PTElement)$5.obj, (PTLeaf)$6.obj, (PTElement)$7.obj)); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj, (PTLeaf)$3.obj)); }
	| BREAK SEMICOLON             { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj)); }
	| RETURN SEMICOLON            { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, (PTLeaf)$1.obj, (PTLeaf)$2.obj)); }
	| RETURN expression SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, (PTLeaf)$1.obj, (PTElement)$2.obj, (PTLeaf)$3.obj)); }
	;

root
	: translation_unit { $$ = new ParserVal(new ParsingTree(new PTNode(Nonterminals.ROOT, (PTElement)$1.obj))); }
	;

translation_unit
	: external_declaration                  { $$ = new ParserVal(new PTNode(Nonterminals.TRANSLATION_UNIT, (PTElement)$1.obj)); }
	| translation_unit external_declaration { $$ = new ParserVal(new PTNode(Nonterminals.TRANSLATION_UNIT, (PTElement)$1.obj, (PTElement)$2.obj)); }
	;

external_declaration
	: function_definition { $$ = new ParserVal(new PTNode(Nonterminals.EXTERNAL_DECLARATION, (PTElement)$1.obj)); }
	| declaration         { $$ = new ParserVal(new PTNode(Nonterminals.EXTERNAL_DECLARATION, (PTElement)$1.obj)); }
	;

function_definition
	: declaration_specifiers direct_declarator compound_statement { $$ = new ParserVal(new PTNode(Nonterminals.FUNCTION_DEFINITION, (PTElement)$1.obj, (PTElement)$2.obj, (PTElement)$3.obj)); }
	;

%%

  private Yylex lexer;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }

  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  public static String getTokenName(short c)  throws TokenNotFoundException
  {
      String val = "ERROR";
      if(c<Parser.yyname.length)
        val =  Parser.yyname[c];
      else
        throw new TokenNotFoundException();
      return val;
  }

  public static ParserVal ParseFile(String file) throws IOException {
    System.out.println("==========Lexical analyzer output=========");
	Parser yyparser;
    yyparser = new Parser(new FileReader(file));
    //Tokenize input file (for debug)
	Yylex lexer = new Yylex(new FileReader(file));
	    int i = 1;
          while(i>0)
          {
              i = lexer.yylex();
              System.out.print(Parser.yyname[i]);
              System.out.print(" ");
          }
	System.out.println();
    System.out.println("================Syntax tree===============");

  	//yyparser.yydebug = true;
  	yyparser.yyparse(); //Parsing goes here
  	return yyparser.yyval;
  }