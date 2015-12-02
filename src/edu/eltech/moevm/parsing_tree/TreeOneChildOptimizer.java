package edu.eltech.moevm.parsing_tree;

/**
 * Created by ivan on 06.11.15.
 */
public class TreeOneChildOptimizer implements PTCallback {
    @Override
    public void processElement(PTElement e, int level) {
        if (!(e instanceof PTNode)) {
            return;
        }
        PTNode ptnode = (PTNode) e;
        for (int i = 0; i < ptnode.getElements().size(); i++) {
            PTElement child = ptnode.getElements().get(i);
            if (!(child instanceof PTNode)) {
                continue;
            }
            PTNode ptnodeChild = (PTNode) child;
            if (ptnodeChild.getElements().size() == 1) {
                ptnodeChild.getElements().get(0).setParent(ptnode);
                ptnode.insertElementBefore(ptnodeChild, ptnodeChild.getElements().get(0));
                System.out.println("(one child) removed " + ptnodeChild.getNonterminal());
                ptnode.remove(ptnodeChild);
                i--;
            }
        }
    }
}
