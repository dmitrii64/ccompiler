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
	: IDENTIFIER                { $$ = new PrimaryExpression($1); }
	| CONSTANT                  { $$ = new PrimaryExpression($1); }
	| STRING_LITERAL            { $$ = new PrimaryExpression($1); }
	| RBLEFT expression RBRIGHT { $$ = new PrimaryExpression((Expression)$2); }
	;

postfix_expression
	: primary_expression                                         { $$ = new PostfixExpression((PrimaryExpression)$1); }
	| postfix_expression BRACKETLEFT expression BRACKETRIGHT     { $$ = new PostfixExpression((PostfixExpression)$1, (Expression)$3); }
	| postfix_expression RBLEFT RBRIGHT                          { $$ = new PostfixExpression((PostfixExpression)$1); }
	| postfix_expression RBLEFT argument_expression_list RBRIGHT { $$ = new PostfixExpression((PostfixExpression)$1, (ArgumentExpressionList)$3); }
	| postfix_expression DOT IDENTIFIER                          { $$ = new PostfixExpression((PostfixExpression)$1, $3); }
	| postfix_expression PTR_OP IDENTIFIER                       { $$ = new PostfixExpression((PostfixExpression)$1, $3); }
	| postfix_expression INC_OP                                  { $$ = new PostfixExpression((PostfixExpression)$1); }
	| postfix_expression DEC_OP                                  { $$ = new PostfixExpression((PostfixExpression)$1); }
	;

argument_expression_list
	: assignment_expression                                { $$ = new ArgumentExpressionList((AssignmentExpression)$1); }
	| argument_expression_list COMMA assignment_expression { $$ = new ArgumentExpressionList((ArgumentExpressionList)$1, (AssignmentExpression)$3); }
	;

unary_expression
	: postfix_expression              { $$ = new UnaryExpression((PostfixExpression)$1); }
	| INC_OP unary_expression         { $$ = new UnaryExpression((UnaryExpression)$2); }
	| DEC_OP unary_expression         { $$ = new UnaryExpression((UnaryExpression)$2); }
	| unary_operator cast_expression  { $$ = new UnaryExpression((UnaryOperator)$1, (CastExpression)$2); }
	| SIZEOF unary_expression         { $$ = new UnaryExpression((UnaryExpression)$2); }
	| SIZEOF RBLEFT type_name RBRIGHT { $$ = new UnaryExpression((TypeName)$3); }
	;

unary_operator
	: AMP   { $$ = new UnaryOperator(); }
	| STAR  { $$ = new UnaryOperator(); }
	| PLUS  { $$ = new UnaryOperator(); }
	| MINUS { $$ = new UnaryOperator(); }
	| EXCL  { $$ = new UnaryOperator(); }
	;

cast_expression
	: unary_expression                         { $$ = new CastExpression((UnaryExpression)$1); }
	| RBLEFT type_name RBRIGHT cast_expression { $$ = new CastExpression((TypeName)$2, (CastExpression)$4); }
	;

multiplicative_expression
	: cast_expression                                   { $$ = new MultiplicativeExpression((CastExpression)$1); }
	| multiplicative_expression STAR cast_expression    { $$ = new MultiplicativeExpression((MultiplicativeExpression)$1, (CastExpression)$3); }
	| multiplicative_expression SLASH cast_expression   { $$ = new MultiplicativeExpression((MultiplicativeExpression)$1, (CastExpression)$3); }
	| multiplicative_expression PERCENT cast_expression { $$ = new MultiplicativeExpression((MultiplicativeExpression)$1, (CastExpression)$3); }
	;

additive_expression
	: multiplicative_expression                           { $$ = new AdditiveExpression((MultiplicativeExpression)$1); }
	| additive_expression PLUS multiplicative_expression  { $$ = new AdditiveExpression((AdditiveExpression)$1, (MultiplicativeExpression)$3); }
	| additive_expression MINUS multiplicative_expression { $$ = new AdditiveExpression((AdditiveExpression)$1, (MultiplicativeExpression)$3); }
	;

