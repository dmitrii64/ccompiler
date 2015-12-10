package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public enum IROperation {
    VOID,
    INT,
    CHAR,
    FLOAT,
    BOOL,
    COMPLEX,
    SHORT,
    DOUBLE,
    LONG,

    MOV,
    ADD,
    DIV,
    MUL,
    CALL,
    RET,
    INC,
    DEC,

    IDENTIFIER,
    CONSTANT,
    STRING_LITERAL

}
