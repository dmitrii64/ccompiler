package edu.eltech.moevm.syntax_tree;

import java.util.Vector;

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

    private class Identifier {
        public String name;
        public Type type;
        public TreeElement value;

        public Identifier(String n, Type t, TreeElement v) {
            name = n;
            type = t;
            value = v;
        }

        public Identifier(String n, Type t) {
            name = n;
            type = t;
        }

        public boolean hasValue() {
            return value != null;
        }
    }

    public class NameScopeException extends Exception {}

    public void verifyNameScopes() throws NameScopeException {
        try {
            Vector<Identifier> globalNameScope = new Vector<Identifier>();

            for (TreeElement e : root.getElements()) {
                if (e != null && e instanceof Node) {
                    Node nodeElem = (Node) e;
                    switch (nodeElem.getOperation()) {
                        case FUNCTION_DEFINITION:
                            // is identifier already defined??
                            globalNameScope.add(new Identifier(nodeElem.getValue(), nodeElem.getType()));
                            if (nodeElem.getElements().size() > 1) {

                            } else if (nodeElem.getElements().size() == 1) {
                                verifyNameScopes(nodeElem, globalNameScope, new Vector<Identifier>());
                            }
                            break;
                        case DECLARATION:
                            if (nodeElem.getElements().size() == 1) {
                                TreeElement child = nodeElem.getElements().get(0);
                                if (child instanceof Node) {
                                    Node nchild = (Node) child;
                                    if (nchild.getOperation() == Operation.INIT_DECLARATOR && nchild.getElements().size() == 2) {
                                        Leaf identifier = (Leaf)nchild.getElements().get(0);
                                        TreeElement value = nchild.getElements().get(1);
                                        if (identifier.getOperand() == Operand.IDENTIFIER) {
                                            // is identifier already defined??
                                            globalNameScope.add(new Identifier(identifier.getValue(), identifier.getType(), value));
                                        }
                                    }
                                } else if (child instanceof Leaf) {
                                    Leaf lchild = (Leaf) child;
                                    if (lchild.getOperand() == Operand.IDENTIFIER) {
                                        // is identifier already defined??
                                        globalNameScope.add(new Identifier(lchild.getValue(), lchild.getType()));
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }

    private void verifyNameScopes(TreeElement node, Vector<Identifier> parentNames, Vector<Identifier> localNames) throws NameScopeException {

    }
}