shift_expression
	: additive_expression                           { $$ = new ShiftExpression((AdditiveExpression)$1); }
	| shift_expression LEFT_OP additive_expression  { $$ = new ShiftExpression((ShiftExpression)$1, (AdditiveExpression)$3); }
	| shift_expression RIGHT_OP additive_expression { $$ = new ShiftExpression((ShiftExpression)$1, (AdditiveExpression)$3); }
	;

relational_expression
	: shift_expression                               { $$ = new RelationalExpression((ShiftExpression)$1); }
	| relational_expression LESS shift_expression    { $$ = new RelationalExpression((RelationalExpression)$1, (ShiftExpression)$3); }
	| relational_expression GREATER shift_expression { $$ = new RelationalExpression((RelationalExpression)$1, (ShiftExpression)$3); }
	| relational_expression LE_OP shift_expression   { $$ = new RelationalExpression((RelationalExpression)$1, (ShiftExpression)$3); }
	| relational_expression GE_OP shift_expression   { $$ = new RelationalExpression((RelationalExpression)$1, (ShiftExpression)$3); }
	;

equality_expression
	: relational_expression                           { $$ = new EqualityExpression((RelationalExpression)$1); }
	| equality_expression EQ_OP relational_expression { $$ = new EqualityExpression((EqualityExpression)$1, (RelationalExpression)$3); }
	| equality_expression NE_OP relational_expression { $$ = new EqualityExpression((EqualityExpression)$1, (RelationalExpression)$3); }
	;

and_expression
	: equality_expression                    { $$ = new AndExpression((EqualityExpression)$1); }
	| and_expression AMP equality_expression { $$ = new AndExpression((AndExpression)$1, (EqualityExpression)$3); }
	;

exclusive_or_expression
	: and_expression                               { $$ = new ExclusiveOrExpression((AndExpression)$1); }
	| exclusive_or_expression CARET and_expression { $$ = new ExclusiveOrExpression((ExclusiveOrExpression)$1, (AndExpression)$3); }
	;

inclusive_or_expression
	: exclusive_or_expression                             { $$ = new InclusiveOrExpression((ExclusiveOrExpression)$1); }
	| inclusive_or_expression BAR exclusive_or_expression { $$ = new InclusiveOrExpression((InclusiveOrExpression)$1, (ExclusiveOrExpression)$3); }
	;

logical_and_expression
	: inclusive_or_expression                               { $$ = new LogicalAndExpression((InclusiveOrExpression)$1); }
	| logical_and_expression AND_OP inclusive_or_expression { $$ = new LogicalAndExpression((LogicalAndExpression)$1, (InclusiveOrExpression)$3); }
	;

logical_or_expression
	: logical_and_expression                             { $$ = new LogicalOrExpression((LogicalAndExpression)$1); }
	| logical_or_expression OR_OP logical_and_expression { $$ = new LogicalOrExpression((LogicalOrExpression)$1, (LogicalAndExpression)$3); }
	;

conditional_expression
	: logical_or_expression                                                  { $$ = new ConditionalExpression((LogicalOrExpression)$1); }
	| logical_or_expression QUESTION expression COLON conditional_expression { $$ = new ConditionalExpression((LogicalOrExpression)$1, (Expression)$3, (ConditionalExpression)$5); }
	;

assignment_expression
	: conditional_expression                       { $$ = new AssignmentExpression((ConditionalExpression)$1); }
	| unary_expression EQUAL assignment_expression { $$ = new AssignmentExpression((UnaryExpression)$1, (AssignmentExpression)$3); }
	;

expression
	: assignment_expression                  { $$ = new Expression((AssignmentExpression)$1); }
	| expression COMMA assignment_expression { $$ = new Expression((Expression)$1, (AssignmentExpression)$3); }
	;

constant_expression
	: conditional_expression { $$ = new ConstantExpression((ConditionalExpression)$1); }
	;

declaration
	: declaration_specifiers SEMICOLON                      { $$ = new Declaration((DeclarationSpecifiers)$1); }
	| declaration_specifiers init_declarator_list SEMICOLON { $$ = new Declaration((DeclarationSpecifiers)$1, (InitDeclaratorList)$2); }
	;

