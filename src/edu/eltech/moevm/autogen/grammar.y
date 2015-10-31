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
  import edu.eltech.moevm.grammar.*;
%}
      
%token IDENTIFIER CONSTANT STRING_LITERAL SIZEOF
%token PTR_OP INC_OP DEC_OP LEFT_OP RIGHT_OP LE_OP GE_OP EQ_OP NE_OP
%token AND_OP OR_OP

%token STATIC
%token CHAR SHORT INT LONG FLOAT DOUBLE CONST VOID COMPLEX BOOL

%token RE IM MOD

%token CASE DEFAULT IF ELSE SWITCH WHILE DO FOR GOTO BREAK RETURN

%token SEMICOLON BRACELEFT BRACERIGHT COMMA COLON EQUAL RBLEFT RBRIGHT BRACKETLEFT
%token BRACKETRIGHT DOT AMP EXCL MINUS PLUS STAR SLASH PERCENT LESS GREATER
%token CARET BAR QUESTION

%start translation_unit
%%

primary_expression
	: IDENTIFIER                { System.out.println("PrimaryExpression ID = "+$1.sval); }
	| CONSTANT                  { System.out.println("PrimaryExpression"); }
	| STRING_LITERAL            { System.out.println("PrimaryExpression"); }
	| RBLEFT expression RBRIGHT { System.out.println("PrimaryExpression"); }
	;

