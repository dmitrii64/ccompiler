package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.common.Operation;
import edu.eltech.moevm.syntax_tree.*;
import edu.eltech.moevm.syntax_tree.UnsupportedOperationException;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by lazorg on 12/10/15.
 */
public class CodeGenerator {
    private CodeList code = new CodeList();
    private Instruction lastDeclaration;
    private String binaryOperand;
    private boolean isAdd = true;
    private String r1 = "R1";
    private String r2 = "R2";

    public CodeList generate(SyntaxTree tree) {

        generate(tree.getRoot());

        return code;
    }

    private void generate(TreeElement node) {
        try {
            for (TreeElement e : node.getElements()) {
                if (e != null)
                    generate(e);
            }
        } catch (UnsupportedOperationException ignored) {
        } finally {
            createCommand(node);
        }
    }

    private Instruction createCommand(TreeElement node) {
        Instruction i = null;
        if (node instanceof Node) {
            Node n = (Node) node;
            try {
                i = new Instruction(IROperation.valueOf(n.getOperation().name()),
                        new Address(n.getValue()));
            } catch (IllegalArgumentException e) {
                i = new Instruction(IROperation.CHAR,
                        new Address(n.getValue()));
            }
        } else {
            Leaf l = (Leaf) node;
            switch (((Node) l.getParent()).getOperation()) {
                case INIT_DECLARATOR:
                    switch (l.getOperand()) {
                        case IDENTIFIER:
                            i = new Instruction(IROperation.valueOf(l.getOperand().name()),
                                    new Address(l.getValue() == null ? "null" : l.getValue()));
                            if (lastDeclaration != null) {
                                i.setOperation(lastDeclaration.getOperation());
                            }
                            break;
                        case CONSTANT:
                            i = new Instruction(IROperation.MOV,
                                    new Address(l.getValue()),
                                    code.get(code.size() - 1).getFirst());
                            break;
                    }

                    break;
                case DECLARATION:
                    i = new Instruction(IROperation.valueOf(l.getType().name()),
                            new Address(l.getValue() == null ? "null" : l.getValue()));
                    lastDeclaration = i;
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
                    if (binaryOperand == null) {
                        binaryOperand = l.getValue();
                    } else {
                        i = new Instruction(IROperation.ADD,
                                new Address(binaryOperand),
                                new Address(l.getValue()),
                                new Address(r1));
                        isAdd = false;
                        code.add(i);
                        if (code.get(code.size() - 2).getOperation() == IROperation.MOV) {
                            Collections.swap(code.getInstructions(), code.size() - 1, code.size() - 2);
                        }
                    }
                    break;
                case EQUAL:
                    i = new Instruction(IROperation.MOV,
                            new Address(r1),
                            new Address(l.getValue()));
                    binaryOperand = null;
                    break;
            }
            if (binaryOperand == null && isAdd) {
                code.add(i);
            }
            isAdd = true;
        }

        return i;
    }

}
