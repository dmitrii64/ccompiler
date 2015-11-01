package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public class Leaf extends TreeElement {
    private Operand operand;
    private String value;

    public Leaf(Operand operand, String value) {
        this.operand = operand;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Operand getOperand() {
        return operand;
    }
}
