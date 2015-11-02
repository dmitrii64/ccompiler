package edu.eltech.moevm.parsing_tree;


/**
 * Created by vladimir on 31.10.15.
 */
public class PTLeaf extends PTElement {
    private short token;
    private String value;

    public PTLeaf(short tok, String value) {
        this.token = tok;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public short getToken() {
        return token;
    }
}
