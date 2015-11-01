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


}
