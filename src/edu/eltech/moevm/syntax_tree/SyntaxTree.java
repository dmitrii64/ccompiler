package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 31.10.15.
 */
public class SyntaxTree {
    private TreeElement root;

    public SyntaxTree(TreeElement root) {
        this.root = root;
    }

    public void visit(TreeCallback c) {
        visit(c, root);
    }

    private void visit(TreeCallback c, TreeElement node) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    visit(c, e);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            c.processElement(node);
        }
    }
}
