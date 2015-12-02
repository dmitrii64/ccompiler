package edu.eltech.moevm.parsing_tree;

import edu.eltech.moevm.common.Type;

/**
 * Created by vladimir on 31.10.15.
 */
public class PTLeaf extends PTElement {
    // Token type
    private short tokenConst;
    // Type of value
    private Type type;
    private String value;
    private int line;

    public PTLeaf(short tokenConst, int line, Type type, String value) {
        this.tokenConst = tokenConst;
        this.line = line;
        this.type = type;
        this.value = value;
    }

    public PTLeaf(short tokenConst, int line) {
        this.tokenConst = tokenConst;
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public short getToken() {
        return tokenConst;
    }

    public int getLine() {
        return line;
    }

    public Type getType() {
        return type;
    }
}
