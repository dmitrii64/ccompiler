package edu.eltech.moevm.syntax_tree;

import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void verifyNameScopes() throws IdentifierDefinedException, UnexpectedNodeException, UnexpectedChildCountException, IdentifierNotDefinedException {
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

                            break;

                        case DECLARATION:
                            // Variable declaration

                            // Parse nodes and create new variable into last name scope
                            handleVariableDeclaration(nodeElem, globalNameScope);

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
            IdentifierDefinedException, UnexpectedNodeException, UnexpectedChildCountException, IdentifierNotDefinedException {
        // Work only with compound statement
        if (node.getOperation() != Operation.COMPOUND_STATEMENT) {
            throw new UnexpectedNodeException();
        }

        if (identifiers.length == 0) {
            identifiers = new IdentifierStore[] { new IdentifierStore() };
        }

        try {
            for (TreeElement nodeChild : node.getElements()) {
                if (!(nodeChild instanceof Node)) {
                    throw new UnexpectedNodeException();
                }
                // COMPOUND_STATEMENT cannot contain leaf child
                Node nodeElem = (Node)nodeChild;

                if (nodeElem.getOperation() == Operation.DECLARATION) {
                    // Parse nodes and create new variable into last name scope
                    handleVariableDeclaration(nodeElem, identifiers);
                } else {
                    verifyNameScopes(nodeElem, identifiers);
                }
            }
        } catch (UnsupportedOperationException ignore) {
        }

//        identifiers[identifiers.length-1].getFirstUnusedIdentifier();
    }

    private void verifyNameScopes(TreeElement element, IdentifierStore... identifiers) throws IdentifierNotDefinedException {
        if (element instanceof Node) {
            Node node = (Node) element;
            if (node.getOperation() == Operation.COMPOUND_STATEMENT) {

                ArrayList<IdentifierStore> arrayList = new ArrayList<IdentifierStore>(Arrays.asList(identifiers));
                arrayList.add(new IdentifierStore());
                try {
                    verifyNameScopesInCompoundStatement(node, (IdentifierStore[]) arrayList.toArray());
                } catch (Exception ignore) {
                }

            } else {
                try {
                    for (TreeElement child : node.getElements()) {
                        verifyNameScopes(child, identifiers);
                    }
                } catch (UnsupportedOperationException ignore) {
                }
            }

        } else {
            Leaf leaf = (Leaf) element;
            if (leaf.getOperand() == Operand.IDENTIFIER) {

                boolean identifierExists = false;

                // Check all name scopes for that identifier
                for (int i = identifiers.length-1; i >= 0 ; i--) {
                    System.out.println("identifier used: "+leaf.getValue());
                    if (identifiers[i].identifierExists(leaf.getValue())) {
                        identifiers[i].markAsUsed(leaf.getValue());
                        identifierExists = true;
                        break;
                    }
                }

                if (!identifierExists) {
                    throw new IdentifierNotDefinedException();
                }
            }
        }
    }

    private void handleVariableDeclaration(Node nodeElem, IdentifierStore... identifiers) throws
            UnexpectedNodeException, UnexpectedChildCountException, IdentifierDefinedException, IdentifierNotDefinedException {
        if (nodeElem.getOperation() != Operation.DECLARATION) {
            throw new UnexpectedNodeException();
        }

        try {
            if (nodeElem.getElements().size() == 1) {
                TreeElement child = nodeElem.getElements().get(0);
                if (child instanceof Node) {

                    // Variable declaration with initializing

                    Node nchild = (Node) child;

                    if (nchild.getOperation() != Operation.INIT_DECLARATOR) {
                        throw new UnexpectedNodeException();
                    }
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

                    System.out.println("verify value of " + identifier.getValue());
                    verifyNameScopes(value, identifiers);

                    identifiers[identifiers.length-1].createIdentifier(identifier.getValue(), identifier.getType(), value);

                } else if (child instanceof Leaf) {

                    // Variable declaration WITHOUT initializing

                    Leaf lchild = (Leaf) child;
                    if (lchild.getOperand() != Operand.IDENTIFIER) {
                        throw new UnexpectedNodeException();
                    }

                    identifiers[identifiers.length-1].createIdentifier(lchild.getValue(), lchild.getType());
                }
            } else {
                // Invalid child number
                throw new UnexpectedChildCountException();
            }
        } catch (UnsupportedOperationException ignore) {
        }
    }
}