postfix_expression
	: primary_expression                                         { System.out.println("PostfixExpression"); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { System.out.println("PostfixExpression"); }
	| postfix_expression RBLEFT RBRIGHT                          { System.out.println("PostfixExpression"); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { System.out.println("PostfixExpression"); }
	| postfix_expression DOT IDENTIFIER                          { System.out.println("PostfixExpression"); }
	| postfix_expression PTR_OP IDENTIFIER                       { System.out.println("PostfixExpression"); }
	| postfix_expression INC_OP                                  { System.out.println("PostfixExpression"); }
	| postfix_expression DEC_OP                                  { System.out.println("PostfixExpression"); }
	;

argument_expression_list
	: assignment_expression                                { System.out.println("ArgumentExpressionList"); }
	| argument_expression_list COMMA assignment_expression { System.out.println("ArgumentExpressionList"); }
	;

unary_expression
	: postfix_expression              { System.out.println("UnaryExpression"); }
	| INC_OP unary_expression         { System.out.println("UnaryExpression"); }
	| DEC_OP unary_expression         { System.out.println("UnaryExpression"); }
	| unary_operator cast_expression  { System.out.println("UnaryExpression"); }
	| SIZEOF unary_expression         { System.out.println("UnaryExpression"); }
	| SIZEOF RBLEFT type_name RBRIGHT { System.out.println("UnaryExpression"); }
	| RE RBLEFT CONSTANT RBRIGHT      { System.out.println("UnaryExpression"); }
	| RE RBLEFT IDENTIFIER RBRIGHT    { System.out.println("UnaryExpression"); }
	| IM RBLEFT CONSTANT RBRIGHT      { System.out.println("UnaryExpression"); }
	| IM RBLEFT IDENTIFIER RBRIGHT    { System.out.println("UnaryExpression"); }
	| MOD RBLEFT CONSTANT RBRIGHT     { System.out.println("UnaryExpression"); }
	| MOD RBLEFT IDENTIFIER RBRIGHT   { System.out.println("UnaryExpression"); }
	;

unary_operator
	: AMP   { System.out.println("UnaryOperator"); }
	| STAR  { System.out.println("UnaryOperator"); }
	| PLUS  { System.out.println("UnaryOperator"); }
	| MINUS { System.out.println("UnaryOperator"); }
	| EXCL  { System.out.println("UnaryOperator"); }
	;

cast_expression
	: unary_expression                         { System.out.println("CastExpression"); }
	| RBLEFT type_name RBRIGHT cast_expression { System.out.println("CastExpression"); }
	;

multiplicative_expression
	: cast_expression                                   { System.out.println("MultiplicativeExpression"); }
	| multiplicative_expression STAR cast_expression    { System.out.println("MultiplicativeExpression"); }
	| multiplicative_expression SLASH cast_expression   { System.out.println("MultiplicativeExpression"); }
	| multiplicative_expression PERCENT cast_expression { System.out.println("MultiplicativeExpression"); }
	;

additive_expression
	: multiplicative_expression                           { System.out.println("AdditiveExpression"); }
	| additive_expression PLUS multiplicative_expression  { System.out.println("AdditiveExpression"); }
	| additive_expression MINUS multiplicative_expression { System.out.println("AdditiveExpression"); }
	;

shift_expression
	: additive_expression                           { System.out.println("ShiftExpression"); }
	| shift_expression LEFT_OP additive_expression  { System.out.println("ShiftExpression"); }
	| shift_expression RIGHT_OP additive_expression { System.out.println("ShiftExpression"); }
	;

relational_expression
	: shift_expression                               { System.out.println("RelationalExpression"); }
	| relational_expression LESS shift_expression    { System.out.println("RelationalExpression"); }
	| relational_expression GREATER shift_expression { System.out.println("RelationalExpression"); }
	| relational_expression LE_OP shift_expression   { System.out.println("RelationalExpression"); }
	| relational_expression GE_OP shift_expression   { System.out.println("RelationalExpression"); }
	;

equality_expression
	: relational_expression                           { System.out.println("EqualityExpression"); }
	| equality_expression EQ_OP relational_expression { System.out.println("EqualityExpression"); }
	| equality_expression NE_OP relational_expression { System.out.println("EqualityExpression"); }
	;

and_expression
	: equality_expression                    { System.out.println("AndExpression"); }
	| and_expression AMP equality_expression { System.out.println("AndExpression"); }
	;

exclusive_or_expression
	: and_expression                               { System.out.println("ExclusiveOrExpression"); }
	| exclusive_or_expression CARET and_expression { System.out.println("ExclusiveOrExpression"); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { System.out.println("InclusiveOrExpression"); }
	| inclusive_or_expression BAR exclusive_or_expression { System.out.println("InclusiveOrExpression"); }
	;

logical_and_expression
	: inclusive_or_expression                               { System.out.println("LogicalAndExpression"); }
	| logical_and_expression AND_OP inclusive_or_expression { System.out.println("LogicalAndExpression"); }
	;

logical_or_expression
	: logical_and_expression                             { System.out.println("LogicalOrExpression"); }
	| logical_or_expression OR_OP logical_and_expression { System.out.println("LogicalOrExpression"); }
	;

conditional_expression
	: logical_or_expression                                                  { System.out.println("ConditionalExpression"); }
	| logical_or_expression QUESTION expression COLON conditional_expression { System.out.println("ConditionalExpression"); }
	;

assignment_expression
	: conditional_expression                       { System.out.println("AssignmentExpression"); }
	| unary_expression EQUAL assignment_expression { System.out.println("AssignmentExpression"); }
	;

expression
	: assignment_expression                  { System.out.println("Expression"); }
	| expression COMMA assignment_expression { System.out.println("Expression"); }
	;

constant_expression
	: conditional_expression { System.out.println("ConstantExpression"); }
	;

declaration
	: declaration_specifiers SEMICOLON                      { System.out.println("Declaration"); }
	| declaration_specifiers init_declarator_list SEMICOLON { System.out.println("Declaration"); }
	;

declaration_specifiers
	: storage_class_specifier                        { System.out.println("DeclarationSpecifiers"); }
	| storage_class_specifier declaration_specifiers { System.out.println("DeclarationSpecifiers"); }
	| type_specifier                                 { System.out.println("DeclarationSpecifiers"); }
	| type_specifier declaration_specifiers          { System.out.println("DeclarationSpecifiers"); }
	| CONST                                          { System.out.println("DeclarationSpecifiers"); }
	| CONST declaration_specifiers                   { System.out.println("DeclarationSpecifiers"); }
	;

init_declarator_list
	: init_declarator                            { System.out.println("InitDeclaratorList"); }
	| init_declarator_list COMMA init_declarator { System.out.println("InitDeclaratorList"); }
	;

init_declarator
	: direct_declarator                   { System.out.println("InitDeclarator"); }
	| direct_declarator EQUAL initializer { System.out.println("InitDeclarator"); }
	;

storage_class_specifier
	: STATIC   { System.out.println("StorageClassSpecifier"); }
	;

type_specifier
	: VOID      { System.out.println("TypeSpecifier VOID"); }
	| COMPLEX   { System.out.println("TypeSpecifier COMPLEX"); }
	| CHAR      { System.out.println("TypeSpecifier"); }
	| SHORT     { System.out.println("TypeSpecifier"); }
	| INT       { System.out.println("TypeSpecifier INT"); }
	| LONG      { System.out.println("TypeSpecifier"); }
	| FLOAT     { System.out.println("TypeSpecifier"); }
	| DOUBLE    { System.out.println("TypeSpecifier"); }
	| BOOL      { System.out.println("TypeSpecifier BOOL"); }
	;

specifier_qualifier_list
	: type_specifier specifier_qualifier_list { System.out.println("SpecifierQualifierList"); }
	| type_specifier                          { System.out.println("SpecifierQualifierList"); }
	| CONST specifier_qualifier_list          { System.out.println("SpecifierQualifierList"); }
	| CONST                                   { System.out.println("SpecifierQualifierList"); }
	;

direct_declarator
	: IDENTIFIER                                                     { System.out.println("DirectDeclarator ID = "+$1.sval); }
	| RBLEFT direct_declarator RBRIGHT                                      { System.out.println("DirectDeclarator"); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { System.out.println("DirectDeclarator"); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { System.out.println("DirectDeclarator"); }
	| direct_declarator RBLEFT parameter_list RBRIGHT           { System.out.println("DirectDeclarator"); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { System.out.println("DirectDeclarator"); }
	| direct_declarator RBLEFT RBRIGHT                               { System.out.println("DirectDeclarator"); }
	;

parameter_list
	: parameter_declaration                      { System.out.println("ParameterList"); }
	| parameter_list COMMA parameter_declaration { System.out.println("ParameterList"); }
	;

parameter_declaration
	: declaration_specifiers direct_declarator          { System.out.println("ParameterDeclaration"); }
	| declaration_specifiers abstract_declarator { System.out.println("ParameterDeclaration"); }
	| declaration_specifiers                     { System.out.println("ParameterDeclaration"); }
	;

identifier_list
	: IDENTIFIER                       { System.out.println("IdentifierList"); }
	| identifier_list COMMA IDENTIFIER { System.out.println("IdentifierList"); }
	;

type_name
	: specifier_qualifier_list                     { System.out.println("TypeName"); }
	| specifier_qualifier_list abstract_declarator { System.out.println("TypeName"); }
	;

abstract_declarator
	: direct_abstract_declarator         { System.out.println("AbstractDeclarator"); }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { System.out.println("DirectAbstractDeclarator"); }
	| BRACKETLEFT BRACKETRIGHT                                                { System.out.println("DirectAbstractDeclarator"); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { System.out.println("DirectAbstractDeclarator"); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { System.out.println("DirectAbstractDeclarator"); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { System.out.println("DirectAbstractDeclarator"); }
	| RBLEFT RBRIGHT                                                          { System.out.println("DirectAbstractDeclarator"); }
	| RBLEFT parameter_list RBRIGHT                                      { System.out.println("DirectAbstractDeclarator"); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { System.out.println("DirectAbstractDeclarator"); }
	| direct_abstract_declarator RBLEFT parameter_list RBRIGHT           { System.out.println("DirectAbstractDeclarator"); }
	;

initializer
	: assignment_expression                       { System.out.println("Initializer"); }
	| BRACELEFT initializer_list BRACERIGHT       { System.out.println("Initializer"); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { System.out.println("Initializer"); }
	;

initializer_list
	: initializer                        { System.out.println("InitializerList"); }
	| initializer_list COMMA initializer { System.out.println("InitializerList"); }
	;

statement
	: labeled_statement    { System.out.println("Statement"); }
	| compound_statement   { System.out.println("Statement"); }
	| expression_statement { System.out.println("Statement"); }
	| selection_statement  { System.out.println("Statement"); }
	| iteration_statement  { System.out.println("Statement"); }
	| jump_statement       { System.out.println("Statement"); }
	;

labeled_statement
	: IDENTIFIER COLON statement               { System.out.println("LabeledStatement"); }
	| CASE constant_expression COLON statement { System.out.println("LabeledStatement"); }
	| DEFAULT COLON statement                  { System.out.println("LabeledStatement"); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { System.out.println("CompoundStatement"); }
	| BRACELEFT statement_list BRACERIGHT                  { System.out.println("CompoundStatement"); }
	| BRACELEFT declaration_list BRACERIGHT                { System.out.println("CompoundStatement"); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { System.out.println("CompoundStatement"); }
	;

declaration_list
	: declaration                  { System.out.println("DeclarationList"); }
	| declaration_list declaration { System.out.println("DeclarationList"); }
	;

statement_list
	: statement                { System.out.println("StatementList"); }
	| statement_list statement { System.out.println("StatementList"); }
	;

expression_statement
	: SEMICOLON            { System.out.println("ExpressionStatement"); }
	| expression SEMICOLON { System.out.println("ExpressionStatement"); }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { System.out.println("SelectionStatement"); }
	| SWITCH RBLEFT expression RBRIGHT statement            { System.out.println("SelectionStatement"); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { System.out.println("IterationStatement"); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { System.out.println("IterationStatement"); }
	| FOR RBLEFT expression_statement expression_statement RBRIGHT statement            { System.out.println("IterationStatement"); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { System.out.println("IterationStatement"); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { System.out.println("JumpStatement"); }
	| BREAK SEMICOLON             { System.out.println("JumpStatement"); }
	| RETURN SEMICOLON            { System.out.println("JumpStatement"); }
	| RETURN expression SEMICOLON { System.out.println("JumpStatement"); }
	;

translation_unit
	: external_declaration                  { System.out.println("TranslationUnit"); $$ = new ParserVal("test"); }
	| translation_unit external_declaration { System.out.println("TranslationUnit"); }
	;

external_declaration
	: function_definition { System.out.println("ExternalDeclaration"); }
	| declaration         { System.out.println("ExternalDeclaration"); }
	;

function_definition
	: declaration_specifiers direct_declarator declaration_list compound_statement { System.out.println("FunctionDefinition"); }
	| declaration_specifiers direct_declarator compound_statement                  { System.out.println("FunctionDefinition"); }
	| direct_declarator declaration_list compound_statement                        { System.out.println("FunctionDefinition"); }
	| direct_declarator compound_statement                                         { System.out.println("FunctionDefinition"); }
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
  	System.out.print(yyparser.yyval.sval );
  }
