package edu.eltech.moevm.syntax_tree;

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
                visit(e);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            print(node);
        }
    }

    private void print(TreeElement e) {
        System.out.print(e.getValue());
    }
}
