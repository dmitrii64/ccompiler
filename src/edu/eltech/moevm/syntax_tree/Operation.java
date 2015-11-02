package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public enum Operation {
    PLUS,   // +
    MINUS,  // -
    MULTIPLY,   // *
    DIVIDE, // /
    UMINUS, //-x
    PERCENT,    //%
    LESS,   //<
    GREATER,  //>
    NOT,    //!
    EQUAL,  //==
    ASSIGNMENT, //=
    NOT_EQ, //!=
    GREATER_OR_EQ,    //>=
    LESS_OR_EQ,  //<=
    LEFT_OP,
    RIGHT_OP,
    AND_OP,
    XOR_OP,
    OR_OP,
    LOGICAL_AND_OP,
    LOGICAL_OR_OP,
    QUESTION_OP,

    EXPRESSION,
    VARIABLE_DECLARATION,

    DIRECT_DEC_FUNC,
    FUNCTION_DEFINITION,

    WHILE,
    DO,
    FOR,
    IF,
    GOTO,
    BREAK,
    RETURN,

    INC_OP,
    DEC_OP,

    FUNC_CALL,

    ARRAY_ACCESS,

    TYPE_CAST,

    SIZEOF,
    SIZEOFTYPE,

    RE,
    IM,
    MOD,

    NEW,

    ROOT
}
