package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public class Leaf extends TreeElement {
    private String value;

    public Leaf(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
