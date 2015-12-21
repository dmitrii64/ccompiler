package edu.eltech.moevm.parsing_tree;

import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.common.Nonterminals;

import java.util.List;

/**
 * Created by lazorg on 12/21/15.
 */
public class TreeParenthesesOptimizer implements PTCallback {
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

            if (Nonterminals.PRIMARY_EXPRESSION == ptnodeChild.getNonterminal()) {
                if (((PTLeaf) ptnodeChild.getElements().get(0)).getToken() == Parser.RBLEFT) {
                    List<PTElement> elements = ptnodeChild.getElements();
                    elements.get(1).setParent(ptnode);
                    ptnode.insertElementBefore(child, elements.get(1));

                    System.out.println("(skip node) removed " + ptnodeChild.getNonterminal());
                    ptnode.remove(child);
                    i--;
                }


            }
        }
    }
}
