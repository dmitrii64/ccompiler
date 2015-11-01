/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2001 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * This is a modified version of the example from                          *
 *   http://www.lincom-asg.com/~rjamison/byacc/                            *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

%{
  import java.io.*;
  import edu.eltech.moevm.*;
  import edu.eltech.moevm.syntax_tree.*;
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
%token CARET BAR QUESTION

%start root
%%

primary_expression
	: IDENTIFIER                { $$ = new ParserVal(new Leaf(Operand.IDENTIFIER,$1.sval)); }
	| CONSTANT                  { $$ = new ParserVal(new Leaf(Operand.CONSTANT,$1.sval)); }
	| STRING_LITERAL            { $$ = new ParserVal(new Leaf(Operand.STRING_LITERAL, $1.sval)); }
	| RBLEFT expression RBRIGHT { $$ = $2; }
	;

postfix_expression
	: primary_expression                                         { $$ = $1; }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { $$ = new ParserVal(new Node(Operation.ARRAY_ACCESS, $1, $3)); }
	| postfix_expression RBLEFT RBRIGHT                          { $$ = new ParserVal(new Node(Operation.FUNC_CALL, $1)); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { $$ = new ParserVal(new Node(Operation.FUNC_CALL, $1, $3)); }
	| postfix_expression INC_OP                                  { $$ = new ParserVal(new Node(Operation.INC_OP, $1)); }
	| postfix_expression DEC_OP                                  { $$ = new ParserVal(new Node(Operation.DEC_OP, $1)); }
	;

argument_expression_list
	: assignment_expression                                { $$ = new ParserVal(new Node(Operation.ARGUMENT_EXP_LIST, $1)); }
	| argument_expression_list COMMA assignment_expression { $$ = new ParserVal(new Node(Operation.ARGUMENT_EXP_LIST, $1,$3)); }
	;

unary_expression
	: postfix_expression              { $$ = $1; }
	| INC_OP unary_expression         { $$ = new ParserVal(new Node(Operation.INC_OP, $1)); }
	| DEC_OP unary_expression         { $$ = new ParserVal(new Node(Operation.DEC_OP, $1)); }
	| MINUS  cast_expression	      { $$ = new ParserVal(new Node(Operation.UMINUS, $2)); }
	| EXCL  cast_expression	          { $$ = new ParserVal(new Node(Operation.NOT, $2)); }
	| SIZEOF unary_expression         { $$ = new ParserVal(new Node(Operation.SIZEOF, $2)); }
	| SIZEOF RBLEFT type_specifier RBRIGHT { $$ = new ParserVal(new Node(Operation.SIZEOFTYPE, $3)); }
	| RE RBLEFT CONSTANT RBRIGHT      { $$ = new ParserVal(new Node(Operation.RE, $3)); }
	| RE RBLEFT IDENTIFIER RBRIGHT    { $$ = new ParserVal(new Node(Operation.RE, $3)); }
	| IM RBLEFT CONSTANT RBRIGHT      { $$ = new ParserVal(new Node(Operation.IM, $3)); }
	| IM RBLEFT IDENTIFIER RBRIGHT    { $$ = new ParserVal(new Node(Operation.IM, $3)); }
	| MOD RBLEFT CONSTANT RBRIGHT     { $$ = new ParserVal(new Node(Operation.MOD, $3)); }
	| MOD RBLEFT IDENTIFIER RBRIGHT   { $$ = new ParserVal(new Node(Operation.MOD, $3)); }
	;

cast_expression
	: unary_expression                         { $$ = $1; }
	| RBLEFT type_specifier RBRIGHT cast_expression { $$ = new ParserVal(new Node(Operation.TYPE_CAST, $2, $4)); }
	;

multiplicative_expression
	: cast_expression                                   { $$ = $1; }
	| multiplicative_expression STAR cast_expression    { $$ = new ParserVal(new Node(Operation.MULTIPLY, $1, $3)); }
	| multiplicative_expression SLASH cast_expression   { $$ = new ParserVal(new Node(Operation.DIVIDE, $1, $3)); }
	| multiplicative_expression PERCENT cast_expression { $$ = new ParserVal(new Node(Operation.PERCENT, $1, $3)); }
	;

additive_expression
	: multiplicative_expression                           { $$ = $1; }
	| additive_expression PLUS multiplicative_expression  { $$ = new ParserVal(new Node(Operation.PLUS, $1, $3)); }
	| additive_expression MINUS multiplicative_expression { $$ = new ParserVal(new Node(Operation.MINUS, $1, $3)); }
	;

shift_expression
	: additive_expression                           { $$ = $1; }
	| shift_expression LEFT_OP additive_expression  { $$ = new ParserVal(new Node(Operation.LEFT_OP, $1, $3)); }
	| shift_expression RIGHT_OP additive_expression { $$ = new ParserVal(new Node(Operation.RIGHT_OP, $1, $3)); }
	;

relational_expression
	: shift_expression                               { $$ = $1; }
	| relational_expression LESS shift_expression    { $$ = new ParserVal(new Node(Operation.LESS, $1, $3)); }
	| relational_expression GREATER shift_expression { $$ = new ParserVal(new Node(Operation.GREATER, $1, $3)); }
	| relational_expression LE_OP shift_expression   { $$ = new ParserVal(new Node(Operation.LESS_OR_EQ, $1, $3)); }
	| relational_expression GE_OP shift_expression   { $$ = new ParserVal(new Node(Operation.GREATER_OR_EQ, $1, $3)); }
	;

equality_expression
	: relational_expression                           { $$ = $1; }
	| equality_expression EQ_OP relational_expression { $$ = new ParserVal(new Node(Operation.EQUAL, $1, $3)); }
	| equality_expression NE_OP relational_expression { $$ = new ParserVal(new Node(Operation.NOT_EQ, $1, $3)); }
	;

and_expression
	: equality_expression                    { $$ = $1; }
	| and_expression AMP equality_expression { $$ = new ParserVal(new Node(Operation.AND_OP, $1, $3)); }
	;

exclusive_or_expression
	: and_expression                               { $$ = $1; }
	| exclusive_or_expression CARET and_expression { $$ = new ParserVal(new Node(Operation.XOR_OP, $1, $3)); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { $$ = $1; }
	| inclusive_or_expression BAR exclusive_or_expression { $$ = new ParserVal(new Node(Operation.OR_OP, $1, $3)); }
	;

logical_and_expression
	: inclusive_or_expression                               { $$ = $1; }
	| logical_and_expression AND_OP inclusive_or_expression { $$ = new ParserVal(new Node(Operation.LOGICAL_AND_OP, $1, $3)); }
	;

logical_or_expression
	: logical_and_expression                             { $$ = $1; }
	| logical_or_expression OR_OP logical_and_expression { $$ = new ParserVal(new Node(Operation.LOGICAL_OR_OP, $1, $3)); }
	;

conditional_expression
	: logical_or_expression                                                  { $$ = $1; }
	| logical_or_expression QUESTION expression COLON conditional_expression { $$ = new ParserVal(new Node(Operation.QUESTION_OP, $1, $3, $5)); }
	;

assignment_expression
	: conditional_expression                       { $$ = $1; }
	| unary_expression EQUAL assignment_expression { $$ = new ParserVal(new Node(Operation.ASSIGNMENT, $1, $3)); }
	;

expression
	: assignment_expression                  { $$ = $1; }
	| expression COMMA assignment_expression { $$ = new ParserVal(new Node(Operation.EXPRESSION, $1, $3)); }
	;

constant_expression
	: conditional_expression { $$ = $1; }
	;

declaration
	: declaration_specifiers SEMICOLON                      { $$ = new ParserVal(new Node(Operation.DECLARATION, $1)); }
	| declaration_specifiers init_declarator_list SEMICOLON { $$ = new ParserVal(new Node(Operation.DECLARATION_INIT_LIST, $1, $2)); }
	;

declaration_specifiers
	: type_specifier                                 { $$ = $1; }
	| type_specifier declaration_specifiers          { $$ = new ParserVal(new Node(Operation.DEC_SPEC, $1, $2)); }
	;

init_declarator_list
	: init_declarator                            { $$ = $1; }
	| init_declarator_list COMMA init_declarator { $$ = new ParserVal(new Node(Operation.INIT_LIST, $1, $3)); }
	;

init_declarator
	: direct_declarator                   { $$ = $1; }
	| direct_declarator EQUAL initializer { $$ = new ParserVal(new Node(Operation.INIT_DECLARATION, $1, $3)); }
	;


type_specifier
	: VOID    { $$ = new ParserVal(new Leaf(Operand.VOID,"")); }
	| COMPLEX { $$ = new ParserVal(new Leaf(Operand.COMPLEX,"")); }
	| CHAR    { $$ = new ParserVal(new Leaf(Operand.CHAR,"")); }
	| SHORT   { $$ = new ParserVal(new Leaf(Operand.SHORT,"")); }
	| INT     { $$ = new ParserVal(new Leaf(Operand.INT,"")); }
	| LONG    { $$ = new ParserVal(new Leaf(Operand.LONG,"")); }
	| FLOAT   { $$ = new ParserVal(new Leaf(Operand.FLOAT,"")); }
	| DOUBLE  { $$ = new ParserVal(new Leaf(Operand.DOUBLE,"")); }
	| BOOL    { $$ = new ParserVal(new Leaf(Operand.BOOL,"")); }
	;


direct_declarator
	: IDENTIFIER                                                     { $$ = new ParserVal(new Leaf(Operand.IDENTIFIER, $1.sval)); }
	| RBLEFT direct_declarator RBRIGHT                               { $$ = $2; }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new Node(Operation.DIRECT_DEC_ARRAY, $1, $3)); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new Node(Operation.DIRECT_DEC_ARRAY, $1)); }
	| direct_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new Node(Operation.DIRECT_DEC_FUNC, $1, $3)); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { $$ = new ParserVal(new Node(Operation.DIRECT_DEC_FUNC, $1, $3)); }
	| direct_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new Node(Operation.DIRECT_DEC_FUNC, $1)); }
	;

