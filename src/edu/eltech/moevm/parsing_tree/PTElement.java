package edu.eltech.moevm.parsing_tree;

import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public abstract class PTElement {
    protected PTElement parent;

    public void add(PTElement element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void remove(PTElement element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public List<PTElement> getElements() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public final PTElement getParent() {
        return parent;
    }

    public void setParent(PTElement parent) {
        this.parent = parent;
    }
}
