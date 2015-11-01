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

                for (TreeElement te : node.getElements())
                    if (te instanceof Leaf) {
                        if (Type.valueOf(((Leaf) te).getOperand().name()) != null) {
                            Leaf type = (Leaf) node.getElements().get(1);
                            node.setType(Type.valueOf(type.getOperand().name()));
                            node.remove(type);
                        }
                    }


            }
            ArrayList<TreeElement> addlist = new ArrayList<TreeElement>();
            ArrayList<TreeElement> rmlist = new ArrayList<TreeElement>();
            for (TreeElement te : node.getElements())
                if (te instanceof Node)
                    if (((Node) te).getOperation() == Operation.FUNCTION_PARAMETER) {
                        Leaf typeleaf = (Leaf) te.getElements().get(0);
                        if (te.getElements().get(te.getElements().size() - 1) instanceof Leaf) {
                            Leaf id2 = (Leaf) (te.getElements().get(te.getElements().size() - 1));
                            id2.setType(Type.valueOf(typeleaf.getOperand().name()));
                            te.remove(typeleaf);
                        }
                        addlist.add(te.getElements().get(0));
                        rmlist.add(te);
                    }
            for (TreeElement rm : rmlist)
                node.remove(rm);
            for (TreeElement add : node.getElements())
                addlist.add(add);
            node.clear();
            for (TreeElement addnew : addlist)
                node.add(addnew);

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

        if (node.getOperation() == Operation.PARAM_LIST) {

            for (TreeElement par : node.getElements()) {
                Leaf type = (Leaf) par.getElements().get(0);
                if (par.getElements().get(par.getElements().size() - 1) instanceof Leaf) {
                    Leaf id = (Leaf) (par.getElements().get(par.getElements().size() - 1));
                    id.setType(Type.valueOf(type.getOperand().name()));
                    par.remove(type);
                }
            }
            while (node.haveAny(Operation.FUNCTION_PARAMETER))
                reduce(node, Operation.PARAM_LIST, Operation.FUNCTION_PARAMETER);
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
