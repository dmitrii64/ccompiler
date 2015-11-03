%{
  import java.io.*;
  import edu.eltech.moevm.*;
  import edu.eltech.moevm.parsing_tree.*;
%}
      
%token IDENTIFIER CONSTANT STRING_LITERAL SIZEOF
%token INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP

%token STATIC
%token CHAR SHORT INT LONG FLOAT DOUBLE VOID COMPLEX BOOL

%token RE IM MOD

%token IF ELSE WHILE DO FOR GOTO BREAK RETURN

%token SEMICOLON BRACELEFT BRACERIGHT COMMA COLON EQUAL RBLEFT RBRIGHT BRACKETLEFT
%token BRACKETRIGHT DOT AMP EXCL MINUS PLUS STAR SLASH PERCENT LESS GREATER
%token CARET BAR QUESTION NUMBER_SIGN


%start root
%%

primary_expression
	: IDENTIFIER                { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, new PTLeaf(Parser.IDENTIFIER, $1.sval))); }
	| CONSTANT                  { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, new PTLeaf(Parser.CONSTANT, $1.sval))); }
	| STRING_LITERAL            { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, new PTLeaf(Parser.STRING_LITERAL, $1.sval))); }
	| RBLEFT expression RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.PRIMARY_EXPRESSION, new PTLeaf(Parser.RBLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	;

postfix_expression
	: primary_expression                                         { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj)); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.BRACKETLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| postfix_expression RBLEFT RBRIGHT                          { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.RBRIGHT, null))); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| postfix_expression INC_OP                                  { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.INC_OP, null))); }
	| postfix_expression DEC_OP                                  { $$ = new ParserVal(new PTNode(Nonterminals.POSTFIX_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.DEC_OP, null))); }
	;

argument_expression_list
	: assignment_expression                                { $$ = new ParserVal(new PTNode(Nonterminals.ARGUMENT_EXPRESSION_LIST, (PTElement)$1.obj)); }
	| argument_expression_list COMMA assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.ARGUMENT_EXPRESSION_LIST, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), (PTElement)$3.obj)); }
	;

unary_expression
	: postfix_expression                   { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, (PTElement)$1.obj)); }
	| INC_OP unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.INC_OP, null), (PTElement)$2.obj)); }
	| DEC_OP unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.DEC_OP, null), (PTElement)$2.obj)); }
	| MINUS  cast_expression               { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.MINUS, null), (PTElement)$2.obj)); }
	| EXCL  cast_expression                { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.EXCL, null), (PTElement)$2.obj)); }
	| SIZEOF unary_expression              { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.SIZEOF, null), (PTElement)$2.obj)); }
	| SIZEOF RBLEFT type_specifier RBRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.SIZEOF, null), new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| RE RBLEFT CONSTANT RBRIGHT           { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.RE, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.CONSTANT, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	| RE RBLEFT IDENTIFIER RBRIGHT         { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.RE, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.IDENTIFIER, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	| IM RBLEFT CONSTANT RBRIGHT           { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.IM, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.CONSTANT, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	| IM RBLEFT IDENTIFIER RBRIGHT         { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.IM, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.IDENTIFIER, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	| MOD RBLEFT CONSTANT RBRIGHT          { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.MOD, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.CONSTANT, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	| MOD RBLEFT IDENTIFIER RBRIGHT        { $$ = new ParserVal(new PTNode(Nonterminals.UNARY_EXPRESSION, new PTLeaf(Parser.MOD, null), new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.IDENTIFIER, $3.sval), new PTLeaf(Parser.RBRIGHT, null))); }
	;

cast_expression
	: unary_expression                              { $$ = new ParserVal(new PTNode(Nonterminals.CAST_EXPRESSION, (PTElement)$1.obj)); }
	| RBLEFT type_specifier RBRIGHT cast_expression { $$ = new ParserVal(new PTNode(Nonterminals.CAST_EXPRESSION, new PTLeaf(Parser.RBLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.RBRIGHT, null), (PTElement)$4.obj)); }
	;

multiplicative_expression
	: cast_expression                                   { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj)); }
	| multiplicative_expression STAR cast_expression    { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.STAR, null), (PTElement)$3.obj)); }
	| multiplicative_expression SLASH cast_expression   { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.SLASH, null), (PTElement)$3.obj)); }
	| multiplicative_expression PERCENT cast_expression { $$ = new ParserVal(new PTNode(Nonterminals.MULTIPLICATIVE_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.PERCENT, null), (PTElement)$3.obj)); }
	;

