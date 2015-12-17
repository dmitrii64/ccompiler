package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public enum IROperation {
    VOID,
    INTEGER,
    CHAR,
    FLOAT,
    BOOL,
    COMPLEX,
    SHORT,
    DOUBLE,
    LONG,

    MOV,
    ADD,
    SUB,
    DIV,
    MUL,
    CALL,
    RET,
    INC,
    DEC,

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
