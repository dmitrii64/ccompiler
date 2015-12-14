package edu.eltech.moevm.parsing_tree;

import edu.eltech.moevm.common.Nonterminals;
import edu.eltech.moevm.intermediate.ReduceObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public class PTNode extends PTElement {
    private Nonterminals nonterminal;
    private String value;
    private ArrayList<PTElement> elements;

    public PTNode(Nonterminals nt, PTElement... elements) {
        this.nonterminal = nt;
        this.elements = new ArrayList<PTElement>();
        for (PTElement obj : elements) {
            this.elements.add((PTElement) obj);
            ((PTElement) obj).setParent(this);
        }
//        ReduceObserver.getInstance().generate(this);
    }

    @Override
    public void add(PTElement element) throws UnsupportedOperationException {
        elements.add(element);
        element.setParent(this);
    }

    public void insertElementBefore(PTElement element, PTElement newElement) {
        elements.add(elements.indexOf(element), newElement);
    }

    @Override
    public void remove(PTElement element) throws UnsupportedOperationException {
        elements.remove(element);
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        elements.clear();
    }

    @Override
    public List<PTElement> getElements() throws UnsupportedOperationException {
        return elements;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Nonterminals getNonterminal() {
        return nonterminal;
    }
}