additive_expression
	: multiplicative_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj)); }
	| additive_expression PLUS multiplicative_expression  { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.PLUS, null), (PTElement)$3.obj)); }
	| additive_expression MINUS multiplicative_expression { $$ = new ParserVal(new PTNode(Nonterminals.ADDITIVE_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.MINUS, null), (PTElement)$3.obj)); }
	;

shift_expression
	: additive_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj)); }
	| shift_expression LEFT_OP additive_expression  { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.LEFT_OP, null), (PTElement)$3.obj)); }
	| shift_expression RIGHT_OP additive_expression { $$ = new ParserVal(new PTNode(Nonterminals.SHIFT_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.RIGHT_OP, null), (PTElement)$3.obj)); }
	;

relational_expression
	: shift_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj)); }
	| relational_expression LESS shift_expression    { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.LESS, null), (PTElement)$3.obj)); }
	| relational_expression GREATER shift_expression { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.GREATER, null), (PTElement)$3.obj)); }
	| relational_expression LE_OP shift_expression   { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.LE_OP, null), (PTElement)$3.obj)); }
	| relational_expression GE_OP shift_expression   { $$ = new ParserVal(new PTNode(Nonterminals.RELATIONAL_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.GE_OP, null), (PTElement)$3.obj)); }
	;

equality_expression
	: relational_expression                           { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj)); }
	| equality_expression EQ_OP relational_expression { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.EQ_OP, null), (PTElement)$3.obj)); }
	| equality_expression NE_OP relational_expression { $$ = new ParserVal(new PTNode(Nonterminals.EQUALITY_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.NE_OP, null), (PTElement)$3.obj)); }
	;

and_expression
	: equality_expression                    { $$ = new ParserVal(new PTNode(Nonterminals.AND_EXPRESSION, (PTElement)$1.obj)); }
	| and_expression AMP equality_expression { $$ = new ParserVal(new PTNode(Nonterminals.AND_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.AMP, null), (PTElement)$3.obj)); }
	;

exclusive_or_expression
	: and_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.EXCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj)); }
	| exclusive_or_expression CARET and_expression { $$ = new ParserVal(new PTNode(Nonterminals.EXCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.CARET, null), (PTElement)$3.obj)); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { $$ = new ParserVal(new PTNode(Nonterminals.INCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj)); }
	| inclusive_or_expression BAR exclusive_or_expression { $$ = new ParserVal(new PTNode(Nonterminals.INCLUSIVE_OR_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.BAR, null), (PTElement)$3.obj)); }
	;

logical_and_expression
	: inclusive_or_expression                               { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_AND_EXPRESSION, (PTElement)$1.obj)); }
	| logical_and_expression AND_OP inclusive_or_expression { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_AND_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.AND_OP, null), (PTElement)$3.obj)); }
	;

logical_or_expression
	: logical_and_expression                             { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_OR_EXPRESSION, (PTElement)$1.obj)); }
	| logical_or_expression OR_OP logical_and_expression { $$ = new ParserVal(new PTNode(Nonterminals.LOGICAL_OR_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.OR_OP, null), (PTElement)$3.obj)); }
	;

conditional_expression
	: logical_or_expression                                                  { $$ = new ParserVal(new PTNode(Nonterminals.CONDITIONAL_EXPRESSION, (PTElement)$1.obj)); }
	| logical_or_expression QUESTION expression COLON conditional_expression { $$ = new ParserVal(new PTNode(Nonterminals.CONDITIONAL_EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.QUESTION, null), (PTElement)$3.obj, new PTLeaf(Parser.COLON, null), (PTElement)$5.obj)); }
	;

assignment_expression
	: conditional_expression                 { $$ = new ParserVal(new PTNode(Nonterminals.ASSIGNMENT_EXPRESSION, (PTElement)$1.obj)); }
	| IDENTIFIER EQUAL assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.ASSIGNMENT_EXPRESSION, new PTLeaf(Parser.IDENTIFIER, $1.sval), new PTLeaf(Parser.EQUAL, null), (PTElement)$3.obj)); }
	;

