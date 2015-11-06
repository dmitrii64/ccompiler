package edu.eltech.moevm.autogen;

%%

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

%%

"//".* { }

"bool"    { return Parser.BOOL; }
"break"   { return Parser.BREAK; }
"char"    { return Parser.CHAR; }
"complex" { return Parser.COMPLEX; }
"do"      { return Parser.DO; }
"double"  { return Parser.DOUBLE; }
"else"    { return Parser.ELSE; }
"float"   { return Parser.FLOAT; }
"for"     { return Parser.FOR; }
"goto"    { return Parser.GOTO; }
"if"      { return Parser.IF; }
"im"      { return Parser.IM; }
"int"     { return Parser.INT; }
"long"    { return Parser.LONG; }
"mod"     { return Parser.MOD; }
"print"   { return Parser.PRINT; }
"re"      { return Parser.RE; }
"return"  { return Parser.RETURN; }
"short"   { return Parser.SHORT; }
"sizeof"  { return Parser.SIZEOF; }
"static"  { return Parser.STATIC; }
"void"    { return Parser.VOID; }
"while"   { return Parser.WHILE; }


"false"|"true"      { if (yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }
{L}({L}|{D})*       { if (yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.IDENTIFIER; }
[+-]?{D}+("."{D}+)? { if (yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }

/* Complex type */
(([+-]?{D}+("."{D}+?)?" "*[+-]" "*)|([+-])?)({D}+("."{D}+)?)"i" {
  if (yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT; }

{D}+({L})+ { throw new java.io.IOException("bad variable name"); }

\".*?\" { if (yyparser!=null) yyparser.yylval = new ParserVal(yytext().replaceAll("\"$|^\"","")); return Parser.STRING_LITERAL; }

">>" { return Parser.RIGHT_OP; }
"<<" { return Parser.LEFT_OP; }
"++" { return Parser.INC_OP; }
"--" { return Parser.DEC_OP; }
"&&" { return Parser.AND_OP; }
"||" { return Parser.OR_OP; }
"<=" { return Parser.LE_OP; }
">=" { return Parser.GE_OP; }
"==" { return Parser.EQ_OP; }
"!=" { return Parser.NE_OP; }
";"  { return Parser.SEMICOLON; }
"\{" { return Parser.BRACELEFT; }
"\}" { return Parser.BRACERIGHT; }
","  { return Parser.COMMA; }
":"  { return Parser.COLON; }
"="  { return Parser.EQUAL; }
"("  { return Parser.RBLEFT; }
")"  { return Parser.RBRIGHT; }
"["  { return Parser.BRACKETLEFT; }
"]"  { return Parser.BRACKETRIGHT; }
"&"  { return Parser.AMP; }
"!"  { return Parser.EXCL; }
"-"  { return Parser.MINUS; }
"+"  { return Parser.PLUS; }
"*"  { return Parser.STAR; }
"/"  { return Parser.SLASH; }
"%"  { return Parser.PERCENT; }
"<"  { return Parser.LESS; }
">"  { return Parser.GREATER; }
"^"  { return Parser.CARET; }
"|"  { return Parser.BAR; }
"?"  { return Parser.QUESTION; }
"#"  { return Parser.NUMBER_SIGN; }

[ \t\v\n\f] { }
