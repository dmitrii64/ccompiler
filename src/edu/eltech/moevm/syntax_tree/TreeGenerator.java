package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.parsing_tree.*;

import java.util.ArrayList;

/**
 * Created by lazorg on 11/4/15.
 */
public class TreeGenerator {


    public SyntaxTree generate(ParsingTree parsingTree) {
        PTNode root = (PTNode) parsingTree.getRoot();
        SyntaxTree syntaxTree = null;
        try {
            syntaxTree = new SyntaxTree(visitNode(root));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return syntaxTree;
    }

    private boolean operationExist(String operation) {
        for (Operation op : Operation.values())
            if (op.name().compareTo(operation) == 0)
                return true;
        return false;
    }

    private PTNode getPTNodeByNonterminal(ArrayList<PTElement> list, Nonterminals nonterminal, int num) {
        for (PTElement el : list)
            if (el instanceof PTNode)
                if (((PTNode) el).getNonterminal() == nonterminal) {
                    if (num == 0)
                        return (PTNode) el;
                    else
                        num--;
                }
        return null;
    }

    private void setBinaryExpr(Node output, PTNode input) throws UnsupportedOperationException {
        PTElement first = input.getElements().get(0);
        if (first instanceof PTLeaf) {
            Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())), ((PTLeaf) first).getValue());
            output.add(leaf);
        }
        PTElement second = input.getElements().get(2);
        if (second instanceof PTLeaf) {
            Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
            output.add(leaf);
        }
        PTElement op = input.getElements().get(1);
        output.setOperation(Operation.valueOf(Parser.getTokenName(((PTLeaf) op).getToken())));
    }

    private Node visitNode(PTNode node) throws UnsupportedOperationException {
        Node result;
        String name = node.getNonterminal().name();
        if (operationExist(name)) {
            result = new Node(Operation.valueOf(name));
            if (name.compareTo(Operation.FUNCTION_DEFINITION.name()) == 0) {
                //Setting function return type
                PTLeaf type = (PTLeaf) node.getElements().get(0);
                result.setType(Type.valueOf(Parser.getTokenName(type.getToken())));
                //Setting function name
                PTLeaf fname = (PTLeaf) node.getElements().get(1);
                result.setValue(fname.getValue());
            } else if (name.compareTo(Operation.ITERATION_STATEMENT.name()) == 0) {
                //Setting cycle type
                PTLeaf itname = (PTLeaf) node.getElements().get(0);
                result.setValue(Parser.getTokenName(itname.getToken()));
            } else if (name.compareTo(Operation.PARAMETER_DECLARATION.name()) == 0) {
                //Creating leaf for parameter identifier with type
                PTLeaf type = (PTLeaf) node.getElements().get(0);
                PTLeaf fname = (PTLeaf) node.getElements().get(1);
                Leaf leaf = new Leaf(Operand.IDENTIFIER, fname.getValue());
                leaf.setType(Type.valueOf(Parser.getTokenName(type.getToken())));
                result.add(leaf);
            } else if (name.compareTo(Operation.DECLARATION.name()) == 0) {
                PTElement first = node.getElements().get(0);
                PTElement second = node.getElements().get(1);
                if (second instanceof PTLeaf) {
                    Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
                    leaf.setType(Type.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())));
                    result.add(leaf);
                }
            } else if (name.compareTo(Operation.INIT_DECLARATOR.name()) == 0) {
                PTElement first = node.getElements().get(0);
                PTElement second = node.getElements().get(2);
                Leaf leaf = new Leaf(Operand.IDENTIFIER, ((PTLeaf) first).getValue());
                result.add(leaf);
                if (second instanceof PTLeaf) {
                    Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
                    result.add(leaf2);
                }
            } else if (name.compareTo(Operation.POSTFIX_EXPRESSION.name()) == 0) {
                PTElement first = node.getElements().get(0);
                PTElement second = node.getElements().get(1);
                String op = Parser.getTokenName(((PTLeaf) second).getToken());
                if (op.compareTo("INC_OP") == 0)
                    result.setOperation(Operation.POST_INC_OP);
                else if (op.compareTo("DEC_OP") == 0)
                    result.setOperation(Operation.POST_DEC_OP);
                Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())), ((PTLeaf) first).getValue());
                result.add(leaf);
            }
        } else {
            result = null;
            if (name.compareTo("RELATIONAL_EXPRESSION") == 0) {
                PTElement first = node.getElements().get(0);
                PTElement oper = node.getElements().get(1);
                PTElement second = node.getElements().get(2);
                Operation op = Operation.EQUAL;
                switch (((PTLeaf) oper).getToken()) {
                    case Parser.EQ_OP:
                        op = Operation.EQ_OP;
                        break;
                    case Parser.LE_OP:
                        op = Operation.LESS_OR_EQ;
                        break;
                    case Parser.GE_OP:
                        op = Operation.GREATER_OR_EQ;
                        break;
                    case Parser.NE_OP:
                        op = Operation.NOT_EQ;
                        break;
                    case Parser.LESS:
                        op = Operation.LESS;
                        break;
                    case Parser.GREATER:
                        op = Operation.GREATER;
                        break;
                }
                result = new Node(op);
                if (first instanceof PTLeaf) {
                    Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())), ((PTLeaf) first).getValue());
                    result.add(leaf);
                }

                if (second instanceof PTLeaf) {
                    Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
                    result.add(leaf);
                }
            } else if (name.compareTo("ASSIGNMENT_EXPRESSION") == 0) {
                result = new Node(Operation.EQUAL);
                setBinaryExpr(result, node);
            } else if (name.compareTo("ADDITIVE_EXPRESSION") == 0) {
                result = new Node(Operation.PLUS);
                setBinaryExpr(result, node);
            } else if (name.compareTo("MULTIPLICATIVE_EXPRESSION") == 0) {
                result = new Node(Operation.STAR);
                setBinaryExpr(result, node);
            } else if (name.compareTo("UNARY_EXPRESSION") == 0) {
                PTElement first = node.getElements().get(0);
                PTElement second = node.getElements().get(1);

                result = new Node(Operation.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())));
                Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
                result.add(leaf);
            } else if (name.compareTo("JUMP_STATEMENT") == 0) {
                PTElement first = node.getElements().get(0);
                PTElement second = node.getElements().get(1);
                result = new Node(Operation.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())));

                if (result.getOperation() == Operation.RETURN) {
                    Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue());
                    result.add(leaf);
                }
            }

        }

        for (PTElement child : node.getElements()) {
            if (child instanceof PTNode) {
                Node ret = visitNode((PTNode) child);
                if (ret != null && result != null)
                    result.add(ret);
            }
        }


        return result;
    }
}