expression
	: assignment_expression                  { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION, (PTElement)$1.obj)); }
	| expression COMMA assignment_expression { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), (PTElement)$3.obj)); }
	;

constant_expression
	: conditional_expression { $$ = new ParserVal(new PTNode(Nonterminals.CONSTANT_EXPRESSION, (PTElement)$1.obj)); }
	;

declaration
	: declaration_specifiers SEMICOLON                      { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION, (PTElement)$1.obj, new PTLeaf(Parser.SEMICOLON, null))); }
	| declaration_specifiers init_declarator_list SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj, new PTLeaf(Parser.SEMICOLON, null))); }
	;

declaration_specifiers
	: type_specifier                        { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_SPECIFIERS, (PTElement)$1.obj)); }
	| type_specifier declaration_specifiers { $$ = new ParserVal(new PTNode(Nonterminals.DECLARATION_SPECIFIERS, (PTElement)$1.obj, (PTElement)$2.obj)); }
	;

init_declarator_list
	: init_declarator                            { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR_LIST, (PTElement)$1.obj)); }
	| init_declarator_list COMMA init_declarator { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR_LIST, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), (PTElement)$3.obj)); }
	;

init_declarator
	: direct_declarator                   { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR, (PTElement)$1.obj)); }
	| direct_declarator EQUAL initializer { $$ = new ParserVal(new PTNode(Nonterminals.INIT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.EQUAL, null), (PTElement)$3.obj)); }
	;

type_specifier
	: VOID    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.VOID, null))); }
	| COMPLEX { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.COMPLEX, null))); }
	| CHAR    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.CHAR, null))); }
	| SHORT   { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.SHORT, null))); }
	| INT     { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.INT, null))); }
	| LONG    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.LONG, null))); }
	| FLOAT   { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.FLOAT, null))); }
	| DOUBLE  { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.DOUBLE, null))); }
	| BOOL    { $$ = new ParserVal(new PTNode(Nonterminals.TYPE_SPECIFIER, new PTLeaf(Parser.BOOL, null))); }
	;

direct_declarator
	: IDENTIFIER                                                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, new PTLeaf(Parser.IDENTIFIER, $1.sval))); }
	| RBLEFT direct_declarator RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, new PTLeaf(Parser.RBLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.BRACKETLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.BRACKETLEFT, null), new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| direct_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| direct_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.RBRIGHT, null))); }
	;

parameter_list
	: parameter_declaration                      { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_LIST, (PTElement)$1.obj)); }
	| parameter_list COMMA parameter_declaration { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_LIST, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), (PTElement)$3.obj)); }
	;

parameter_declaration
	: declaration_specifiers direct_declarator   { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj)); }
	| declaration_specifiers abstract_declarator { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj, (PTElement)$2.obj)); }
	| declaration_specifiers                     { $$ = new ParserVal(new PTNode(Nonterminals.PARAMETER_DECLARATION, (PTElement)$1.obj)); }
	;

identifier_list
	: IDENTIFIER                       { $$ = new ParserVal(new PTNode(Nonterminals.IDENTIFIER_LIST, new PTLeaf(Parser.IDENTIFIER, $1.sval))); }
	| identifier_list COMMA IDENTIFIER { $$ = new ParserVal(new PTNode(Nonterminals.IDENTIFIER_LIST, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), new PTLeaf(Parser.IDENTIFIER, $3.sval))); }
	;

abstract_declarator
	: direct_abstract_declarator { $$ = new ParserVal(new PTNode(Nonterminals.ABSTRACT_DECLARATOR, (PTElement)$1.obj)); }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, new PTLeaf(Parser.RBLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| BRACKETLEFT BRACKETRIGHT                                                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, new PTLeaf(Parser.BRACKETLEFT, null), new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, new PTLeaf(Parser.BRACKETLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.BRACKETLEFT, null), new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.BRACKETLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.BRACKETRIGHT, null))); }
	| RBLEFT RBRIGHT                                                          { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.RBRIGHT, null))); }
	| RBLEFT parameter_list RBRIGHT                                           { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, new PTLeaf(Parser.RBLEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), new PTLeaf(Parser.RBRIGHT, null))); }
	| direct_abstract_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.DIRECT_ABSTRACT_DECLARATOR, (PTElement)$1.obj, new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null))); }
	;