declaration_specifiers
	: storage_class_specifier                        { $$ = new DeclarationSpecifiers((StorageClassSpecifier)$1); }
	| storage_class_specifier declaration_specifiers { $$ = new DeclarationSpecifiers((StorageClassSpecifier)$1, (DeclarationSpecifiers)$2); }
	| type_specifier                                 { $$ = new DeclarationSpecifiers((TypeSpecifier)$1); }
	| type_specifier declaration_specifiers          { $$ = new DeclarationSpecifiers((TypeSpecifier)$1, (DeclarationSpecifiers)$2); }
	| CONST                                          { $$ = new DeclarationSpecifiers(); }
	| CONST declaration_specifiers                   { $$ = new DeclarationSpecifiers((DeclarationSpecifiers)$2); }
	;

init_declarator_list
	: init_declarator                            { $$ = new InitDeclaratorList((InitDeclarator)$1); }
	| init_declarator_list COMMA init_declarator { $$ = new InitDeclaratorList((InitDeclaratorList)$1, (InitDeclarator)$3); }
	;

init_declarator
	: declarator                   { $$ = new InitDeclarator((Declarator)$1); }
	| declarator EQUAL initializer { $$ = new InitDeclarator((Declarator)$1, (Initializer)$3); }
	;

storage_class_specifier
	: TYPEDEF  { $$ = new StorageClassSpecifier(); }
	| STATIC   { $$ = new StorageClassSpecifier(); }
	| AUTO     { $$ = new StorageClassSpecifier(); }
	| REGISTER { $$ = new StorageClassSpecifier(); }
	;

type_specifier
	: VOID      { $$ = new TypeSpecifier(); }
	| CHAR      { $$ = new TypeSpecifier(); }
	| SHORT     { $$ = new TypeSpecifier(); }
	| INT       { $$ = new TypeSpecifier(); }
	| LONG      { $$ = new TypeSpecifier(); }
	| FLOAT     { $$ = new TypeSpecifier(); }
	| DOUBLE    { $$ = new TypeSpecifier(); }
	| SIGNED    { $$ = new TypeSpecifier(); }
	| UNSIGNED  { $$ = new TypeSpecifier(); }
	| TYPE_NAME { $$ = new TypeSpecifier(); }
	;

specifier_qualifier_list
	: type_specifier specifier_qualifier_list { $$ = new SpecifierQualifierList((TypeSpecifier)$1, (SpecifierQualifierList)$2); }
	| type_specifier                          { $$ = new SpecifierQualifierList((TypeSpecifier)$1); }
	| CONST specifier_qualifier_list          { $$ = new SpecifierQualifierList((SpecifierQualifierList)$2); }
	| CONST                                   { $$ = new SpecifierQualifierList(); }
	;

declarator
	: pointer direct_declarator { $$ = new Declarator((Pointer)$1, (DirectDeclarator)$2); }
	| direct_declarator         { $$ = new Declarator((DirectDeclarator)$1); }
	;

direct_declarator
	: IDENTIFIER                                                     { $$ = new DirectDeclarator($1); }
	| RBLEFT declarator RBRIGHT                                      { $$ = new DirectDeclarator((Declarator)$2); }
	| direct_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new DirectDeclarator((DirectDeclarator)$1, (ConstantExpression)$3); }
	| direct_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new DirectDeclarator((DirectDeclarator)$1); }
	| direct_declarator RBLEFT parameter_type_list RBRIGHT           { $$ = new DirectDeclarator((DirectDeclarator)$1, (ParameterTypeList)$3); }
	| direct_declarator RBLEFT identifier_list RBRIGHT               { $$ = new DirectDeclarator((DirectDeclarator)$1, (IdentifierList)$3); }
	| direct_declarator RBLEFT RBRIGHT                               { $$ = new DirectDeclarator((DirectDeclarator)$1); }
	;

pointer
	: STAR               { $$ = new Pointer(); }
	| STAR CONST         { $$ = new Pointer(); }
	| STAR pointer       { $$ = new Pointer((Pointer)$2); }
	| STAR CONST pointer { $$ = new Pointer((Pointer)$3); }
	;

