package edu.eltech.moevm.parsing_tree;


/**
 * Created by vladimir on 31.10.15.
 */
public class ParsingTree {


    private PTElement root;

    public ParsingTree(PTElement root) {
        this.root = root;
    }

    public void postfixVisit(PTCallback c) {
        postfixVisit(c, root, 0);
    }

    private void postfixVisit(PTCallback c, PTElement node, int level) {
        try {
            for (PTElement e : node.getElements()) {
                if (e != null) {
                    postfixVisit(c, e, level + 1);
                }
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }

    public void infixVisit(PTCallback c) {
        infixVisit(c, root, 0);
    }

    private void infixVisit(PTCallback c, PTElement node, int level) {
        try {
            c.processElement(node, level);
            for (PTElement e : node.getElements()) {
                if (e != null)
                    infixVisit(c, e, level + 1);
            }
        } catch (UnsupportedOperationException ignored) {
        }

    }

    public PTElement getRoot() {
        return root;
    }
}
