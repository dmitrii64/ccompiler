package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.common.Type;
import edu.eltech.moevm.intermediate.CodeList;

import java.util.List;

/**
 * Created by vladimir on 31.10.15.
 */
public abstract class TreeElement {
    protected static int counter = 0;
    protected int id;
    protected TreeElement parent;
    protected Type type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void add(TreeElement element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void remove(TreeElement element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public List<TreeElement> getElements() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public final TreeElement getParent() {
        return parent;
    }

    public void setParent(TreeElement parent) {
        this.parent = parent;
    }

    public CodeList getCode() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
