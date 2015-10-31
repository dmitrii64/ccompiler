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

"break"			{ return Parser.BREAK; }
"case"			{ return Parser.CASE; }
"char"			{ return Parser.CHAR; }
"complex"		{ return Parser.COMPLEX; }
"const"			{ return Parser.CONST; }
"default"		{ return Parser.DEFAULT; }
"do"			{ return Parser.DO; }
"double"		{ return Parser.DOUBLE; }
"else"			{ return Parser.ELSE; }
"float"			{ return Parser.FLOAT; }
"for"			{ return Parser.FOR; }
"goto"			{ return Parser.GOTO; }
"if"			{ return Parser.IF; }
"im"			{ return Parser.IM; }
"int"			{ return Parser.INT; }
"long"			{ return Parser.LONG; }
"mod"			{ return Parser.MOD; }
"re"			{ return Parser.RE; }
"return"		{ return Parser.RETURN; }
"short"			{ return Parser.SHORT; }
"sizeof"		{ return Parser.SIZEOF; }
"static"		{ return Parser.STATIC; }
"switch"		{ return Parser.SWITCH; }
"void"			{ return Parser.VOID; }
"while"			{ return Parser.WHILE; }

{L}({L}|{D})*	{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.IDENTIFIER; }

0[xX]{H}+{IS}?		{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
0{D}+{IS}?		{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
{D}+{IS}?		{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
L?'(\\.|[^\\'])+'	{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }

{D}+{E}{FS}?		{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
{D}*"."{D}+({E})?{FS}?	{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
{D}+"."{D}*({E})?{FS}?	{ if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }

/* Complex type */
((0[xX]{H}+{IS}?|0{D}+{IS}?|{D}+{IS}?|{D}+{E}{FS}?|{D}*"."{D}+({E})?{FS}?|{D}+"."{D}*({E})?{FS}?)" "*[+-]" "*)?(0[xX]{H}+{IS}?|0{D}+{IS}?|{D}+{IS}?|{D}+{E}{FS}?|{D}*"."{D}+({E})?{FS}?|{D}+"."{D}*({E})?{FS}?)"i" { if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }


\".*\"     { return Parser.STRING_LITERAL; }

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
"["         { return Parser.BRACKETLEFT; }
"]"         { return Parser.BRACKETRIGHT; }
"."			{ return Parser.DOT; }
"&"			{ return Parser.AMP; }
"!"			{ return Parser.EXCL; }
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

[ \t\v\n\f]	{ }
.           { }
