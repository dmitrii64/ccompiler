package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.autogen.ParserVal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public class Node extends TreeElement {
    private Operation operation;
    private ArrayList<TreeElement> elements;

    public Node(Operation operation, ParserVal... elements) {
        this.operation = operation;
        this.elements = new ArrayList<TreeElement>();
        for (ParserVal obj : elements) {
            this.elements.add((TreeElement) obj.obj);
        }

    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public void add(TreeElement element) throws UnsupportedOperationException {
        element.add(element);
    }

    @Override
    public List<TreeElement> getElements() throws UnsupportedOperationException {
        return elements;
    }

    @Override
    public String getValue() {
        String value = null;
        switch (operation) {
            case PLUS:
                value = "+";
                break;
            case MINUS:
                value = "-";
                break;
            case MULTIPLY:
                value = "*";
                break;
            case DIVIDE:
                value = "/";
                break;
            case UMINUS:
                value = "-";
                break;
            case PERCENT:
                value = "%";
                break;
            case LESS:
                value = "<";
                break;
            case GRAND:
                value = ">";
                break;
            case NOT:
                value = "!";
                break;
            case EQUAL:
                value = "==";
                break;
            case ASSIGNMENT:
                value = "=";
                break;
            case NOT_EQ:
                value = "!=";
                break;
            case GRAND_OR_EQ:
                value = ">=";
                break;
            case LESS_OR_EQ:
                value = "<=";
                break;
        }
        return value;
    }

}
