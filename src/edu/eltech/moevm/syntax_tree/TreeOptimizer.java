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
        if(node.getOperation()==Operation.ROOT)
            while (node.haveAny(Operation.TR_UNIT))
                reduce(node, Operation.ROOT, Operation.TR_UNIT);

        if(node.getOperation()==Operation.FNDEF_2)
            while (node.haveAny(Operation.DIRECT_DEC_FUNC))
                reduce(node, Operation.FNDEF_2, Operation.DIRECT_DEC_FUNC);

        if (node.getOperation() == Operation.FUNC_CALL)
            while (node.haveAny(Operation.ARGUMENT_EXP_LIST))
                reduce(node, Operation.FUNC_CALL, Operation.ARGUMENT_EXP_LIST);

    }

    private void reduce(Node node, Operation op1, Operation op2) throws UnsupportedOperationException {
        if(node.getOperation()==op1)
        {
            ArrayList<TreeElement> addlist = new ArrayList<>();
            ArrayList<TreeElement> rmlist = new ArrayList<>();
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
