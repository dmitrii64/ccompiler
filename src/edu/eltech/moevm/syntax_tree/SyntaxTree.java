package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.common.Operand;
import edu.eltech.moevm.common.Operation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vladimir on 31.10.15.
 */
public class SyntaxTree {
    private TreeElement root;

    public SyntaxTree(TreeElement root) {
        this.root = root;
    }

    public TreeElement getRoot() {
        return root;
    }

    public void postfixVisit(TreeCallback c) {
        postfixVisit(c, root, 0);
    }

    private void postfixVisit(TreeCallback c, TreeElement node, int level) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    postfixVisit(c, e, level + 1);
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
                    infixVisit(c, e, level + 1);
            }
        } catch (UnsupportedOperationException ignored) {
        }
    }

    public void verifyNameScopes() throws IdentifierDefinedException, UnexpectedNodeException,
            UnexpectedChildCountException, IdentifierNotDefinedException, UnusedIdentifierException, DuplicateArgumentException {
        try {
            IdentifierStore globalNameScope = new IdentifierStore();

            for (TreeElement e : root.getElements()) {
                if (e != null && e instanceof Node) {
                    Node nodeElem = (Node) e;
                    switch (nodeElem.getOperation()) {
                        case FUNCTION_DEFINITION:

                            // Function definition

                            // create identifier of function if not exists
                            globalNameScope.createIdentifier(nodeElem.getValue(), nodeElem.getType());

                            // Create local name store for function parameters
                            IdentifierStore localNameScope = new IdentifierStore();

                            if (nodeElem.getElements().size() == 0) {
                                throw new UnexpectedChildCountException();
                            }

                            // If function has parameters
                            if (nodeElem.getElements().size() > 1) {
                                // child nodes of function_definition is 0-N parameter_declarations and
                                // 1 compound_statement

                                // create function parameters
                                for (int i = 0; i < nodeElem.getElements().size() - 1; i++) {
                                    if (!(nodeElem.getElements().get(i) instanceof Node)) {
                                        throw new UnexpectedNodeException();
                                    }
                                    Node paramNode = (Node) nodeElem.getElements().get(i);
                                    if (paramNode.getOperation() != Operation.PARAMETER_DECLARATION) {
                                        throw new UnexpectedNodeException();
                                    }
                                    if (paramNode.getElements().size() != 1) {
                                        throw new UnexpectedChildCountException();
                                    }
                                    if (!(paramNode.getElements().get(0) instanceof Leaf)) {
                                        throw new UnexpectedNodeException();
                                    }
                                    Leaf identifierElem = (Leaf) paramNode.getElements().get(0);
                                    if (identifierElem.getOperand() != Operand.IDENTIFIER) {
                                        throw new UnexpectedNodeException();
                                    }
                                    try {
                                        localNameScope.createIdentifier(identifierElem.getValue(), identifierElem.getType());
                                    } catch (IdentifierDefinedException e1) {
                                        throw new DuplicateArgumentException();
                                    }
                                }
                            }

                            // Last node must be compound statement
                            if (!(nodeElem.getElements().get(nodeElem.getElements().size() - 1) instanceof Node)) {
                                throw new UnexpectedNodeException();
                            }
                            // get Compound Statement
                            Node compoundStatementNode = (Node) nodeElem.getElements().get(nodeElem.getElements().size() - 1);
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
            IdentifierDefinedException, UnexpectedNodeException, UnexpectedChildCountException, IdentifierNotDefinedException,
            UnusedIdentifierException {

        // Work only with compound statement
        if (node.getOperation() != Operation.COMPOUND_STATEMENT) {
            throw new UnexpectedNodeException();
        }

        if (identifiers.length == 0) {
            identifiers = new IdentifierStore[]{new IdentifierStore()};
        }

        try {
            for (TreeElement nodeChild : node.getElements()) {
                // COMPOUND_STATEMENT cannot contain leaf child
                if (!(nodeChild instanceof Node)) {
                    throw new UnexpectedNodeException();
                }
                Node nodeElem = (Node) nodeChild;

                if (nodeElem.getOperation() == Operation.DECLARATION) {
                    // Parse nodes and create new variable into name scope
                    handleVariableDeclaration(nodeElem, identifiers);
                } else {
                    verifyNameScopes(nodeElem, identifiers);
                }
            }
        } catch (UnsupportedOperationException ignore) {
        }

        // Check unused identifiers defined in current compound statement
        String name = identifiers[identifiers.length - 1].getFirstUnusedIdentifier();
        if (name != null) {
            System.out.println("unused identifier: " + name);
            throw new UnusedIdentifierException();
        }
    }

    private void verifyNameScopes(TreeElement element, IdentifierStore... identifiers) throws IdentifierNotDefinedException,
            IdentifierDefinedException, UnexpectedChildCountException, UnexpectedNodeException, UnusedIdentifierException {

        if (element instanceof Node) {
            Node node = (Node) element;
            if (node.getOperation() == Operation.COMPOUND_STATEMENT) {

                // Create new empty name scope
                ArrayList<IdentifierStore> arrayList = new ArrayList<IdentifierStore>(Arrays.asList(identifiers));
                arrayList.add(new IdentifierStore());
                identifiers = new IdentifierStore[identifiers.length + 1];
                arrayList.toArray(identifiers);

                verifyNameScopesInCompoundStatement(node, identifiers);

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
                for (int i = identifiers.length - 1; i >= 0; i--) {
                    if (identifiers[i].identifierExists(leaf.getValue())) {
                        System.out.println("used identifier: " + leaf.getValue());
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
            UnexpectedNodeException, UnexpectedChildCountException, IdentifierDefinedException, IdentifierNotDefinedException,
            UnusedIdentifierException {
        if (nodeElem.getOperation() != Operation.DECLARATION) {
            throw new UnexpectedNodeException();
        }

        try {
            // One or more declarations or throw exception
            if (nodeElem.getElements().size() == 0) {
                // Invalid child number
                throw new UnexpectedChildCountException();
            }

            for (int i = 0; i < nodeElem.getElements().size(); i++) {
                TreeElement child = nodeElem.getElements().get(i);
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
                    Leaf identifier = (Leaf) nchild.getElements().get(0);
                    if (identifier.getOperand() != Operand.IDENTIFIER) {
                        throw new UnexpectedNodeException();
                    }
                    TreeElement value = nchild.getElements().get(1);

                    System.out.println("verify value of " + identifier.getValue());
                    verifyNameScopes(value, identifiers);

                    identifiers[identifiers.length - 1].createIdentifier(identifier.getValue(), identifier.getType(), value);

                } else if (child instanceof Leaf) {

                    // Variable declaration WITHOUT initializing

                    Leaf lchild = (Leaf) child;
                    if (lchild.getOperand() != Operand.IDENTIFIER) {
                        throw new UnexpectedNodeException();
                    }

                    identifiers[identifiers.length - 1].createIdentifier(lchild.getValue(), lchild.getType());
                }
            }
        } catch (UnsupportedOperationException ignore) {
        }
    }
}