initializer
	: assignment_expression                       { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, (PTElement)$1.obj)); }
	| BRACELEFT initializer_list BRACERIGHT       { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, new PTLeaf(Parser.BRACELEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.BRACERIGHT, null))); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER, new PTLeaf(Parser.BRACELEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.COMMA, null), new PTLeaf(Parser.BRACERIGHT, null))); }
	;

initializer_list
	: initializer                        { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER_LIST, (PTElement)$1.obj)); }
	| initializer_list COMMA initializer { $$ = new ParserVal(new PTNode(Nonterminals.INITIALIZER_LIST, (PTElement)$1.obj, new PTLeaf(Parser.COMMA, null), (PTElement)$3.obj)); }
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
	: IDENTIFIER NUMBER_SIGN statement { $$ = new ParserVal(new PTNode(Nonterminals.LABELED_STATEMENT, new PTLeaf(Parser.IDENTIFIER, $1.sval), new PTLeaf(Parser.NUMBER_SIGN, null), (PTElement)$3.obj)); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, new PTLeaf(Parser.BRACELEFT, null), new PTLeaf(Parser.BRACERIGHT, null))); }
	| BRACELEFT statement_list BRACERIGHT                  { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, new PTLeaf(Parser.BRACELEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.BRACERIGHT, null))); }
	| BRACELEFT declaration_list BRACERIGHT                { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, new PTLeaf(Parser.BRACELEFT, null), (PTElement)$2.obj, new PTLeaf(Parser.BRACERIGHT, null))); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { $$ = new ParserVal(new PTNode(Nonterminals.COMPOUND_STATEMENT, new PTLeaf(Parser.BRACELEFT, null), (PTElement)$2.obj, (PTElement)$3.obj, new PTLeaf(Parser.BRACERIGHT, null))); }
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
	: SEMICOLON            { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION_STATEMENT, new PTLeaf(Parser.SEMICOLON, null))); }
	| expression SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.EXPRESSION_STATEMENT, (PTElement)$1.obj, new PTLeaf(Parser.SEMICOLON, null))); }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { $$ = new ParserVal(new PTNode(Nonterminals.SELECTION_STATEMENT, new PTLeaf(Parser.IF, null), new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null), (PTElement)$5.obj, new PTLeaf(Parser.ELSE, null), (PTElement)$7.obj)); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, new PTLeaf(Parser.WHILE, null), new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, new PTLeaf(Parser.RBRIGHT, null), (PTElement)$5.obj)); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, new PTLeaf(Parser.DO, null), (PTElement)$2.obj, new PTLeaf(Parser.WHILE, null), new PTLeaf(Parser.RBLEFT, null), (PTElement)$5.obj, new PTLeaf(Parser.RBRIGHT, null), new PTLeaf(Parser.SEMICOLON, null))); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { $$ = new ParserVal(new PTNode(Nonterminals.ITERATION_STATEMENT, new PTLeaf(Parser.FOR, null), new PTLeaf(Parser.RBLEFT, null), (PTElement)$3.obj, (PTElement)$4.obj, (PTElement)$5.obj, new PTLeaf(Parser.RBRIGHT, null), (PTElement)$7.obj)); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, new PTLeaf(Parser.GOTO, null), new PTLeaf(Parser.IDENTIFIER, $2.sval), new PTLeaf(Parser.SEMICOLON, null))); }
	| BREAK SEMICOLON             { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, new PTLeaf(Parser.BREAK, null), new PTLeaf(Parser.SEMICOLON, null))); }
	| RETURN SEMICOLON            { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, new PTLeaf(Parser.RETURN, null), new PTLeaf(Parser.SEMICOLON, null))); }
	| RETURN expression SEMICOLON { $$ = new ParserVal(new PTNode(Nonterminals.JUMP_STATEMENT, new PTLeaf(Parser.RETURN, null), (PTElement)$2.obj, new PTLeaf(Parser.SEMICOLON, null))); }
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

  public static String getTokenName(short c)
  {
  	return Parser.yyname[c];
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