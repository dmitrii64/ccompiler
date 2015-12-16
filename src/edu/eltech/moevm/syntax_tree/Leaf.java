package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.common.Operand;

/**
 * Created by vladimir on 31.10.15.
 */
public class Leaf extends TreeElement {
    private Operand operand;
    private String value;
    private int line;

    public Leaf(Operand operand, String value, int ln) {
        this.operand = operand;
        this.value = value;
        line = ln;
        id = counter;
        counter++;
    }

    public int getLine() {
        return line;
    }

    public String getValue() {
        return value;
    }

    public Operand getOperand() {
        return operand;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
