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
        postfixVisit(c, root, 0);
    }

    private void postfixVisit(TreeCallback c, TreeElement node, int level) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    postfixVisit(c, e, level+1);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            c.processElement(node, level);
        }
    }

    public void infixVisit(TreeCallback c) {
        infixVisit(c, root, 0);
    }

    private void infixVisit(TreeCallback c, TreeElement node, int level) {
        c.processElement(node, level);
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    infixVisit(c, e, level+1);
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }
}
