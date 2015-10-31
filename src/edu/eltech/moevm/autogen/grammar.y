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
%token CHAR SHORT INT LONG FLOAT DOUBLE CONST VOID COMPLEX BOOL

%token RE IM MOD

%token CASE DEFAULT IF ELSE SWITCH WHILE DO FOR GOTO BREAK RETURN

%token SEMICOLON BRACELEFT BRACERIGHT COMMA COLON EQUAL RBLEFT RBRIGHT BRACKETLEFT
%token BRACKETRIGHT DOT AMP EXCL MINUS PLUS STAR SLASH PERCENT LESS GREATER
%token CARET BAR QUESTION

%start root
%%

primary_expression
	: IDENTIFIER                { $$ = new Node("primary_expression", $1); }
	| CONSTANT                  { $$ = new Node("primary_expression", $1); }
	| STRING_LITERAL            { $$ = new Node("primary_expression", $1); }
	| RBLEFT expression RBRIGHT { $$ = new Node("primary_expression", $1, $2, $3); }
	;

postfix_expression
	: primary_expression                                         { $$ = new Node("postfix_expression", $1); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { $$ = new Node("postfix_expression", $1, $2, $3, $4); }
	| postfix_expression RBLEFT RBRIGHT                          { $$ = new Node("postfix_expression", $1, $2, $3); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { $$ = new Node("postfix_expression", $1, $2, $3, $4); }
	| postfix_expression DOT IDENTIFIER                          { $$ = new Node("postfix_expression", $1, $2, $3); }
	| postfix_expression INC_OP                                  { $$ = new Node("postfix_expression", $1, $2); }
	| postfix_expression DEC_OP                                  { $$ = new Node("postfix_expression", $1, $2); }
	;

argument_expression_list
	: assignment_expression                                { $$ = new Node("argument_expression_list", $1); }
	| argument_expression_list COMMA assignment_expression { $$ = new Node("argument_expression_list", $1, $2, $3); }
	;

unary_expression
	: postfix_expression              { $$ = new Node("unary_expression", $1); }
	| INC_OP unary_expression         { $$ = new Node("unary_expression", $1, $2); }
	| DEC_OP unary_expression         { $$ = new Node("unary_expression", $1, $2); }
	| unary_operator cast_expression  { $$ = new Node("unary_expression", $1, $2); }
	| SIZEOF unary_expression         { $$ = new Node("unary_expression", $1, $2); }
	| SIZEOF RBLEFT type_name RBRIGHT { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| RE RBLEFT CONSTANT RBRIGHT      { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| RE RBLEFT IDENTIFIER RBRIGHT    { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| IM RBLEFT CONSTANT RBRIGHT      { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| IM RBLEFT IDENTIFIER RBRIGHT    { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| MOD RBLEFT CONSTANT RBRIGHT     { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	| MOD RBLEFT IDENTIFIER RBRIGHT   { $$ = new Node("unary_expression", $1, $2, $3, $4); }
	;

unary_operator
	: AMP   { $$ = new Node("unary_operator", $1); }
	| STAR  { $$ = new Node("unary_operator", $1); }
	| PLUS  { $$ = new Node("unary_operator", $1); }
	| MINUS { $$ = new Node("unary_operator", $1); }
	| EXCL  { $$ = new Node("unary_operator", $1); }
	;

cast_expression
	: unary_expression                         { $$ = new Node("cast_expression", $1); }
	| RBLEFT type_name RBRIGHT cast_expression { $$ = new Node("cast_expression", $1, $2, $3, $4); }
	;

multiplicative_expression
	: cast_expression                                   { $$ = new Node("multiplicative_expression", $1); }
	| multiplicative_expression STAR cast_expression    { $$ = new Node("multiplicative_expression", $1, $2, $3); }
	| multiplicative_expression SLASH cast_expression   { $$ = new Node("multiplicative_expression", $1, $2, $3); }
	| multiplicative_expression PERCENT cast_expression { $$ = new Node("multiplicative_expression", $1, $2, $3); }
	;

additive_expression
	: multiplicative_expression                           { $$ = new Node("additive_expression", $1); }
	| additive_expression PLUS multiplicative_expression  { $$ = new Node("additive_expression", $1, $2, $3); }
	| additive_expression MINUS multiplicative_expression { $$ = new Node("additive_expression", $1, $2, $3); }
	;

shift_expression
	: additive_expression                           { $$ = new Node("shift_expression", $1); }
	| shift_expression LEFT_OP additive_expression  { $$ = new Node("shift_expression", $1, $2, $3); }
	| shift_expression RIGHT_OP additive_expression { $$ = new Node("shift_expression", $1, $2, $3); }
	;

relational_expression
	: shift_expression                               { $$ = new Node("relational_expression", $1); }
	| relational_expression LESS shift_expression    { $$ = new Node("relational_expression", $1, $2, $3); }
	| relational_expression GREATER shift_expression { $$ = new Node("relational_expression", $1, $2, $3); }
	| relational_expression LE_OP shift_expression   { $$ = new Node("relational_expression", $1, $2, $3); }
	| relational_expression GE_OP shift_expression   { $$ = new Node("relational_expression", $1, $2, $3); }
	;

equality_expression
	: relational_expression                           { $$ = new Node("equality_expression", $1); }
	| equality_expression EQ_OP relational_expression { $$ = new Node("equality_expression", $1, $2, $3); }
	| equality_expression NE_OP relational_expression { $$ = new Node("equality_expression", $1, $2, $3); }
	;

and_expression
	: equality_expression                    { $$ = new Node("and_expression", $1); }
	| and_expression AMP equality_expression { $$ = new Node("and_expression", $1, $2, $3); }
	;

exclusive_or_expression
	: and_expression                               { $$ = new Node("exclusive_or_expression", $1); }
	| exclusive_or_expression CARET and_expression { $$ = new Node("exclusive_or_expression", $1, $2, $3); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { $$ = new Node("inclusive_or_expression", $1); }
	| inclusive_or_expression BAR exclusive_or_expression { $$ = new Node("inclusive_or_expression", $1, $2, $3); }
	;

logical_and_expression
	: inclusive_or_expression                               { $$ = new Node("logical_and_expression", $1); }
	| logical_and_expression AND_OP inclusive_or_expression { $$ = new Node("logical_and_expression", $1, $2, $3); }
	;

logical_or_expression
	: logical_and_expression                             { $$ = new Node("logical_or_expression", $1); }
	| logical_or_expression OR_OP logical_and_expression { $$ = new Node("logical_or_expression", $1, $2, $3); }
	;

conditional_expression
	: logical_or_expression                                                  { $$ = new Node("conditional_expression", $1); }
	| logical_or_expression QUESTION expression COLON conditional_expression { $$ = new Node("conditional_expression", $1, $2, $3, $4, $5); }
	;

assignment_expression
	: conditional_expression                       { $$ = new Node("assignment_expression", $1); }
	| unary_expression EQUAL assignment_expression { $$ = new Node("assignment_expression", $1, $2, $3); }
	;

expression
	: assignment_expression                  { $$ = new Node("expression", $1); }
	| expression COMMA assignment_expression { $$ = new Node("expression", $1, $2, $3); }
	;

constant_expression
	: conditional_expression { $$ = new Node("constant_expression", $1); }
	;

declaration
	: declaration_specifiers SEMICOLON                      { $$ = new Node("declaration", $1, $2); }
	| declaration_specifiers init_declarator_list SEMICOLON { $$ = new Node("declaration", $1, $2, $3); }
	;

declaration_specifiers
	: storage_class_specifier                        { $$ = new Node("declaration_specifiers", $1); }
	| storage_class_specifier declaration_specifiers { $$ = new Node("declaration_specifiers", $1, $2); }
	| type_specifier                                 { $$ = new Node("declaration_specifiers", $1); }
	| type_specifier declaration_specifiers          { $$ = new Node("declaration_specifiers", $1, $2); }
	| CONST                                          { $$ = new Node("declaration_specifiers", $1); }
	| CONST declaration_specifiers                   { $$ = new Node("declaration_specifiers", $1, $2); }
	;

init_declarator_list
	: init_declarator                            { $$ = new Node("init_declarator_list", $1); }
	| init_declarator_list COMMA init_declarator { $$ = new Node("init_declarator_list", $1, $2, $3); }
	;

init_declarator
	: direct_declarator                   { $$ = new Node("init_declarator", $1); }
	| direct_declarator EQUAL initializer { $$ = new Node("init_declarator", $1, $2, $3); }
	;

storage_class_specifier
	: STATIC { $$ = new Node("storage_class_specifier", $1); }
	;

type_specifier
	: VOID    { $$ = new Node("type_specifier", $1); }
	| COMPLEX { $$ = new Node("type_specifier", $1); }
	| CHAR    { $$ = new Node("type_specifier", $1); }
	| SHORT   { $$ = new Node("type_specifier", $1); }
	| INT     { $$ = new Node("type_specifier", $1); }
	| LONG    { $$ = new Node("type_specifier", $1); }
	| FLOAT   { $$ = new Node("type_specifier", $1); }
	| DOUBLE  { $$ = new Node("type_specifier", $1); }
	| BOOL    { $$ = new Node("type_specifier", $1); }
	;

specifier_qualifier_list
	: type_specifier specifier_qualifier_list { $$ = new Node("specifier_qualifier_list", $1, $2); }
	| type_specifier                          { $$ = new Node("specifier_qualifier_list", $1); }
	| CONST specifier_qualifier_list          { $$ = new Node("specifier_qualifier_list", $1, $2); }
	| CONST                                   { $$ = new Node("specifier_qualifier_list", $1); }
	;

direct_declarator
	: IDENTIFIER                                                     { $$ = new Node("direct_declarator", $1); }
	| RBLEFT direct_declarator RBRIGHT                               { $$ = new Node("direct_declarator", $1, $2, $3); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new Node("direct_declarator", $1, $2, $3, $4); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new Node("direct_declarator", $1, $2, $3); }
	| direct_declarator RBLEFT parameter_list RBRIGHT                { $$ = new Node("direct_declarator", $1, $2, $3, $4); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { $$ = new Node("direct_declarator", $1, $2, $3, $4); }
	| direct_declarator RBLEFT RBRIGHT                               { $$ = new Node("direct_declarator", $1, $2, $3); }
	;

parameter_list
	: parameter_declaration                      { $$ = new Node("parameter_list", $1); }
	| parameter_list COMMA parameter_declaration { $$ = new Node("parameter_list", $1, $2, $3); }
	;

parameter_declaration
	: declaration_specifiers direct_declarator   { $$ = new Node("parameter_declaration", $1, $2); }
	| declaration_specifiers abstract_declarator { $$ = new Node("parameter_declaration", $1, $2); }
	| declaration_specifiers                     { $$ = new Node("parameter_declaration", $1); }
	;

identifier_list
	: IDENTIFIER                       { $$ = new Node("identifier_list", $1); }
	| identifier_list COMMA IDENTIFIER { $$ = new Node("identifier_list", $1, $2, $3); }
	;

type_name
	: specifier_qualifier_list                     { $$ = new Node("type_name", $1); }
	| specifier_qualifier_list abstract_declarator { $$ = new Node("type_name", $1, $2); }
	;

abstract_declarator
	: direct_abstract_declarator { $$ = new Node("abstract_declarator", $1); }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { $$ = new Node("direct_abstract_declarator", $1, $2, $3); }
	| BRACKETLEFT BRACKETRIGHT                                                { $$ = new Node("direct_abstract_declarator", $1, $2); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { $$ = new Node("direct_abstract_declarator", $1, $2, $3); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new Node("direct_abstract_declarator", $1, $2, $3); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new Node("direct_abstract_declarator", $1, $2, $3, $4); }
	| RBLEFT RBRIGHT                                                          { $$ = new Node("direct_abstract_declarator", $1, $2); }
	| RBLEFT parameter_list RBRIGHT                                           { $$ = new Node("direct_abstract_declarator", $1, $2, $3); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { $$ = new Node("direct_abstract_declarator", $1, $2, $3); }
	| direct_abstract_declarator RBLEFT parameter_list RBRIGHT                { $$ = new Node("direct_abstract_declarator", $1, $2, $3, $4); }
	;

initializer
	: assignment_expression                       { $$ = new Node("initializer", $1); }
	| BRACELEFT initializer_list BRACERIGHT       { $$ = new Node("initializer", $1, $2, $3); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { $$ = new Node("initializer", $1, $2, $3, $4); }
	;

initializer_list
	: initializer                        { $$ = new Node("initializer_list", $1); }
	| initializer_list COMMA initializer { $$ = new Node("initializer_list", $1, $2, $3); }
	;

statement
	: labeled_statement    { $$ = new Node("statement", $1); }
	| compound_statement   { $$ = new Node("statement", $1); }
	| expression_statement { $$ = new Node("statement", $1); }
	| selection_statement  { $$ = new Node("statement", $1); }
	| iteration_statement  { $$ = new Node("statement", $1); }
	| jump_statement       { $$ = new Node("statement", $1); }
	;

labeled_statement
	: IDENTIFIER COLON statement               { $$ = new Node("labeled_statement", $1, $2, $3); }
	| CASE constant_expression COLON statement { $$ = new Node("labeled_statement", $1, $2, $3, $4); }
	| DEFAULT COLON statement                  { $$ = new Node("labeled_statement", $1, $2, $3); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { $$ = new Node("compound_statement", $1, $2); }
	| BRACELEFT statement_list BRACERIGHT                  { $$ = new Node("compound_statement", $1, $2, $3); }
	| BRACELEFT declaration_list BRACERIGHT                { $$ = new Node("compound_statement", $1, $2, $3); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { $$ = new Node("compound_statement", $1, $2, $3, $4); }
	;

declaration_list
	: declaration                  { $$ = new Node("declaration_list", $1); }
	| declaration_list declaration { $$ = new Node("declaration_list", $1, $2); }
	;

statement_list
	: statement                { $$ = new Node("statement_list", $1); }
	| statement_list statement { $$ = new Node("statement_list", $1, $2); }
	;

expression_statement
	: SEMICOLON            { $$ = new Node("expression_statement", $1); }
	| expression SEMICOLON { $$ = new Node("expression_statement", $1, $2); }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { $$ = new Node("selection_statement", $1, $2, $3, $4, $5, $6, $7); }
	| SWITCH RBLEFT expression RBRIGHT statement            { $$ = new Node("selection_statement", $1, $2, $3, $4, $5); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { $$ = new Node("iteration_statement", $1, $2, $3, $4, $5); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { $$ = new Node("iteration_statement", $1, $2, $3, $4, $5, $6, $7); }
	| FOR RBLEFT expression_statement expression_statement RBRIGHT statement            { $$ = new Node("iteration_statement", $1, $2, $3, $4, $5, $6); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { $$ = new Node("iteration_statement", $1, $2, $3, $4, $5, $6, $7); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { $$ = new Node("jump_statement", $1, $2, $3); }
	| BREAK SEMICOLON             { $$ = new Node("jump_statement", $1, $2); }
	| RETURN SEMICOLON            { $$ = new Node("jump_statement", $1, $2); }
	| RETURN expression SEMICOLON { $$ = new Node("jump_statement", $1, $2, $3); }
	;

root
	: translation_unit { $$ = new Node("root", $1); }
	;

translation_unit
	: external_declaration                  { $$ = new Node("translation_unit", $1); }
	| translation_unit external_declaration { $$ = new Node("translation_unit", $1, $2); }
	;

external_declaration
	: function_definition { $$ = new Node("external_declaration", $1); }
	| declaration         { $$ = new Node("external_declaration", $1); }
	;

function_definition
	: declaration_specifiers direct_declarator declaration_list compound_statement { $$ = new Node("function_definition", $1, $2, $3, $4); }
	| declaration_specifiers direct_declarator compound_statement                  { $$ = new Node("function_definition", $1, $2, $3); }
	| direct_declarator declaration_list compound_statement                        { $$ = new Node("function_definition", $1, $2, $3); }
	| direct_declarator compound_statement                                         { $$ = new Node("function_definition", $1, $2); }
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