parameter_type_list
	: parameter_list                { $$ = new ParameterTypeList((ParameterList)$1); }
	| parameter_list COMMA ELLIPSIS { $$ = new ParameterTypeList((ParameterList)$1); }
	;

parameter_list
	: parameter_declaration                      { $$ = new ParameterList((ParameterDeclaration)$1); }
	| parameter_list COMMA parameter_declaration { $$ = new ParameterList((ParameterList)$1, (ParameterDeclaration)$3); }
	;

parameter_declaration
	: declaration_specifiers declarator          { $$ = new ParameterDeclaration((DeclarationSpecifiers)$1, (Declarator)$2); }
	| declaration_specifiers abstract_declarator { $$ = new ParameterDeclaration((DeclarationSpecifiers)$1, (AbstractDeclarator)$2); }
	| declaration_specifiers                     { $$ = new ParameterDeclaration((DeclarationSpecifiers)$1); }
	;

identifier_list
	: IDENTIFIER                       { $$ = new IdentifierList($1); }
	| identifier_list COMMA IDENTIFIER { $$ = new IdentifierList((IdentifierList)$1, $3); }
	;

type_name
	: specifier_qualifier_list                     { $$ = new TypeName((SpecifierQualifierList)$1); }
	| specifier_qualifier_list abstract_declarator { $$ = new TypeName((SpecifierQualifierList)$1, (AbstractDeclarator)$2); }
	;

abstract_declarator
	: pointer                            { $$ = new AbstractDeclarator((Pointer)$1); }
	| direct_abstract_declarator         { $$ = new AbstractDeclarator((DirectAbstractDeclarator)$1); }
	| pointer direct_abstract_declarator { $$ = new AbstractDeclarator((Pointer)$1, (DirectAbstractDeclarator)$2); }
	;

direct_abstract_declarator
	: RBLEFT abstract_declarator RBRIGHT                                      { $$ = new DirectAbstractDeclarator((AbstractDeclarator)$2); }
	| BRACKETLEFT BRACKETRIGHT                                                { $$ = new DirectAbstractDeclarator(); }
	| BRACKETLEFT constant_expression BRACKETRIGHT                            { $$ = new DirectAbstractDeclarator((ConstantExpression)$2); }
	| direct_abstract_declarator BRACKETLEFT BRACKETRIGHT                     { $$ = new DirectAbstractDeclarator((DirectAbstractDeclarator)$1); }
	| direct_abstract_declarator BRACKETLEFT constant_expression BRACKETRIGHT { $$ = new DirectAbstractDeclarator((DirectAbstractDeclarator)$1, (ConstantExpression)$3); }
	| RBLEFT RBRIGHT                                                          { $$ = new DirectAbstractDeclarator(); }
	| RBLEFT parameter_type_list RBRIGHT                                      { $$ = new DirectAbstractDeclarator((ParameterTypeList)$2); }
	| direct_abstract_declarator RBLEFT RBRIGHT                               { $$ = new DirectAbstractDeclarator((DirectAbstractDeclarator)$1); }
	| direct_abstract_declarator RBLEFT parameter_type_list RBRIGHT           { $$ = new DirectAbstractDeclarator((DirectAbstractDeclarator)$1, (ParameterTypeList)$3); }
	;

initializer
	: assignment_expression                       { $$ = new Initializer((AssignmentExpression)$1); }
	| BRACELEFT initializer_list BRACERIGHT       { $$ = new Initializer((InitializerList)$2); }
	| BRACELEFT initializer_list COMMA BRACERIGHT { $$ = new Initializer((InitializerList)$2); }
	;

initializer_list
	: initializer                        { $$ = new InitializerList((Initializer)$1); }
	| initializer_list COMMA initializer { $$ = new InitializerList((InitializerList)$1, (Initializer)$3); }
	;

statement
	: labeled_statement    { $$ = new Statement((LabeledStatement)$1); }
	| compound_statement   { $$ = new Statement((CompoundStatement)$1); }
	| expression_statement { $$ = new Statement((ExpressionStatement)$1); }
	| selection_statement  { $$ = new Statement((SelectionStatement)$1); }
	| iteration_statement  { $$ = new Statement((IterationStatement)$1); }
	| jump_statement       { $$ = new Statement((JumpStatement)$1); }
	;

