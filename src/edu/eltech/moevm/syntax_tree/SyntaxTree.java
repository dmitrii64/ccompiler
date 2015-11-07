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

    public void verifyNameScopes() throws IdentifierDefinedException, UnexpectedNodeException, UnexpectedChildCountException {
        try {
            IdentifierStore globalNameScope = new IdentifierStore();

            for (TreeElement e : root.getElements()) {
                if (e != null && e instanceof Node) {
                    Node nodeElem = (Node) e;
                    switch (nodeElem.getOperation()) {
                        case FUNCTION_DEFINITION:

                            // Function definition

                            // create identifier if not exists
                            globalNameScope.createIdentifier(nodeElem.getValue(), nodeElem.getType());

                            // Create local name store for function parameters
                            IdentifierStore localNameScope = new IdentifierStore();

                            if (nodeElem.getElements().size() > 1) {
                                // last child nodes of function_definition is N parameter_declarations and
                                // 1 compound_statement

                                // create parameters
                                for (int i = 0; i < nodeElem.getElements().size()-1; i++) {
                                    if (!(nodeElem.getElements().get(i) instanceof Node)) {
                                        throw new UnexpectedNodeException();
                                    }
                                    Node paramNode = (Node)nodeElem.getElements().get(i);
                                    if (paramNode.getOperation() != Operation.PARAMETER_DECLARATION) {
                                        throw new UnexpectedNodeException();
                                    }
                                    if (paramNode.getElements().size() != 1) {
                                        throw new UnexpectedChildCountException();
                                    }
                                    if (!(paramNode.getElements().get(0) instanceof Leaf)) {
                                        throw new UnexpectedNodeException();
                                    }
                                    Leaf identifierElem = (Leaf)paramNode.getElements().get(0);
                                    if (identifierElem.getOperand() != Operand.IDENTIFIER) {
                                        throw new UnexpectedNodeException();
                                    }
                                    localNameScope.createIdentifier(identifierElem.getValue(), identifierElem.getType());
                                }
                            }

                            // Last node must be compound statement
                            if (!(nodeElem.getElements().get(nodeElem.getElements().size()-1) instanceof Node)) {
                                throw new UnexpectedNodeException();
                            }
                            // get Compound Statement
                            Node compoundStatementNode = (Node)nodeElem.getElements().get(nodeElem.getElements().size()-1);
                            if (compoundStatementNode.getOperation() != Operation.COMPOUND_STATEMENT) {
                                throw new UnexpectedNodeException();
                            }

                            // Verify function body
                            verifyNameScopesInCompoundStatement(compoundStatementNode, globalNameScope, localNameScope);
                            // вызываем verifyNameScopes для compound_statement

                            break;

                        case DECLARATION:

                            // Variable declaration

                            if (nodeElem.getElements().size() == 1) {
                                TreeElement child = nodeElem.getElements().get(0);
                                if (child instanceof Node) {

                                    // Variable declaration with initializing

                                    Node nchild = (Node) child;

                                    if (nchild.getOperation() != Operation.INIT_DECLARATOR) {
                                        throw new UnexpectedNodeException();
                                    }
                                    try {
                                        if (nchild.getElements().size() != 2) {
                                            throw new UnexpectedChildCountException();
                                        }
                                        if (!(nchild.getElements().get(0) instanceof Leaf)) {
                                            throw new UnexpectedNodeException();
                                        }
                                        Leaf identifier = (Leaf)nchild.getElements().get(0);
                                        if (identifier.getOperand() != Operand.IDENTIFIER) {
                                            throw new UnexpectedNodeException();
                                        }
                                        TreeElement value = nchild.getElements().get(1);

                                        globalNameScope.createIdentifier(identifier.getValue(), identifier.getType(), value);
                                    } catch (UnsupportedOperationException ignore) {
                                    }

                                } else if (child instanceof Leaf) {

                                    // Variable declaration WITHOUT initializing

                                    Leaf lchild = (Leaf) child;
                                    if (lchild.getOperand() != Operand.IDENTIFIER) {
                                        throw new UnexpectedNodeException();
                                    }

                                    globalNameScope.createIdentifier(lchild.getValue(), lchild.getType());
                                }
                            } else {
                                // Invalid child number
                                throw new UnexpectedChildCountException();
                            }
                            break;

                        default:
                            // Unexpected node!
                            throw new UnexpectedNodeException();
                    }
                }
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }

    private void verifyNameScopesInCompoundStatement(Node node, IdentifierStore... identifiers) throws
            IdentifierDefinedException, UnexpectedNodeException, UnexpectedChildCountException
    {
        // Work only with compound statement
        if (node.getOperation() != Operation.COMPOUND_STATEMENT) {
            throw new UnexpectedNodeException();
        }

        // 1. declaration
        // 1.1 DECLARATION -> INIT_DECLARATOR -> IDENTIFIER, value
        // 1.2 DECLARATION -> IDENTIFIER

        // 2. something else
        // verifyNameScopes(Node)
    }

    private void verifyNameScopes(TreeElement node, IdentifierStore... identifiers) {
        // if COMPOUND_STATEMENT
        // verifyNameScopesInCompoundStatement

        // if IDENTIFIER
        // check if identifier exists
        // setup unused flag

        // something else
        // verifyNameScopes
    }
}