parameter_list
	: parameter_declaration                      { $$ = $1; }
	| parameter_list COMMA parameter_declaration { $$ = new ParserVal(new Node(Operation.PARAM_LIST, $1, $3)); }
	;

parameter_declaration
	: declaration_specifiers direct_declarator   { $$ = new ParserVal(new Node(Operation.PARAM_DEC_DIR, $1, $2)); }
	| declaration_specifiers abstract_declarator { $$ = new ParserVal(new Node(Operation.PARAM_DEC_ABS, $1, $2)); }
	| declaration_specifiers                     { $$ = new ParserVal(new Node(Operation.PARAM_DEC, $1)); }
	;

identifier_list
	: IDENTIFIER                       { $$ = new ParserVal(new Leaf(Operand.IDENTIFIER, $1.sval)); }
	| identifier_list COMMA IDENTIFIER { $$ = new ParserVal(new Node(Operation.IDENT_LIST, $1, new ParserVal(new Leaf(Operand.IDENTIFIER, $2.sval)))); }
	;


abstract_declarator
	: direct_abstract_declarator { $$ = $1; }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { $$ = $2; }
	| BRACKETLEFT BRACKETRIGHT                                                { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_BRACKETS)); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_BRACKETS, $2)); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_BRACKETS_WITH_DEC, $1)); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_BRACKETS_WITH_DEC, $1,$3)); }
	| RBLEFT RBRIGHT                                                          { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_ROUND)); }
	| RBLEFT parameter_list RBRIGHT                                           { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_ROUND, $2)); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_ROUND_WITH_DEC, $1)); }
	| direct_abstract_declarator RBLEFT parameter_list RBRIGHT                { $$ = new ParserVal(new Node(Operation.DIR_ABS_DEC_ROUND_WITH_DEC, $1, $3)); }
	;

