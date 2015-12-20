package edu.eltech.moevm.autogen;

import edu.eltech.moevm.parsing_tree.PTLeaf;
import edu.eltech.moevm.common.Type;

%%

%byaccj

%{
    private int yylineno = 1;
    private Parser yyparser;
    public Yylex(java.io.Reader r, Parser yyparser) {
        this(r);
        this.yyparser = yyparser;
    }
%}

D = [0-9]
L = [a-zA-Z_]
H = [a-fA-F0-9]

%%

"//".* { }

"bool"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BOOL, yylineno));
    }
    return Parser.BOOL;
}
"break"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BREAK, yylineno));
    }
    return Parser.BREAK;
}
"char"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CHAR, yylineno));
    }
    return Parser.CHAR;
}
"complex" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.COMPLEX, yylineno));
    }
    return Parser.COMPLEX;
}
"do"      {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.DO, yylineno));
    }
    return Parser.DO;
}
"double"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.DOUBLE, yylineno));
    }
    return Parser.DOUBLE;
}
"else"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.ELSE, yylineno));
    }
    return Parser.ELSE;
}
"float"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.FLOAT, yylineno));
    }
    return Parser.FLOAT;
}
"for"     {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.FOR, yylineno));
    }
    return Parser.FOR;
}
"goto"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.GOTO, yylineno));
    }
    return Parser.GOTO;
}
"if"      {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.IF, yylineno));
    }
    return Parser.IF;
}
"im"      {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.IM, yylineno));
    }
    return Parser.IM;
}
"int"     {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.INT, yylineno));
    }
    return Parser.INT;
}
"long"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.LONG, yylineno));
    }
    return Parser.LONG;
}
"mod"     {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.MOD, yylineno));
    }
    return Parser.MOD;
}
"print"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.PRINT, yylineno));
    }
    return Parser.PRINT;
}
"re"      {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.RE, yylineno));
    }
    return Parser.RE;
}
"return"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.RETURN, yylineno));
    }
    return Parser.RETURN;
}
"short"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.SHORT, yylineno));
    }
    return Parser.SHORT;
}
"sizeof"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.SIZEOF, yylineno));
    }
    return Parser.SIZEOF;
}
"static"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.STATIC, yylineno));
    }
    return Parser.STATIC;
}
"void"    {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.VOID, yylineno));
    }
    return Parser.VOID;
}
"while"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.WHILE, yylineno));
    }
    return Parser.WHILE;
}
"new"   {
     if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.NEW, yylineno));
     }
     return Parser.NEW;
}

/* boolean type */

"false"|"true" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CONSTANT, yylineno, Type.BOOL, yytext()));
    }
    return Parser.CONSTANT;
}

/* identifiers */

{L}({L}|{D})* {
    if(yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.IDENTIFIER, yylineno, Type.UNKNOWN, yytext()));
    }
    return Parser.IDENTIFIER;
}

/* integer numbers */

[+-]?{D}+ {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CONSTANT, yylineno, Type.INT, yytext()));
    }
    return Parser.CONSTANT;
}

/* float numbers */

[+-]?{D}+"."{D}+ {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CONSTANT, yylineno, Type.FLOAT, yytext()));
    }
    return Parser.CONSTANT;
}

/* hex numbers */

0[xX]{H}+ {
    if (yyparser != null) {
        //TODO: hex conversion
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CONSTANT, yylineno, Type.INT, yytext()));
    }
    return Parser.CONSTANT;
}


/* Complex type: */

/*  { ( (sign)? (  dec number      or  hex number)               )  or  (sign)?  }   (dec number)    "i"  */
    ( (  [+-]?  (({D}+("."{D}+)?)  |  (0[xX]{H}+))  " "*[+-]" "* )  |   ([+-]?)  ) ({D}+("."{D}+)?)  "i"   {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CONSTANT, yylineno, Type.COMPLEX, yytext()));
    }
    return Parser.CONSTANT;
}


\".*\" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.STRING_LITERAL, yylineno, Type.CHAR, yytext()));
    }
    return Parser.STRING_LITERAL;
}

">>" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.RIGHT_OP, yylineno));
    }
    return Parser.RIGHT_OP;
}
"<<" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.LEFT_OP, yylineno));
    }
    return Parser.LEFT_OP;
}
"++" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.INC_OP, yylineno));
    }
    return Parser.INC_OP;
}
"--" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.DEC_OP, yylineno));
    }
    return Parser.DEC_OP;
}
"&&" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.AND_OP, yylineno));
    }
    return Parser.AND_OP;
}
"||" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.OR_OP, yylineno));
    }
    return Parser.OR_OP;
}
"<=" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.LE_OP, yylineno));
    }
    return Parser.LE_OP;
}
">=" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.GE_OP, yylineno));
    }
    return Parser.GE_OP;
}
"==" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.EQ_OP, yylineno));
    }
    return Parser.EQ_OP;
}
"!=" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.NE_OP, yylineno));
    }
    return Parser.NE_OP;
}
";"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.SEMICOLON, yylineno));
    }
    return Parser.SEMICOLON;
}
"\{" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BRACELEFT, yylineno));
    }
    return Parser.BRACELEFT;
}
"\}" {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BRACERIGHT, yylineno));
    }
    return Parser.BRACERIGHT;
}
","  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.COMMA, yylineno));
    }
    return Parser.COMMA;
}
":"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.COLON, yylineno));
    }
    return Parser.COLON;
}
"="  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.EQUAL, yylineno));
    }
    return Parser.EQUAL;
}
"("  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.RBLEFT, yylineno));
    }
    return Parser.RBLEFT;
}
")"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.RBRIGHT, yylineno));
    }
    return Parser.RBRIGHT;
}
"["  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BRACKETLEFT, yylineno));
    }
    return Parser.BRACKETLEFT;
}
"]"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BRACKETRIGHT, yylineno));
    }
    return Parser.BRACKETRIGHT;
}
"&"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.AMP, yylineno));
    }
    return Parser.AMP;
}
"!"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.EXCL, yylineno));
    }
    return Parser.EXCL;
}
"-"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.MINUS, yylineno));
    }
    return Parser.MINUS;
}
"+"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.PLUS, yylineno));
    }
    return Parser.PLUS;
}
"*"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.STAR, yylineno));
    }
    return Parser.STAR;
}
"/"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.DIVIDE, yylineno));
    }
    return Parser.DIVIDE;
}
"%"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.PERCENT, yylineno));
    }
    return Parser.PERCENT;
}
"<"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.LESS, yylineno));
    }
    return Parser.LESS;
}
">"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.GREATER, yylineno));
    }
    return Parser.GREATER;
}
"^"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.CARET, yylineno));
    }
    return Parser.CARET;
}
"|"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.BAR, yylineno));
    }
    return Parser.BAR;
}
"?"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.QUESTION, yylineno));
    }
    return Parser.QUESTION;
}
"#"  {
    if (yyparser != null) {
        yyparser.yylval = new ParserVal(new PTLeaf(Parser.NUMBER_SIGN, yylineno));
    }
    return Parser.NUMBER_SIGN;
}

[\n]      { yylineno++; }
[ \t\v\f] { }
.         { }
