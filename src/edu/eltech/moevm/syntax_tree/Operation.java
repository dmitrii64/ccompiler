package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public enum Operation {
    PLUS,   // +
    MINUS,  // -
    STAR,   // *
    DIVIDE, // /
    UMINUS, //-x
    PERCENT,    //%
    LESS,   //<
    GREATER,  //>
    NOT,    //!
    EQ_OP,  //==
    EQUAL, //=
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
    POSTFIX_EXPRESSION,

    EXPRESSION,
    DECLARATION,

    FUNCTION_DEFINITION,
    COMPOUND_STATEMENT,

    ITERATION_STATEMENT,
    INIT_DECLARATOR,
    PARAMETER_DECLARATION,

    EXPRESSION_STATEMENT,

    IF,
    GOTO,
    BREAK,
    RETURN,

    INC_OP,
    DEC_OP,
    POST_INC_OP,
    POST_DEC_OP,

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
