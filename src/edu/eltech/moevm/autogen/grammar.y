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
%}
      
%token IDENTIFIER CONSTANT STRING_LITERAL SIZEOF
%token PTR_OP INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP
%token TYPE_NAME

%token TYPEDEF STATIC AUTO REGISTER
%token CHAR SHORT INT LONG SIGNED UNSIGNED FLOAT DOUBLE CONST VOID
%token STRUCT UNION ENUM ELLIPSIS

%token CASE DEFAULT IF ELSE SWITCH WHILE DO FOR GOTO BREAK RETURN

%token SEMICOLON BRACELEFT BRACERIGHT COMMA COLON EQUAL RBLEFT RBRIGHT BRACKETLEFT
%token BRACKETRIGHT DOT AMP EXCL MINUS PLUS STAR SLASH PERCENT LESS GREATER
%token CARET BAR QUESTION

%start translation_unit
%%

primary_expression
	: IDENTIFIER                                                                { $$ = new ParserVal($1.sval); }
	| CONSTANT                                                                  { System.out.println("primary_expression"); }
	| STRING_LITERAL                                                            { $$ = new ParserVal($1.sval); }
	| RBLEFT expression RBRIGHT
	;

postfix_expression
	: primary_expression                                                        { System.out.println("postfix_expression"); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT                    { $$ = Translator.postfix_expression2($1,$3); }
	| postfix_expression RBLEFT RBRIGHT                                         { $$ = Translator.postfix_expression3($1); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT                { $$ = Translator.postfix_expression4($1,$3); }
	| postfix_expression DOT IDENTIFIER
	| postfix_expression PTR_OP IDENTIFIER
	| postfix_expression INC_OP
	| postfix_expression DEC_OP
	;

argument_expression_list
	: assignment_expression                                                         { $$ = Translator.argument_expression_list1($1); }
	| argument_expression_list COMMA assignment_expression                          { $$ = Translator.argument_expression_list2($1,$3); }
	;

unary_expression
	: postfix_expression                                                            { System.out.println("unary_expression"); }
	| INC_OP unary_expression                                                       { $$ = Translator.unary_expression2($2); }
	| DEC_OP unary_expression                                                       { $$ = Translator.unary_expression3($2); }
	| unary_operator cast_expression                                                { $$ = Translator.unary_expression4($1,$2); }
	| SIZEOF unary_expression                                                       { $$ = Translator.unary_expression5($2); }
	| SIZEOF RBLEFT type_name RBRIGHT                                               { $$ = Translator.unary_expression6($3); }
	;

unary_operator
	: AMP                                                                           { $$ = new ParserVal("&"); }
	| STAR                                                                          { $$ = new ParserVal("*"); }
	| PLUS                                                                          { $$ = new ParserVal("+"); }
	| MINUS                                                                         { $$ = new ParserVal("-"); }
	| EXCL                                                                          { $$ = new ParserVal("!"); }
	;

cast_expression
	: unary_expression                                                                       { System.out.println("cast_expression"); }
	| RBLEFT type_name RBRIGHT cast_expression
	;

multiplicative_expression
	: cast_expression                                                                        { System.out.println("multiplicative_expression"); }
	| multiplicative_expression STAR cast_expression                                         { $$ = Translator.multiplicative_expression2($1,$3); }
	| multiplicative_expression SLASH cast_expression
	| multiplicative_expression PERCENT cast_expression
	;

additive_expression
	: multiplicative_expression                                                              { System.out.println("additive_expression"); }
	| additive_expression PLUS multiplicative_expression                                     { $$ = Translator.additive_expression2($1,$3); }
	| additive_expression MINUS multiplicative_expression                                    { $$ = Translator.additive_expression3($1,$3); }
	;

shift_expression
	: additive_expression                                                                    { System.out.println("shift_expression"); }
	| shift_expression LEFT_OP additive_expression
	| shift_expression RIGHT_OP additive_expression
	;

relational_expression
	: shift_expression                                                                       { System.out.println("relational_expression"); }
	| relational_expression LESS shift_expression
	| relational_expression GREATER shift_expression
	| relational_expression LE_OP shift_expression
	| relational_expression GE_OP shift_expression
	;

equality_expression
	: relational_expression                                                                  { System.out.println("equality_expression"); }
	| equality_expression EQ_OP relational_expression
	| equality_expression NE_OP relational_expression
	;

and_expression
	: equality_expression                                                                    { System.out.println("and_expression"); }
	| and_expression AMP equality_expression
	;

exclusive_or_expression
	: and_expression                                                                         { System.out.println("exclusive_or_expression"); }
	| exclusive_or_expression CARET and_expression
	;

inclusive_or_expression
	: exclusive_or_expression                                                                { System.out.println("inclusive_or_expression"); }
	| inclusive_or_expression BAR exclusive_or_expression
	;

logical_and_expression
	: inclusive_or_expression                                                                { System.out.println("logical_and_expression"); }
	| logical_and_expression AND_OP inclusive_or_expression                                  { $$ = Translator.logical_and_expression2($1,$3); }
	;

logical_or_expression
	: logical_and_expression                                                                 { System.out.println("logical_or_expression"); }
	| logical_or_expression OR_OP logical_and_expression                                     { $$ = Translator.logical_or_expression2($1,$3); }
	;

conditional_expression
	: logical_or_expression                                                                  { System.out.println("conditional_expression"); }
	| logical_or_expression QUESTION expression COLON conditional_expression                 { $$ = Translator.conditional_expression2($1,$2,$3); }
	;

assignment_expression
	: conditional_expression                                                                 { System.out.println("assignment_expression"); }
	| unary_expression EQUAL assignment_expression                                           { $$ = Translator.assignment_expression2($1,$3); }
	;

expression
	: assignment_expression                                                                 { $$ = new ParserVal($1.sval); }
	| expression COMMA assignment_expression                                                { $$ = new ParserVal($1.sval+"\n"+$2.sval); }
	;

constant_expression
	: conditional_expression
	;

declaration
	: declaration_specifiers SEMICOLON                                                      { $$ = Translator.declaration1($1); }
	| declaration_specifiers init_declarator_list SEMICOLON                                 { System.out.println("declaration"); }
	;

declaration_specifiers
	: storage_class_specifier                                                               { $$ = new ParserVal($1.sval); }
	| storage_class_specifier declaration_specifiers                                        { $$ = new ParserVal($1.sval+" "+$2.sval); }
	| type_specifier                                                                        { System.out.println("declaration_specifiers"); }
	| type_specifier declaration_specifiers                                                 { $$ = new ParserVal($1.sval+" "+$2.sval); }
	| CONST                                                                                 { $$ = new ParserVal($1.sval); }
	| CONST declaration_specifiers                                                          { $$ = new ParserVal($1.sval+" "+$2.sval); }
	;

init_declarator_list
	: init_declarator                                                                       { System.out.println("init_declarator_list"); }
	| init_declarator_list COMMA init_declarator                                            { $$ = Translator.init_declarator_list2($1,$3); }
	;

init_declarator
	: declarator                                                                            { $$ = Translator.init_declarator1($1); }
	| declarator EQUAL initializer                                                          { System.out.println("init_declarator"); }
	;

storage_class_specifier
	: TYPEDEF
	| STATIC
	| AUTO
	| REGISTER
	;

type_specifier
	: VOID                                                                  { $$ = new ParserVal("VOID"); }
	| CHAR                                                                  { $$ = new ParserVal("CHAR"); }
	| SHORT                                                                 { $$ = new ParserVal("SHORT"); }
	| INT                                                                   { System.out.println("type_specifier"); }
	| LONG                                                                  { $$ = new ParserVal("LONG"); }
	| FLOAT                                                                 { $$ = new ParserVal("FLOAT"); }
	| DOUBLE                                                                { $$ = new ParserVal("DOUBLE"); }
	| SIGNED                                                                { $$ = new ParserVal("SIGNED"); }
	| UNSIGNED                                                              { $$ = new ParserVal("UNSIGNED"); }
	| TYPE_NAME                                                             { $$ = new ParserVal($1); }
	;

specifier_qualifier_list
	: type_specifier specifier_qualifier_list
	| type_specifier
	| CONST specifier_qualifier_list
	| CONST
	;

declarator
	: pointer direct_declarator                                         { $$ = Translator.declarator1($1,$2); }
	| direct_declarator                                                 { System.out.println("declarator"); }
	;

direct_declarator
	: IDENTIFIER                                                        { System.out.println("direct_declarator"); }
	| RBLEFT declarator RBRIGHT                                         { $$ = Translator.direct_declarator2($2); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT
	| direct_declarator BRACKETLEFT BRACKETRIGHT
	| direct_declarator RBLEFT parameter_type_list RBRIGHT              { $$ = Translator.direct_declarator5($1,$3); }
	| direct_declarator RBLEFT identifier_list RBRIGHT
	| direct_declarator RBLEFT RBRIGHT
	;

pointer
	: STAR
	| STAR CONST
	| STAR pointer
	| STAR CONST pointer
	;

parameter_type_list
	: parameter_list                                                                { $$ = new ParserVal($1.sval); }
	| parameter_list COMMA ELLIPSIS                                                 { $$ = new ParserVal($1.sval); }
	;

parameter_list
	: parameter_declaration                                                         { $$ = new ParserVal($1.sval); }
	| parameter_list COMMA parameter_declaration                                    { $$ = new ParserVal($1.sval+" "+$3.sval); }
	;

parameter_declaration
	: declaration_specifiers declarator                                             { $$ = Translator.parameter_declaration1($1,$2); }
	| declaration_specifiers abstract_declarator                                    { $$ = Translator.parameter_declaration2($1,$2); }
	| declaration_specifiers                                                        { $$ = Translator.parameter_declaration3($1); }
	;

identifier_list
	: IDENTIFIER                                                                                { $$ = Translator.identifier_list1($1); }
	| identifier_list COMMA IDENTIFIER                                                          { $$ = Translator.identifier_list2($1,$3); }
	;

type_name
	: specifier_qualifier_list
	| specifier_qualifier_list abstract_declarator
	;

abstract_declarator
	: pointer
	| direct_abstract_declarator
	| pointer direct_abstract_declarator
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT
	| BRACKETLEFT BRACKETRIGHT
	| BRACKETLEFT constant_expression BRACKETRIGHT
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT
	| RBLEFT RBRIGHT
	| RBLEFT parameter_type_list RBRIGHT
	| direct_abstract_declarator RBLEFT RBRIGHT
	| direct_abstract_declarator RBLEFT parameter_type_list RBRIGHT
	;

initializer
	: assignment_expression                                                                     { System.out.println("initializer"); $$ = Translator.initializer1($1); }
	| BRACELEFT initializer_list BRACERIGHT                                                     { $$ = Translator.initializer2($2); }
	| BRACELEFT initializer_list COMMA BRACERIGHT                                               { $$ = Translator.initializer3($2); }
	;

initializer_list
	: initializer                                                                               { $$ = Translator.initializer_list1($1); }
	| initializer_list COMMA initializer                                                        { $$ = Translator.initializer_list2($1,$3); }
	;

statement
	: labeled_statement                                                                         { $$ = new ParserVal($1.sval); }
	| compound_statement                                                                        { $$ = new ParserVal($1.sval); }
	| expression_statement                                                                      { $$ = new ParserVal($1.sval); }
	| selection_statement                                                                       { $$ = new ParserVal($1.sval); }
	| iteration_statement                                                                       { $$ = new ParserVal($1.sval); }
	| jump_statement                                                                            { $$ = new ParserVal($1.sval); }
	;

labeled_statement
	: IDENTIFIER COLON statement
	| CASE constant_expression COLON statement
	| DEFAULT COLON statement
	;

compound_statement
	: BRACELEFT BRACERIGHT                                              { $$ = Translator.compound_statement1(); }
	| BRACELEFT statement_list BRACERIGHT                               { $$ = Translator.compound_statement2($2); }
	| BRACELEFT declaration_list BRACERIGHT                             { $$ = Translator.compound_statement3($2); }
	| BRACELEFT declaration_list statement_list BRACERIGHT              { $$ = Translator.compound_statement4($2,$3); }
	;

declaration_list
	: declaration                                                       { $$ = Translator.declaration_list1($1); }
	| declaration_list declaration                                      { $$ = Translator.declaration_list2($1,$2); }
	;

statement_list
	: statement                                                         { $$ = Translator.statement_list1($1); }
	| statement_list statement                                          { $$ = Translator.statement_list2($1,$2); }
	;

expression_statement
	: SEMICOLON
	| expression SEMICOLON
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement
	| SWITCH RBLEFT expression RBRIGHT statement
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON
	| FOR RBLEFT expression_statement expression_statement RBRIGHT statement
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON                             { $$ = Translator.jump_statement1($2); }
	| BREAK SEMICOLON                                       { $$ = Translator.jump_statement2(); }
	| RETURN SEMICOLON                                      { $$ = Translator.jump_statement3(); }
	| RETURN expression SEMICOLON                           { $$ = Translator.jump_statement4($2); }
	;

translation_unit
	: external_declaration                                  { System.out.println("translation_unit"); }
	| translation_unit external_declaration                 { $$ = Translator.translation_unit($2); System.out.println("!!!");  System.out.println($$.sval); }
	;

external_declaration
	: function_definition                                   {  $$ = Translator.external_declaration($1); }
	| declaration                                           { System.out.println("external_declaration"); }
	;

function_definition
	: declaration_specifiers declarator declaration_list compound_statement                 { $$ = Translator.function_definition1($1,$2,$3,$4); }
	| declaration_specifiers declarator compound_statement                                  { $$ = Translator.function_definition2($1,$2,$3); }
	| declarator declaration_list compound_statement                                        { $$ = Translator.function_definition3($1,$2,$3); }
	| declarator compound_statement                                                         { $$ = Translator.function_definition4($1,$2); }
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

  public void doParse() {
  	yyparse();
  }

  public void printTree() {
  	// return yyval;
  }