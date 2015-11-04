package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.autogen.ParserVal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public class Node extends TreeElement {
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    private Operation operation;
    private String value;
    private ArrayList<TreeElement> elements;

    public Node(Operation operation, ParserVal... elements) {
        this.operation = operation;
        this.elements = new ArrayList<TreeElement>();
        for (ParserVal obj : elements) {
            this.elements.add((TreeElement) obj.obj);
            ((TreeElement) obj.obj).setParent(this);
        }
        id = counter;
        counter++;
    }

    public Operation getOperation() {
        return operation;
    }

    public boolean haveAny(Operation op)
    {
        for(TreeElement e : elements)
        if(e instanceof Node)
        {
            if(((Node)e).getOperation()==op)
                return true;
        }
        return false;
    }

    @Override
    public void add(TreeElement element) throws UnsupportedOperationException {
        elements.add(element);
        element.setParent(this);
    }

    @Override
    public void remove(TreeElement element) throws UnsupportedOperationException {
        elements.remove(element);
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        elements.clear();
    }

    @Override
    public List<TreeElement> getElements() throws UnsupportedOperationException {
        return elements;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
