package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.autogen.ParserVal;
import edu.eltech.moevm.common.Operation;
import edu.eltech.moevm.intermediate.CodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public class Node extends TreeElement {
    private CodeList code;
    private Operation operation;
    private String value;
    private ArrayList<TreeElement> right_elements;
    private ArrayList<TreeElement> left_elements;

    public Node(Operation operation, ParserVal... elements) {
        this.operation = operation;
        this.right_elements = new ArrayList<TreeElement>();
        this.left_elements = new ArrayList<TreeElement>();

        for (ParserVal obj : elements) {
            this.left_elements.add((TreeElement) obj.obj);
            ((TreeElement) obj.obj).setParent(this);
        }
        id = counter;
        counter++;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void add(TreeElement element) throws UnsupportedOperationException {
        left_elements.add(element);
        element.setParent(this);
    }

    public void addleft(TreeElement element) throws UnsupportedOperationException {
        right_elements.add(element);
        element.setParent(this);
    }

    @Override
    public void remove(TreeElement element) throws UnsupportedOperationException {
        left_elements.remove(element);
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        left_elements.clear();
        right_elements.clear();
    }

    @Override
    public List<TreeElement> getElements() {
        ArrayList<TreeElement> temp = new ArrayList<TreeElement>();
        int i = right_elements.size();

        temp.addAll(left_elements);
        while (i > 0) {
            temp.add(right_elements.get(i - 1));
            i--;
        }

        return temp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCode(CodeList code) {
        this.code = code;
    }

    @Override
    public CodeList getCode() {
        return code;
    }
}
