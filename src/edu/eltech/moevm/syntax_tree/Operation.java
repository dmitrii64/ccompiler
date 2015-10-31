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
    GRAND,  //>
    NOT,    //!
    EQUAL,  //==
    ASSIGNMENT, //=
    NOT_EQ, //!=
    GRAND_OR_EQ,    //>=
    LESS_OR_EQ,  //<=

    WHILE,
    DO,
    FOR,
    IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    GOTO,
    BREAK,
    RETURN,

    NEW
}
