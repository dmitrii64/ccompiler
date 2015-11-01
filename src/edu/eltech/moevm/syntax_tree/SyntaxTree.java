package edu.eltech.moevm.syntax_tree;

import sun.awt.SunToolkit;

/**
 * Created by vladimir on 31.10.15.
 */
public class SyntaxTree {
    private TreeElement root;

    public SyntaxTree(TreeElement root) {
        this.root = root;
    }

    public void visit() {
        visit(root);
    }

    private void visit(TreeElement node) {
        try {
            for (TreeElement e : node.getElements()) {
                if(e!=null)
                visit(e);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            print(node);
        }
    }

    private void print(TreeElement e) {

        if(e instanceof Leaf)
            System.out.println("leaf ["+((Leaf) e).getOperand()+"] value = "+((Leaf) e).getValue());
        else
            System.out.println("node ["+((Node) e).getOperation()+"]");
    }
}