labeled_statement
	: IDENTIFIER COLON statement               { $$ = new LabeledStatement($1, (Statement)$3); }
	| CASE constant_expression COLON statement { $$ = new LabeledStatement((ConstantExpression)$2, (Statement)$4); }
	| DEFAULT COLON statement                  { $$ = new LabeledStatement((Statement)$3); }
	;

compound_statement
	: BRACELEFT BRACERIGHT                                 { $$ = new CompoundStatement(); }
	| BRACELEFT statement_list BRACERIGHT                  { $$ = new CompoundStatement((StatementList)$2); }
	| BRACELEFT declaration_list BRACERIGHT                { $$ = new CompoundStatement((DeclarationList)$2); }
	| BRACELEFT declaration_list statement_list BRACERIGHT { $$ = new CompoundStatement((DeclarationList)$2, (StatementList)$3); }
	;

declaration_list
	: declaration                  { $$ = new DeclarationList((Declaration)$1); }
	| declaration_list declaration { $$ = new DeclarationList((DeclarationList)$1, (Declaration)$2); }
	;

statement_list
	: statement                { $$ = new StatementList((Statement)$1); }
	| statement_list statement { $$ = new StatementList((StatementList)$1, (Statement)$2); }
	;

expression_statement
	: SEMICOLON            { $$ = new ExpressionStatement(); }
	| expression SEMICOLON { $$ = new ExpressionStatement((Expression)$1); }
	;

selection_statement
	: IF RBLEFT expression RBRIGHT statement ELSE statement { $$ = new SelectionStatement((Expression)$3, (Statement)$5, (Statement)$7); }
	| SWITCH RBLEFT expression RBRIGHT statement            { $$ = new SelectionStatement((Expression)$3, (Statement)$5); }
	;

iteration_statement
	: WHILE RBLEFT expression RBRIGHT statement                                         { $$ = new IterationStatement((Expression)$3, (Statement)$5); }
	| DO statement WHILE RBLEFT expression RBRIGHT SEMICOLON                            { $$ = new IterationStatement((Statement)$2, (Expression)$5); }
	| FOR RBLEFT expression_statement expression_statement RBRIGHT statement            { $$ = new IterationStatement((ExpressionStatement)$3, (ExpressionStatement)$4, (Statement)$6); }
	| FOR RBLEFT expression_statement expression_statement expression RBRIGHT statement { $$ = new IterationStatement((ExpressionStatement)$3, (ExpressionStatement)$4, (Expression)$5, (Statement)$7); }
	;

jump_statement
	: GOTO IDENTIFIER SEMICOLON   { $$ = new JumpStatement($2); }
	| BREAK SEMICOLON             { $$ = new JumpStatement(); }
	| RETURN SEMICOLON            { $$ = new JumpStatement(); }
	| RETURN expression SEMICOLON { $$ = new JumpStatement((Expression)$2); }
	;

translation_unit
	: external_declaration                  { $$ = new TranslationUnit((ExternalDeclaration)$1); }
	| translation_unit external_declaration { $$ = new TranslationUnit((TranslationUnit)$1, (ExternalDeclaration)$2); }
	;

external_declaration
	: function_definition { $$ = new ExternalDeclaration((FunctionDefinition)$1); }
	| declaration         { $$ = new ExternalDeclaration((Declaration)$1); }
	;

function_definition
	: declaration_specifiers declarator declaration_list compound_statement { $$ = new FunctionDefinition((DeclarationSpecifiers)$1, (Declarator)$2, (DeclarationList)$3, (CompoundStatement)$4); }
	| declaration_specifiers declarator compound_statement                  { $$ = new FunctionDefinition((DeclarationSpecifiers)$1, (Declarator)$2, (CompoundStatement)$3); }
	| declarator declaration_list compound_statement                        { $$ = new FunctionDefinition((Declarator)$1, (DeclarationList)$2, (CompoundStatement)$3); }
	| declarator compound_statement                                         { $$ = new FunctionDefinition((Declarator)$1, (CompoundStatement)$2); }
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
