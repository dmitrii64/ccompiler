package edu.eltech.moevm.syntax_tree;

import java.util.ArrayList;

/**
 * Created by lazorg on 11/1/15.
 */
public class TreeOptimizer implements TreeCallback {
    @Override
    public void processElement(TreeElement e, int level) {
        if(e instanceof Node)
            try {
                optimizeNode((Node) e);
            } catch (UnsupportedOperationException e1) {
                e1.printStackTrace();
            }
        else
            optimizeLeaf((Leaf) e);
    }

    private void optimizeNode(Node node) throws UnsupportedOperationException {
        if(node.getOperation()==Operation.ROOT) {
            while (node.haveAny(Operation.TR_UNIT))
                reduce(node, Operation.ROOT, Operation.TR_UNIT);
            while (node.haveAny(Operation.FUNC_DEF))
                reduce(node, Operation.ROOT, Operation.FUNC_DEF);
        }

        if (node.getOperation() == Operation.FUNCTION_DEFINITION) {
            while (node.haveAny(Operation.DIRECT_DEC_FUNC))
                reduce(node, Operation.FUNCTION_DEFINITION, Operation.DIRECT_DEC_FUNC);
            Leaf id = (Leaf) node.getElements().get(0);
            node.setValue(id.getValue());
            node.remove(id);
            if (node.getElements().get(0) instanceof Leaf) {
                Leaf type = (Leaf) node.getElements().get(0);
                node.setType(Type.valueOf(type.getOperand().name()));
                node.remove(type);
            } else {
                Leaf type = (Leaf) node.getElements().get(1);
                node.setType(Type.valueOf(type.getOperand().name()));
                node.remove(type);
            }
        }

        if (node.getOperation() == Operation.FUNC_CALL) {
            while (node.haveAny(Operation.ARGUMENT_EXP_LIST))
                reduce(node, Operation.FUNC_CALL, Operation.ARGUMENT_EXP_LIST);
            Leaf id = (Leaf) node.getElements().get(node.getElements().size() - 1);
            node.setValue(id.getValue());
            node.remove(id);
        }

        if (node.getOperation() == Operation.VARIABLE_DECLARATION) {
            Leaf type = (Leaf) node.getElements().get(0);
            if (node.getElements().get(node.getElements().size() - 1) instanceof Leaf) {
                Leaf id = (Leaf) (node.getElements().get(node.getElements().size() - 1));
                id.setType(Type.valueOf(type.getOperand().name()));
                node.remove(type);
            }
        }

        if (node.getOperation() == Operation.FUNCTION_PARAMETER) {
            Leaf type = (Leaf) node.getElements().get(0);
            if (node.getElements().get(node.getElements().size() - 1) instanceof Leaf) {
                Leaf id = (Leaf) (node.getElements().get(node.getElements().size() - 1));
                id.setType(Type.valueOf(type.getOperand().name()));
                node.remove(type);
            }
        }

        if (node.getOperation() == Operation.DECLARATIONS) {
            while (node.haveAny(Operation.DECLARATIONS))
                reduce(node, Operation.DECLARATIONS, Operation.DECLARATIONS);
        }

        if (node.getOperation() == Operation.STATEMENTS) {
            while (node.haveAny(Operation.STATEMENTS))
                reduce(node, Operation.STATEMENTS, Operation.STATEMENTS);
        }
    }

    private void reduce(Node node, Operation op1, Operation op2) throws UnsupportedOperationException {
        if(node.getOperation()==op1)
        {
            ArrayList<TreeElement> addlist = new ArrayList<TreeElement>();
            ArrayList<TreeElement> rmlist = new ArrayList<TreeElement>();
            for(TreeElement child : node.getElements())
            {
                if(child instanceof Node)
                if(((Node)child).getOperation() == op2) {
                    for (TreeElement subchild : child.getElements()) {
                        addlist.add(subchild);
                        subchild.setParent(node);
                    }
                    rmlist.add(child);
                }
            }
            for(TreeElement child : rmlist)
                node.remove(child);
            for(TreeElement child : node.getElements())
                addlist.add(child);
            node.clear();
            for(TreeElement t : addlist)
                node.add(t);
        }
    }

    private void optimizeLeaf(Leaf leaf)
    {

    }

}
