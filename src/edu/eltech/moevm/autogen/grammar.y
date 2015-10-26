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
	| CONSTANT                                                                  { $$ = new ParserVal($1.sval); }
	| STRING_LITERAL                                                            { $$ = new ParserVal($1.sval); }
	| RBLEFT expression RBRIGHT
	;

postfix_expression
	: primary_expression                                                        { $$ = new ParserVal($1.sval); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT                    { $$ = Translator.postfix_expression2($1,$3); }
	| postfix_expression RBLEFT RBRIGHT                                         { $$ = Translator.postfix_expression3($1); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT                { $$ = Translator.postfix_expression4($1,$3); }
	| postfix_expression DOT IDENTIFIER
	| postfix_expression PTR_OP IDENTIFIER
	| postfix_expression INC_OP
	| postfix_expression DEC_OP
	;

argument_expression_list
	: assignment_expression
	| argument_expression_list COMMA assignment_expression
	;

unary_expression
	: postfix_expression                                                            { $$ = Translator.unary_expression1($1); }
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
	: unary_expression                                                                       { $$ = new ParserVal($1.sval); }
	| RBLEFT type_name RBRIGHT cast_expression
	;

multiplicative_expression
	: cast_expression                                                                        { $$ = new ParserVal($1.sval); }
	| multiplicative_expression STAR cast_expression                                         { $$ = Translator.multiplicative_expression2($1,$3); }
	| multiplicative_expression SLASH cast_expression
	| multiplicative_expression PERCENT cast_expression
	;

additive_expression
	: multiplicative_expression                                                              { $$ = new ParserVal($1.sval); }
	| additive_expression PLUS multiplicative_expression                                     { $$ = Translator.additive_expression2($1,$3); }
	| additive_expression MINUS multiplicative_expression                                    { $$ = Translator.additive_expression3($1,$3); }
	;

shift_expression
	: additive_expression                                                                    { $$ = new ParserVal($1.sval); }
	| shift_expression LEFT_OP additive_expression
	| shift_expression RIGHT_OP additive_expression
	;

relational_expression
	: shift_expression                                                                       { $$ = new ParserVal($1.sval); }
	| relational_expression LESS shift_expression
	| relational_expression GREATER shift_expression
	| relational_expression LE_OP shift_expression
	| relational_expression GE_OP shift_expression
	;

equality_expression
	: relational_expression                                                                  { $$ = new ParserVal($1.sval); }
	| equality_expression EQ_OP relational_expression
	| equality_expression NE_OP relational_expression
	;

and_expression
	: equality_expression                                                                    { $$ = new ParserVal($1.sval); }
	| and_expression AMP equality_expression
	;

exclusive_or_expression
	: and_expression                                                                         { $$ = new ParserVal($1.sval); }
	| exclusive_or_expression CARET and_expression
	;

inclusive_or_expression
	: exclusive_or_expression                                                                { $$ = new ParserVal($1.sval); }
	| inclusive_or_expression BAR exclusive_or_expression
	;

logical_and_expression
	: inclusive_or_expression                                                                { $$ = new ParserVal($1.sval); }
	| logical_and_expression AND_OP inclusive_or_expression                                  { $$ = Translator.logical_and_expression2($1,$3); }
	;

logical_or_expression
	: logical_and_expression                                                                 { $$ = new ParserVal($1.sval); }
	| logical_or_expression OR_OP logical_and_expression                                     { $$ = Translator.logical_or_expression2($1,$3); }
	;

conditional_expression
	: logical_or_expression                                                                  { $$ = new ParserVal($1.sval); }
	| logical_or_expression QUESTION expression COLON conditional_expression                 { $$ = Translator.conditional_expression2($1,$2,$3); }
	;

assignment_expression
	: conditional_expression                                                                 { $$ = new ParserVal($1.sval); }
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
	| declaration_specifiers init_declarator_list SEMICOLON                                 { $$ = Translator.declaration2($1,$2); }
	;

declaration_specifiers
	: storage_class_specifier                                                               { $$ = new ParserVal($1.sval); }
	| storage_class_specifier declaration_specifiers                                        { $$ = new ParserVal($1.sval+" "+$2.sval); }
	| type_specifier                                                                        { $$ = new ParserVal($1.sval); }
	| type_specifier declaration_specifiers                                                 { $$ = new ParserVal($1.sval+" "+$2.sval); }
	| CONST                                                                                 { $$ = new ParserVal($1.sval); }
	| CONST declaration_specifiers                                                          { $$ = new ParserVal($1.sval+" "+$2.sval); }
	;

init_declarator_list
	: init_declarator                                                                       { $$ = Translator.init_declarator_list1($1); }
	| init_declarator_list COMMA init_declarator                                            { $$ = Translator.init_declarator_list2($1,$3); }
	;

init_declarator
	: declarator                                                                            { $$ = Translator.init_declarator1($1); }
	| declarator EQUAL initializer                                                          { $$ = Translator.init_declarator2($1,$3); }
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
	| INT                                                                   { $$ = new ParserVal("INT"); }
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
	| direct_declarator                                                 { $$ = Translator.declarator2($1); }
	;

direct_declarator
	: IDENTIFIER                                                        { $$ = new ParserVal($1.sval); }
	| RBLEFT declarator RBRIGHT
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT
	| direct_declarator BRACKETLEFT BRACKETRIGHT
	| direct_declarator RBLEFT parameter_type_list RBRIGHT
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
	: parameter_list
	| parameter_list COMMA ELLIPSIS
	;

parameter_list
	: parameter_declaration
	| parameter_list COMMA parameter_declaration
	;

parameter_declaration
	: declaration_specifiers declarator
	| declaration_specifiers abstract_declarator
	| declaration_specifiers
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
	: assignment_expression                                                                     { $$ = Translator.initializer1($1); }
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
	: external_declaration                                  { $$ = Translator.translation_unit($1); System.out.println($$.sval); }
	| translation_unit external_declaration                 { $$ = Translator.translation_unit($2); System.out.println($$.sval); }
	;

external_declaration
	: function_definition                                   { $$ = Translator.external_declaration($1); }
	| declaration                                           { $$ = Translator.external_declaration($1); }
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

  public static void ParseFile(String file) throws IOException {
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
  }
