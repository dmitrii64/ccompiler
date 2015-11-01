package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public class SyntaxTree {
    private TreeElement root;

    public SyntaxTree(TreeElement root) {
        this.root = root;
    }

    public void postfixVisit(TreeCallback c) {
        postfixVisit(c, root);
    }

    private void postfixVisit(TreeCallback c, TreeElement node) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    postfixVisit(c, e);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            c.processElement(node);
        }
    }

    public void infixVisit(TreeCallback c) {
        infixVisit(c, root);
    }

    private void infixVisit(TreeCallback c, TreeElement node) {
        c.processElement(node);
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    infixVisit(c, e);
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }
}
