package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.common.Operation;
import edu.eltech.moevm.syntax_tree.Leaf;
import edu.eltech.moevm.syntax_tree.Node;
import edu.eltech.moevm.syntax_tree.TreeElement;
import edu.eltech.moevm.syntax_tree.UnsupportedOperationException;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by vladimir on 13.12.15.
 */
public class ReduceObserver {
    private static ReduceObserver instance = new ReduceObserver();
    private CodeList code = new CodeList();
    private CodeList tempCode = new CodeList();
    private Stack<String> operands = new Stack<>();
    private Stack<Leaf> stack = new Stack<>();
    private Stack<String> reg = new Stack<>();
    private Stack<String> labels = new Stack<>();

    private ReduceObserver() {
    }

    public static ReduceObserver getInstance() {
        if (instance == null) {
            instance = new ReduceObserver();
        }
        return instance;
    }

    public void generate(TreeElement e) {
        Instruction i = null;
        if (e instanceof Node) {
            Node n = (Node) e;
            switch (n.getOperation()) {
                case INIT_DECLARATOR:
                    Leaf cnst = stack.pop();
                    Leaf id = stack.pop();
                    i = new Instruction(IROperation.valueOf(id.getType().name()),
                            new Address(id.getValue()));
                    code.add(i);
                    i = new Instruction(IROperation.MOV,
                            new Address(cnst.getValue()),
                            new Address(id.getValue()));
                    code.add(i);
                    break;
                case PLUS:
                case MINUS:
                case STAR:
                case DIVIDE:
                    i = new Instruction(toMathOperation(n.getOperation()),
                            new Address(operands.pop()),
                            new Address(operands.pop()),
                            new Address(reg.push("R" + reg.size())));
                    operands.push(reg.peek());
                    code.add(i);
                    break;
                case EQUAL:
                    try {
                        i = new Instruction(IROperation.MOV,
                                new Address(reg.pop()),
                                new Address(((Leaf) n.getElements().get(0)).getValue()));
                    } catch (UnsupportedOperationException e1) {
                        e1.printStackTrace();
                    } catch (EmptyStackException e2) {
                        try {
                            i = new Instruction(IROperation.MOV,
                                    new Address(((Leaf) n.getElements().get(1)).getValue()),
                                    new Address(((Leaf) n.getElements().get(0)).getValue()));
                        } catch (UnsupportedOperationException e1) {
                            e1.printStackTrace();
                        }
                    }
                    code.add(i);
                    reg.clear();
                    operands.clear();
                    break;
                case SELECTION_STATEMENT:
                    i = new Instruction(IROperation.DEFL, new Address(labels.peek()));
                    code.add(i);
                    break;
            }
        } else {
            Leaf l = (Leaf) e;
            Operation op = ((Node) l.getParent()).getOperation();
            switch (op) {
                case INIT_DECLARATOR:
                    switch (l.getOperand()) {
                        case IDENTIFIER:
                        case CONSTANT:
                            stack.push(l);
                            break;
                    }
                    break;
                case DECLARATION:
                    i = new Instruction(IROperation.valueOf(l.getType().name()),
                            new Address(l.getValue() == null ? "null" : l.getValue()));
                    break;
                case POST_INC_OP:
                    i = new Instruction(IROperation.INC,
                            new Address(l.getValue() == null ? "null" : l.getValue()));
                    break;
                case POST_DEC_OP:
                    i = new Instruction(IROperation.DEC,
                            new Address(l.getValue() == null ? "null" : l.getValue()));
                    break;
                case PLUS:
                case MINUS:
                case STAR:
                case DIVIDE:
                    operands.push(l.getValue());
                    break;
                case SELECTION_STATEMENT:
                    i = new Instruction(IROperation.BF,
                            new Address(labels.push("L" + labels.size())),
                            new Address(l.getValue()));
                    break;

            }
            if (i != null) {
                code.add(i);
            }
        }
    }

    public void printCode() {
        for (Instruction i : code.getInstructions()) {
            System.out.println(i);
        }
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

//    private IROperation toBoolOperation(Operation op) {
//        switch (op) {
//            case GREATER:
//                return IROperation.;
//            case LESS:
//                return IROperation.SUB;
//            case GE_OP:
//                return IROperation.MUL;
//            case LE_OP:
//                return IROperation.DIV;
//            case EQ_OP:
//                return IROperation.DIV;
//            case NE_OP:
//                return IROperation.DIV;
//        }
//        return null;
//    }
}
