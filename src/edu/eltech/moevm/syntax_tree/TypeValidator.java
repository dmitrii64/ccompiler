package edu.eltech.moevm.syntax_tree;

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
                        System.out.println("Type mismatch at " + node.getOperation().name());
                    }
                    break;
                case RE:
                case IM:
                case UMINUS:
                    TreeElement single = node.getElements().get(0);
                    node.setType(single.getType());
                    break;
                case SRE:
                case SIM:
                    TreeElement complex = node.getElements().get(0);
                    node.setType(complex.getType());
                    TreeElement arg = node.getElements().get(1);
                    //node.setType(complex.getType());
                    break;
            }
        }
    }
}