initializer
	: assignment_expression                       { $$ = $1; }
	| BRACELEFT initializer_list BRACERIGHT       { $$ = new ParserVal(new Node(Operation.ARRAY_INIT, $2)); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { $$ = new ParserVal(new Node(Operation.ARRAY_INIT, $2)); }
	;

initializer_list
	: initializer                        { $$ = $1; }
	| initializer_list COMMA initializer { $$ = new ParserVal(new Node(Operation.INITIALIZER_LIST, $1, $3)); }
	;

statement
	: labeled_statement    { $$ = $1; }
	| compound_statement   { $$ = $1; }
	| expression_statement { $$ = $1; }
	| selection_statement  { $$ = $1; }
	| iteration_statement  { $$ = $1; }
	| jump_statement       { $$ = $1; }
	;

labeled_statement
	: IDENTIFIER COLON statement               { $$ = new ParserVal(new Node(Operation.LABELED,new ParserVal(new Leaf(Operand.IDENTIFIER,$1.sval)), $3)); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { $$ = new ParserVal(new Node(Operation.EMPTY_BODY)); }
	| BRACELEFT statement_list BRACERIGHT                  { $$ = new ParserVal(new Node(Operation.COMP_STATEMENT, $2)); }
	| BRACELEFT declaration_list BRACERIGHT                { $$ = new ParserVal(new Node(Operation.COMP_STATEMENT, $2)); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { $$ = new ParserVal(new Node(Operation.COMP_STATEMENT, $2, $3)); }
	;

declaration_list
	: declaration                  { $$ = $1; }
	| declaration_list declaration { $$ = new ParserVal(new Node(Operation.DECL_LIST, $1, $2)); }
	;

statement_list
	: statement                { $$ = $1; }
	| statement_list statement { $$ = new ParserVal(new Node(Operation.STAT_LIST, $1, $2)); }
	;

expression_statement
	: SEMICOLON            { $$ = new ParserVal(new Node(Operation.EMPTY_EXPR)); }
	| expression SEMICOLON { $$ = $1; }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { $$ = new ParserVal(new Node(Operation.IF, $3, $5, $7)); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { $$ = new ParserVal(new Node(Operation.WHILE, $3, $5)); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { $$ = new ParserVal(new Node(Operation.DO, $2, $5)); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { $$ = new ParserVal(new Node(Operation.FOR, $3, $4, $5, $7)); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { $$ = new ParserVal(new Node(Operation.GOTO, new ParserVal(new Leaf(Operand.IDENTIFIER,$2.sval)))); }
	| BREAK SEMICOLON             { $$ = new ParserVal(new Node(Operation.BREAK)); }
	| RETURN SEMICOLON            { $$ = new ParserVal(new Node(Operation.RETURN)); }
	| RETURN expression SEMICOLON { $$ = new ParserVal(new Node(Operation.RETURN, $2)); }
	;

root
	: translation_unit { $$ = new ParserVal(new Node(Operation.ROOT, $1)); }
	;

translation_unit
	: external_declaration                  { $$ = $1; }
	| translation_unit external_declaration { $$ = new ParserVal(new Node(Operation.TR_UNIT, $1, $2)); }
	;

external_declaration
	: function_definition { $$ = new ParserVal(new Node(Operation.FUNC_DEF, $1)); }
	| declaration         { $$ = new ParserVal(new Node(Operation.EXT_DECL, $1)); }
	;

function_definition
	: declaration_specifiers direct_declarator declaration_list compound_statement { $$ = new ParserVal(new Node(Operation.FNDEF_1, $1, $2, $3, $4)); }
	| declaration_specifiers direct_declarator compound_statement                  { $$ = new ParserVal(new Node(Operation.FNDEF_2, $1, $2, $3)); }
	| direct_declarator declaration_list compound_statement                        { $$ = new ParserVal(new Node(Operation.FNDEF_3, $1, $2, $3)); }
	| direct_declarator compound_statement                                         { $$ = new ParserVal(new Node(Operation.FNDEF_4, $1, $2)); }
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

  public static ParserVal ParseFile(String file) throws IOException {
	System.out.println("Lexer:");
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

	System.out.println("Parser:");

  	//yyparser.yydebug = true;
  	yyparser.yyparse(); //Parsing goes here
  	return yyparser.yyval;
  }
