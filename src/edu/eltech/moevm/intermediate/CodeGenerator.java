package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.common.Operation;
import edu.eltech.moevm.common.Type;
import edu.eltech.moevm.syntax_tree.*;
import edu.eltech.moevm.syntax_tree.UnsupportedOperationException;

import java.util.Stack;

/**
 * Created by lazorg on 12/10/15.
 */
public class CodeGenerator {
    private CodeList code = new CodeList();
    private CodeList tempCode = new CodeList();
    private Stack<CodeList> childs = new Stack<>();
    private Stack<String> operands = new Stack<>();
    private Stack<Leaf> stack = new Stack<>();
    private Stack<String> reg = new Stack<>();
    private Stack<String> labels = new Stack<>();

    public CodeList generate(SyntaxTree tree) {

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
        CodeList code = new CodeList();
        Instruction i = null;
        Leaf left = null;
        Address rightAddr;
        Address leftAddr;
        switch (node.getOperation()) {
            case INIT_DECLARATOR:
                left = (Leaf) node.getElements().get(0);
                Leaf value = (Leaf) node.getElements().get(1);
                i = new Instruction((left.getType() == Type.INT) ? IROperation.INTEGER : IROperation.valueOf(left.getType().name()),
                        new Address(left.getValue()));
                code.add(i);
                i = new Instruction(IROperation.MOV,
                        new Address(value.getValue()),
                        new Address(left.getValue()));
                code.add(i);
                break;
            case DECLARATION:
                for (TreeElement e : node.getElements()) {
                    try {
                        code.addAll(e.getCode());
                    } catch (UnsupportedOperationException e1) {
                        Leaf l = (Leaf) e;
                        i = new Instruction(IROperation.valueOf(l.getType().name()),
                                new Address(l.getValue() == null ? "null" : l.getValue()));
                        code.add(i);
                    }
                }
                break;
            case POST_INC_OP:
                left = (Leaf) node.getElements().get(0);
                i = new Instruction(IROperation.INC,
                        new Address(left.getValue() == null ? "null" : left.getValue()));
                code.add(i);
                break;
            case POST_DEC_OP:
                left = (Leaf) node.getElements().get(0);
                i = new Instruction(IROperation.DEC,
                        new Address(left.getValue() == null ? "null" : left.getValue()));
                code.add(i);
                break;
            case PLUS:
            case MINUS:
            case STAR:
            case DIVIDE:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    leftAddr = new Address(left.getValue());
                } else {
                    Node leftNode = (Node) node.getElements().get(0);
                    code.addAll(leftNode.getCode());
                    leftAddr = new Address(reg.pop());
                }
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new Address(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new Address(reg.pop());
                }
                i = new Instruction(toMathOperation(node.getOperation()),
                        rightAddr,
                        leftAddr,
                        new Address(reg.push("R" + reg.size())));
                code.add(i);
                break;
            case EQUAL:
                left = (Leaf) node.getElements().get(0);
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new Address(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new Address(reg.pop());
                }
                i = new Instruction(IROperation.MOV,
                        rightAddr,
                        new Address(left.getValue()));
                code.add(i);
                break;
            case AND_OP:
            case OR_OP:
                Operation parent = ((Node) node.getParent()).getOperation();
                if (parent != Operation.SELECTION_STATEMENT && parent != Operation.ITERATION_STATEMENT) {
                    if (node.getElements().get(0) instanceof Leaf) {
                        Leaf right = (Leaf) node.getElements().get(0);
                        leftAddr = new Address(right.getValue());
                    } else {
                        Node right = (Node) node.getElements().get(0);
                        code.addAll(right.getCode());
                        leftAddr = new Address(reg.pop());
                    }
                    if (node.getElements().get(1) instanceof Leaf) {
                        Leaf right = (Leaf) node.getElements().get(1);
                        rightAddr = new Address(right.getValue());
                    } else {
                        Node right = (Node) node.getElements().get(1);
                        code.addAll(right.getCode());
                        rightAddr = new Address(reg.peek());
                    }
                    i = new Instruction((node.getOperation() == Operation.AND_OP ? IROperation.AND : IROperation.OR),
                            rightAddr,
                            leftAddr,
                            new Address(reg.push("R" + reg.size())));
                    code.add(i);
                }
                break;
            case LESS:
            case GREATER:
            case EQ_OP:
            case GE_OP:
            case LE_OP:
            case NE_OP:
                if (node.getElements().get(0) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(0);
                    leftAddr = new Address(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(0);
                    code.addAll(right.getCode());
                    leftAddr = new Address(reg.pop());
                }
                if (node.getElements().get(1) instanceof Leaf) {
                    Leaf right = (Leaf) node.getElements().get(1);
                    rightAddr = new Address(right.getValue());
                } else {
                    Node right = (Node) node.getElements().get(1);
                    code.addAll(right.getCode());
                    rightAddr = new Address(reg.pop());
                }
                i = new Instruction(IROperation.CMP,
                        rightAddr,
                        leftAddr,
                        new Address(reg.push("R" + reg.size())));
                code.add(i);
                break;
            case SELECTION_STATEMENT:
                if (node.getElements().get(0) instanceof Leaf) {
                    left = (Leaf) node.getElements().get(0);
                    rightAddr = new Address(left.getValue());
                    i = new Instruction(IROperation.BF,
                            new Address(labels.push("L" + labels.size())),
                            rightAddr);
                } else {
                    Node leftNode = (Node) node.getElements().get(0);
                    code.addAll(leftNode.getCode());
                    rightAddr = new Address(reg.pop());
                    i = compareInstruction(code, leftNode, rightAddr);
                }
                code.add(i);
                try {
                    code.addAll(node.getElements().get(1).getCode());
                    code.add(new Instruction(IROperation.DEFL, new Address(labels.peek())));
                    code.addAll(node.getElements().get(2).getCode());
                } catch (UnsupportedOperationException ignored) {
                }
                break;
            case ITERATION_STATEMENT:
                if (node.getValue().equals("WHILE")) {
                    i = new Instruction(IROperation.DEFL,
                            new Address(labels.push("L" + labels.size())));
                    code.add(i);
                    if (node.getElements().get(0) instanceof Leaf) {
                        left = (Leaf) node.getElements().get(0);
                        rightAddr = new Address(left.getValue());
                        i = new Instruction(IROperation.BF,
                                new Address(labels.push("L" + labels.size())),
                                rightAddr);
                    } else {
                        Node leftNode = (Node) node.getElements().get(0);
                        code.addAll(leftNode.getCode());
                        rightAddr = new Address(reg.pop());
                        i = compareInstruction(code, leftNode, rightAddr);
                    }
                    code.add(i);
                    try {
                        code.addAll(node.getElements().get(1).getCode());
                    } catch (UnsupportedOperationException ignored) {
                    }
                    i = new Instruction(IROperation.BRL,
                            new Address(labels.get(labels.size() - 2)));
                    code.add(i);
                    i = new Instruction(IROperation.DEFL,
                            new Address(labels.peek()));
                    code.add(i);
                } else {
                    iterationFor(code, node);
                }
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

    private Instruction compareInstruction(CodeList code, Node node, Address rightAddr) {
        Instruction i = null;
        if (node.getOperation() == Operation.AND_OP) {
            i = new Instruction(IROperation.AND,
                    rightAddr,
                    new Address(reg.peek()),
                    new Address(reg.push("R" + reg.size())));
            code.add(i);
//            i = new Instruction(IROperation.CMP,
//                    new Address(reg.get(reg.size() - 2)),
//                    new Address("true"),
//                    new Address(reg.push("R" + reg.size())));
//            code.add(i);
//            i = new Instruction(IROperation.CMP,
//                    new Address(reg.pop()),
//                    new Address(reg.pop()),
//                    new Address(reg.push("R" + reg.size())));
//            code.add(i);
            i = new Instruction(IROperation.BF,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.OR_OP) {
            i = new Instruction(IROperation.OR,
                    rightAddr,
                    new Address(reg.peek()),
                    new Address(reg.push("R" + reg.size())));
            code.add(i);
//            i = new Instruction(IROperation.CMP,
//                    new Address(reg.get(reg.size() - 2)),
//                    new Address("true"),
//                    new Address(reg.push("R" + reg.size())));
//            code.add(i);
//            i = new Instruction(IROperation.CMP,
//                    new Address(reg.pop()),
//                    new Address(reg.pop()),
//                    new Address(reg.push("R" + reg.size())));
//            code.add(i);
            i = new Instruction(IROperation.BNF,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.NE_OP) {
            i = new Instruction(IROperation.BZL,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.GREATER) {
            i = new Instruction(IROperation.BPL,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.EQ_OP) {
            i = new Instruction(IROperation.BNZ,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.LESS) {
            i = new Instruction(IROperation.BML,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else if (node.getOperation() == Operation.GE_OP) {
            i = new Instruction(IROperation.BPL,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
            code.add(i);
            i = new Instruction(IROperation.BZL,
                    new Address(labels.peek()),
                    rightAddr);
        } else if (node.getOperation() == Operation.LE_OP) {
            i = new Instruction(IROperation.BML,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
            code.add(i);
            i = new Instruction(IROperation.BZL,
                    new Address(labels.peek()),
                    rightAddr);
        }
        return i;
    }

    private void iterationFor(CodeList code, Node node) {
        Address rightAddr;
        Instruction i = null;
        Leaf left;
        try {
            code.addAll(node.getElements().get(0).getCode());
        } catch (UnsupportedOperationException ignored) {
        }
        i = new Instruction(IROperation.DEFL,
                new Address(labels.push("L" + labels.size())));
        code.add(i);
        if (node.getElements().get(1) instanceof Leaf) {
            left = (Leaf) node.getElements().get(1);
            rightAddr = new Address(left.getValue());
            i = new Instruction(IROperation.BF,
                    new Address(labels.push("L" + labels.size())),
                    rightAddr);
        } else {
            Node leftNode = (Node) node.getElements().get(1);
            code.addAll(leftNode.getCode());
            rightAddr = new Address(reg.pop());
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
        i = new Instruction(IROperation.BRL,
                new Address(labels.get(labels.size() - 2)));
        code.add(i);
        i = new Instruction(IROperation.DEFL,
                new Address(labels.peek()));
        code.add(i);
    }

}
