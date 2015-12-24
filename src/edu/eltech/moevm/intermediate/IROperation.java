package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public enum IROperation {
    NEW,
    SUBS, //array access: a[i]
    VOID,
    INTEGER,
    CHAR,
    FLOAT,
    BOOL,
    COMPLEX,
    SHORT,
    DOUBLE,
    LONG,

    RE,
    IM,
    SRE,
    SIM,
    MOV,
    ADD,
    SUB,
    DIV,
    MUL,
    CALL,
    RET,
    INC,
    DEC,
    UMINUS,

    IDENTIFIER,
    CONSTANT,
    STRING_LITERAL,

    DEFL,
    BF,
    BZL,
    CMP,
    BML,
    BPL,
    BNZ,
    BRL,
    BNF,
    AND,
    OR,
    NOT,

    PRINT,
    SIZEOF

}
