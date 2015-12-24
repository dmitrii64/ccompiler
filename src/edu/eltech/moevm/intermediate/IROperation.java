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
    BZ,
    CMP,
    BL,
    BG,
    BNZ,
    BRL,
    BE,
    BNE,
    BGE,
    BLE,
    AND,
    OR,
    NOT,

    PRINT,
    SIZEOF

}
