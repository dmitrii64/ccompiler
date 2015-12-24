package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.common.Operation;
import edu.eltech.moevm.common.Type;
import edu.eltech.moevm.syntax_tree.*;
import edu.eltech.moevm.syntax_tree.UnsupportedOperationException;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by lazorg on 12/10/15.
 */
public class IRCodeGenerator {
    private IRCodeList code = new IRCodeList();
    private Stack<String> reg = new Stack<>();
    private Stack<String> labels = new Stack<>();
    private ArrayList<String> complexVars = new ArrayList<>();

    public IRCodeList generate(SyntaxTree tree) {

        generate(tree.getRoot());

        try {
            code = tree.getRoot().getCode();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        return code;
    }

    private void generate(TreeElement node) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null) {
                    if (e instanceof Node)
                        generate(e);
                }
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            if (node instanceof Node)
                createCommand((Node) node);
        }
    }

    private void createCommand(Node node) {
        IRCodeList code = new IRCodeList();
        IRInstruction i = null;
        Leaf left = null;
        IROperand rightAddr;
        IROperand leftAddr;
        switch (node.getOperation()) {
            case INIT_DECLARATOR:
                left = (Leaf) node.getElements().get(0);
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf value = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(value.getValue());
                    if (left.getType() != Type.COMPLEX) {
                        i = new IRInstruction((left.getType() == Type.INT) ? IROperation.INTEGER : IROperation.valueOf(left.getType().name()),
                                new IROperand(left.getValue()),
                                rightAddr,
                                left.getType());
                        code.add(i);
                    } else {
                        i = new IRInstruction((left.getType() == Type.INT) ? IROperation.INTEGER : IROperation.valueOf(left.getType().name()),
                                new IROperand(left.getValue()),
                                left.getType());
                        code.add(i);
                        complexVars.add(left.getValue());
                        Complex c = Complex.parse(value.getValue());
                        if (c.getRe() != null) {
                            i = new IRInstruction(IROperation.SRE,
                                    new IROperand(c.getRe()),
                                    new IROperand(left.getValue())
                                    , left.getType());
                            code.add(i);
                        }
                        if (c.getIm() != null) {
                            i = new IRInstruction(IROperation.SIM,
                                    new IROperand(c.getIm()),
                                    new IROperand(left.getValue())
                                    , left.getType());
                            code.add(i);
                        }
                    }
                } else {
                    Node rightNode = (Node) node.getElements().get(1);
                    code.addAll(rightNode.getCode());
                    rightAddr = new IROperand(reg.pop());
                    i = new IRInstruction((left.getType() == Type.INT) ? IROperation.INTEGER : IROperation.valueOf(left.getType().name()),
                            new IROperand(left.getValue()),
                            rightAddr,
                            left.getType());
                    code.add(i);
                }

                break;
            case DECLARATION:
                for (TreeElement e : node.getElements()) {
                    try {
                        code.addAll(e.getCode());
                    } catch (UnsupportedOperationException e1) {
                        Leaf l = (Leaf) e;
                        i = new IRInstruction((l.getType() == Type.INT) ? IROperation.INTEGER : IROperation.valueOf(l.getType().name()),
                                new IROperand(l.getValue() == null ? "null" : l.getValue()),
                                new IROperand("0"),
                                l.getType());
                        if (l.getType() == Type.COMPLEX) {
                            complexVars.add(l.getValue());
                        }
                        code.add(i);
                    }
                }
                break;
            case NEW:
                left = (Leaf) node.getElements().get(0);
                i = new IRInstruction(IROperation.NEW,
                        new IROperand(reg.push(left.getValue())), left.getType());
                code.add(i);
                break;
            case POST_INC_OP:
                left = (Leaf) node.getElements().get(0);
                i = new IRInstruction(IROperation.INC,
                        new IROperand(left.getValue() == null ? "null" : left.getValue()), left.getType());
                code.add(i);
                break;
            case POST_DEC_OP:
                left = (Leaf) node.getElements().get(0);
                i = new IRInstruction(IROperation.DEC,
                        new IROperand(left.getValue() == null ? "null" : left.getValue()), left.getType());
                code.add(i);
                break;
            case UMINUS:
            case RE:
            case IM:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    leftAddr = new IROperand(left.getValue());
                } else {
                    Node leftNode = (Node) node.getElements().get(0);
                    code.addAll(leftNode.getCode());
                    leftAddr = new IROperand(reg.pop());
                }
                i = new IRInstruction(IROperation.valueOf(node.getOperation().name()),
                        leftAddr,
                        new IROperand(reg.push("R" + reg.size())), node.getElements().get(0).getType());
                code.add(i);
                break;
            case PLUS:
            case MINUS:
            case STAR:
            case DIVIDE:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    leftAddr = new IROperand(left.getValue());
                } else {
                    Node leftNode = (Node) node.getElements().get(0);
                    code.addAll(leftNode.getCode());
                    leftAddr = new IROperand(reg.pop());
                }
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new IROperand(reg.pop());
                }
                i = new IRInstruction(toMathOperation(node.getOperation()),
                        leftAddr,
                        rightAddr,
                        new IROperand(reg.push("R" + reg.size())), node.getElements().get(0).getType());
                code.add(i);
                break;
            case ARRAY_ACCESS:
                left = (Leaf) node.getElements().get(0);
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new IROperand(reg.pop());
                }
                i = new IRInstruction(IROperation.SUBS,
                        new IROperand(left.getValue()),
                        rightAddr,
                        new IROperand(reg.push("R" + reg.size())), left.getType());
                code.add(i);
                break;
            case EQUAL:
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new IROperand(reg.pop());
                }
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    if (code.size() > 0) {
                        if (code.get(code.size() - 1).getOperation() != IROperation.NEW) {
                            if (left.getType() != Type.COMPLEX) {
                                i = new IRInstruction(IROperation.MOV,
                                        rightAddr,
                                        new IROperand(left.getValue()), left.getType());
                                code.add(i);
                            } else {
                                if (node.getElements().get(1) instanceof Leaf) {
                                    Complex c = Complex.parse(((Leaf) node.getElements().get(1)).getValue());
                                    if (c.getRe() != null) {
                                        i = new IRInstruction(IROperation.SRE,
                                                new IROperand(c.getRe()),
                                                new IROperand(left.getValue()), left.getType());
                                        code.add(i);
                                    }
                                    if (c.getIm() != null) {
                                        i = new IRInstruction(IROperation.SIM,
                                                new IROperand(c.getIm()),
                                                new IROperand(left.getValue()), left.getType());
                                        code.add(i);
                                    }
                                } else {
                                    i = new IRInstruction(IROperation.MOV,
                                            rightAddr,
                                            new IROperand(left.getValue()), left.getType());
                                    code.add(i);
                                }
                            }
                        } else {
                            code.get(code.size() - 1).setSecond(new IROperand(left.getValue()));
                        }
                    } else {
                        if (!complexVars.contains(left.getValue())) {
                            i = new IRInstruction(IROperation.MOV,
                                    rightAddr,
                                    new IROperand(left.getValue()), left.getType());
                            code.add(i);
                        } else {
                            Complex c = Complex.parse(((Leaf) node.getElements().get(1)).getValue());
                            if (c.getRe() != null) {
                                i = new IRInstruction(IROperation.SRE,
                                        new IROperand(c.getRe()),
                                        new IROperand(left.getValue()), left.getType());
                                code.add(i);
                            }
                            if (c.getIm() != null) {
                                i = new IRInstruction(IROperation.SIM,
                                        new IROperand(c.getIm()),
                                        new IROperand(left.getValue()), left.getType());
                                code.add(i);
                            }
                        }
                    }
                } else {
                    code.addAll(((Node) node.getElements().get(0)).getCode());
                    i = new IRInstruction(IROperation.MOV,
                            rightAddr,
                            new IROperand(reg.pop()), node.getType());
                    code.add(i);
                }
                break;
            case AND_OP:
            case OR_OP:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    leftAddr = new IROperand(left.getValue());
                } else {
                    Node right = (Node) node.getElements().get(0);
                    code.addAll(right.getCode());
                    leftAddr = new IROperand(reg.pop());
                }
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new IROperand(reg.peek());
                }
                i = new IRInstruction((node.getOperation() == Operation.AND_OP ? IROperation.AND : IROperation.OR),
                        rightAddr,
                        leftAddr,
                        new IROperand(reg.push("R" + reg.size())), node.getType());
                code.add(i);
                break;
            case LESS:
            case GREATER:
            case EQ_OP:
            case GE_OP:
            case LE_OP:
            case NE_OP:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    leftAddr = new IROperand(left.getValue());
                } else {
                    Node right = (Node) node.getElements().get(0);
                    code.addAll(right.getCode());
                    leftAddr = new IROperand(reg.pop());
                }
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new IROperand(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new IROperand(reg.peek());
                }
                i = new IRInstruction(IROperation.CMP,
                        rightAddr,
                        leftAddr, node.getType());
                code.add(i);
                break;
            case NOT:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    i = new IRInstruction(IROperation.NOT,
                            new IROperand(left.getValue()),
                            new IROperand(reg.push("R" + reg.size())), left.getType());
                } else {
                    Node boolExp = (Node) node.getElements().get(0);
                    code.addAll(boolExp.getCode());
                    i = new IRInstruction(IROperation.NOT,
                            new IROperand(reg.pop()), node.getType());
                }
                code.add(i);
                break;
            case SELECTION_STATEMENT:
            case CONDITIONAL_EXPRESSION:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    i = new IRInstruction(IROperation.BZ,
                            new IROperand(labels.push("L" + labels.size())),
                            left.getType());
                } else {
                    Node leftNode = (Node) node.getElements().get(0);
                    code.addAll(leftNode.getCode());
                    i = compareInstruction(code, leftNode, null);
                }
                code.add(i);
                try {
                    if (node.getElements().get(1) instanceof Leaf) {
                        Leaf first = (Leaf) node.getElements().get(1);
                        i = new IRInstruction(IROperation.MOV,
                                new IROperand(first.getValue()),
                                new IROperand(reg.push("R"+reg.size())),
                                first.getType());
                        code.add(i);
                    } else {
                        code.addAll(node.getElements().get(1).getCode());
                        i = new IRInstruction(IROperation.BRL, new IROperand(labels.peek() + "E"),
                                node.getElements().get(1).getType());
                        code.add(i);
                    }
                    code.add(new IRInstruction(IROperation.DEFL, new IROperand(labels.peek()),
                            node.getElements().get(1).getType()));
                    if (node.getElements().get(2) instanceof Leaf) {
                        Leaf second = (Leaf) node.getElements().get(2);
                        i = new IRInstruction(IROperation.MOV,
                                new IROperand(second.getValue()),
                                new IROperand(reg.peek()),
                                second.getType());
                        code.add(i);
                    } else {
                        code.addAll(node.getElements().get(2).getCode());
                    }
                    code.add(new IRInstruction(IROperation.DEFL, new IROperand(labels.peek() + "E"),
                            node.getElements().get(1).getType()));
                } catch (UnsupportedOperationException ignored) {
                }
                break;
            case ITERATION_STATEMENT:
                if (node.getValue().equals("WHILE")) {
                    i = new IRInstruction(IROperation.DEFL,
                            new IROperand(labels.push("L" + labels.size())), node.getType());
                    code.add(i);
                    if (node.getElements().get(0) instanceof Leaf) {
                        left = (Leaf) node.getElements().get(0);
                        i = new IRInstruction(IROperation.BZ,
                                new IROperand(labels.push("L" + labels.size())),
                                left.getType());
                    } else {
                        Node leftNode = (Node) node.getElements().get(0);
                        code.addAll(leftNode.getCode());
                        i = compareInstruction(code, leftNode, null);
                    }
                    code.add(i);
                    try {
                        code.addAll(node.getElements().get(1).getCode());
                    } catch (UnsupportedOperationException ignored) {
                    }
                    i = new IRInstruction(IROperation.BRL,
                            new IROperand(labels.get(labels.size() - 2)), node.getType());
                    code.add(i);
                    i = new IRInstruction(IROperation.DEFL,
                            new IROperand(labels.peek()), node.getType());
                    code.add(i);
                } else {
                    iterationFor(code, node);
                }
                break;
            case PRINT:
                left = (Leaf) node.getElements().get(0);
                i = new IRInstruction(IROperation.PRINT,
                        new IROperand(left.getValue()), left.getType());
                code.add(i);
                break;
            case SIZEOF:
                left = (Leaf) node.getElements().get(0);
                i = new IRInstruction(IROperation.SIZEOF,
                        new IROperand(left.getValue()), left.getType());
                code.add(i);
                break;
            default:
                for (TreeElement e : node.getElements()) {
                    try {
                        code.addAll(e.getCode());
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
        }
        node.setCode(code);
    }

    private IROperation toMathOperation(Operation op) {
        switch (op) {
            case PLUS:
                return IROperation.ADD;
            case MINUS:
                return IROperation.SUB;
            case STAR:
                return IROperation.MUL;
            case DIVIDE:
                return IROperation.DIV;
        }
        return null;
    }

    private IRInstruction compareInstruction(IRCodeList code, Node node, IROperand rightAddr) {
        IRInstruction i = null;
        Operation parentOp = ((Node) node.getParent()).getOperation();
        if (node.getOperation() == Operation.AND_OP) {
            if (parentOp != Operation.SELECTION_STATEMENT
                    && parentOp != Operation.ITERATION_STATEMENT)
                i = new IRInstruction(IROperation.AND,
                        rightAddr,
                        new IROperand(reg.pop()),
                        rightAddr, node.getType());
            code.add(i);
            i = new IRInstruction(IROperation.BZ,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
        } else if (node.getOperation() == Operation.OR_OP) {
            if (parentOp != Operation.SELECTION_STATEMENT
                    && parentOp != Operation.ITERATION_STATEMENT)
                i = new IRInstruction(IROperation.OR,
                        rightAddr,
                        new IROperand(reg.peek()),
                        rightAddr, node.getType());
            code.add(i);
            i = new IRInstruction(IROperation.BNZ,
                    new IROperand(labels.push("L" + labels.size())),
                     node.getType());
        } else if (node.getOperation() == Operation.NE_OP) {
            i = new IRInstruction(IROperation.BE,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
        } else if (node.getOperation() == Operation.GREATER) {
            i = new IRInstruction(IROperation.BLE,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
        } else if (node.getOperation() == Operation.EQ_OP) {
            i = new IRInstruction(IROperation.BNE,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
        } else if (node.getOperation() == Operation.LESS) {
            i = new IRInstruction(IROperation.BGE,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
        } else if (node.getOperation() == Operation.GE_OP) {
            i = new IRInstruction(IROperation.BL,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
            code.add(i);
            i = new IRInstruction(IROperation.BZ,
                    new IROperand(labels.peek()),
                    node.getType());
        } else if (node.getOperation() == Operation.LE_OP) {
            i = new IRInstruction(IROperation.BG,
                    new IROperand(labels.push("L" + labels.size())),
                    node.getType());
            code.add(i);
            i = new IRInstruction(IROperation.BZ,
                    new IROperand(labels.peek()),
                    node.getType());
        }
        return i;
    }

    private void iterationFor(IRCodeList code, Node node) {
        IROperand rightAddr;
        IRInstruction i = null;
        Leaf left;
        try {
            code.addAll(node.getElements().get(0).getCode());
        } catch (UnsupportedOperationException ignored) {
        }
        i = new IRInstruction(IROperation.DEFL,
                new IROperand(labels.push("L" + labels.size())), node.getType());
        code.add(i);
        if (node.getElements().get(1) instanceof Leaf) {
            left = (Leaf) node.getElements().get(1);
            rightAddr = new IROperand(left.getValue());
            i = new IRInstruction(IROperation.BZ,
                    new IROperand(labels.push("L" + labels.size())),
                    rightAddr, node.getType());
        } else {
            Node leftNode = (Node) node.getElements().get(1);
            code.addAll(leftNode.getCode());
            rightAddr = new IROperand(reg.pop());
            i = compareInstruction(code,
                    ((Node) leftNode.getElements().get(0)),
                    rightAddr);
        }
        code.add(i);
        try {
            code.addAll(node.getElements().get(3).getCode());
            code.addAll(node.getElements().get(2).getCode());
        } catch (UnsupportedOperationException ignored) {
        }
        i = new IRInstruction(IROperation.BRL,
                new IROperand(labels.get(labels.size() - 2)), node.getType());
        code.add(i);
        i = new IRInstruction(IROperation.DEFL,
                new IROperand(labels.peek()), node.getType());
        code.add(i);
    }

}
