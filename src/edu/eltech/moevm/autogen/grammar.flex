/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package edu.eltech.moevm.autogen;

%%

/* This is not an error! */
%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

D = [0-9]
L = [a-zA-Z_]
H = [a-fA-F0-9]
E = [Ee][+-]?{D}+
FS = (f|F|l|L)
IS = (u|U|l|L)*

%%

"/*"			{  }

"auto"			{ return Parser.AUTO; }
"break"			{ return Parser.BREAK; }
"case"			{ return Parser.CASE; }
"char"			{ return Parser.CHAR; }
"const"			{ return Parser.CONST; }
"continue"		{ return Parser.CONTINUE; }
"default"		{ return Parser.DEFAULT; }
"do"			{ return Parser.DO; }
"double"		{ return Parser.DOUBLE; }
"else"			{ return Parser.ELSE; }
"enum"			{ return Parser.ENUM; }
"extern"		{ return Parser.EXTERN; }
"float"			{ return Parser.FLOAT; }
"for"			{ return Parser.FOR; }
"goto"			{ return Parser.GOTO; }
"if"			{ return Parser.IF; }
"int"			{ return Parser.INT; }
"long"			{ return Parser.LONG; }
"register"		{ return Parser.REGISTER; }
"return"		{ return Parser.RETURN; }
"short"			{ return Parser.SHORT; }
"signed"		{ return Parser.SIGNED; }
"sizeof"		{ return Parser.SIZEOF; }
"static"		{ return Parser.STATIC; }
"struct"		{ return Parser.STRUCT; }
"switch"		{ return Parser.SWITCH; }
"typedef"		{ return Parser.TYPEDEF; }
"union"			{ return Parser.UNION; }
"unsigned"		{ return Parser.UNSIGNED; }
"void"			{ return Parser.VOID; }
"volatile"		{ return Parser.VOLATILE; }
"while"			{ return Parser.WHILE; }

{L}({L}|{D})*		{  }

0[xX]{H}+{IS}?		{ return Parser.CONSTANT; }
0{D}+{IS}?		{ return Parser.CONSTANT; }
{D}+{IS}?		{ return Parser.CONSTANT; }
L?'(\\.|[^\\'])+'	{ return Parser.CONSTANT; }

{D}+{E}{FS}?		{ return Parser.CONSTANT; }
{D}*"."{D}+({E})?{FS}?	{ return Parser.CONSTANT; }
{D}+"."{D}*({E})?{FS}?	{ return Parser.CONSTANT; }

\"([^\\\"]|\\.)*\" { return Parser.STRING_LITERAL; }

"..."			{ return Parser.ELLIPSIS; }
">>="			{ return Parser.RIGHT_ASSIGN; }
"<<="			{ return Parser.LEFT_ASSIGN; }
"+="			{ return Parser.ADD_ASSIGN; }
"-="			{ return Parser.SUB_ASSIGN; }
"*="			{ return Parser.MUL_ASSIGN; }
"/="			{ return Parser.DIV_ASSIGN; }
"%="			{ return Parser.MOD_ASSIGN; }
"&="			{ return Parser.AND_ASSIGN; }
"^="			{ return Parser.XOR_ASSIGN; }
"|="			{ return Parser.OR_ASSIGN; }
">>"			{ return Parser.RIGHT_OP; }
"<<"			{ return Parser.LEFT_OP; }
"++"			{ return Parser.INC_OP; }
"--"			{ return Parser.DEC_OP; }
"->"			{ return Parser.PTR_OP; }
"&&"			{ return Parser.AND_OP; }
"||"			{ return Parser.OR_OP; }
"<="			{ return Parser.LE_OP; }
">="			{ return Parser.GE_OP; }
"=="			{ return Parser.EQ_OP; }
"!="			{ return Parser.NE_OP; }
";"			{ return Parser.SEMICOLON; }
"\{"		{ return Parser.BRACELEFT; }
"\}"		{ return Parser.BRACERIGHT; }
","			{ return Parser.COMMA; }
":"			{ return Parser.COLON; }
"="			{ return Parser.EQUAL; }
"("			{ return Parser.RBLEFT; }
")"			{ return Parser.RBRIGHT; }
("["|"<:")		{ return Parser.BRACKETLEFT; }
("]"|":>")		{ return Parser.BRACKETRIGHT; }
"."			{ return Parser.DOT; }
"&"			{ return Parser.AMP; }
"!"			{ return Parser.EXCL; }
"~"			{ return Parser.TILDE; }
"-"			{ return Parser.MINUS; }
"+"			{ return Parser.PLUS; }
"*"			{ return Parser.STAR; }
"/"			{ return Parser.SLASH; }
"%"			{ return Parser.PERCENT; }
"<"			{ return Parser.LESS; }
">"			{ return Parser.GREATER; }
"^"			{ return Parser.CARET; }
"|"			{ return Parser.BAR; }
"?"			{ return Parser.QUESTION; }

[ \t\v\n\f]		{  }
.			{ /* ignore bad characters */ }
