package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.common.Type;

/**
 * Created by lazorg on 12/28/15.
 */
public class TypeValidator implements TreeCallback {
    @Override
    public void processElement(TreeElement e, int level) {
        if (e instanceof Node) {
            Node node = (Node) e;
            switch (node.getOperation()) {
                case EQUAL:
                case STAR:
                case PLUS:
                case MINUS:
                case DIVIDE:
                    TreeElement left = node.getElements().get(0);
                    TreeElement right = node.getElements().get(1);
                    if (left.getType() == right.getType())
                        node.setType(left.getType());
                    else {
                        try {
                            throw new TypeMismatchException();
                        } catch (TypeMismatchException e1) {
                            System.out.println("Type mismatch!");
                            e1.printStackTrace();
                        }
                    }
                    break;
                case RE:
                case IM:
                    node.setType(Type.FLOAT);
                    break;
                case UMINUS:
                    TreeElement single = node.getElements().get(0);
                    node.setType(single.getType());
                    break;
                case SRE:
                case SIM:
                    TreeElement complex = node.getElements().get(0);
                    node.setType(complex.getType());
                    TreeElement arg = node.getElements().get(1);
                    if (arg.getType() != Type.FLOAT)
                        try {
                            throw new TypeMismatchException();
                        } catch (TypeMismatchException e1) {
                            System.out.println("2nd argument type mismatch!");
                            e1.printStackTrace();
                        }
                    break;
            }
        }
    }
}
