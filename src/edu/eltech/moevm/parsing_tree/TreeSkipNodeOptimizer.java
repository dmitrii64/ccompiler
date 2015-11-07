package edu.eltech.moevm.parsing_tree;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ivan on 06.11.15.
 */
public class TreeSkipNodeOptimizer implements PTCallback {
    List<Nonterminals> nonterminals;

    public TreeSkipNodeOptimizer(Nonterminals... nonterminals) {
        this.nonterminals = Arrays.asList(nonterminals);
    }
    @Override
    public void processElement(PTElement e, int level) {
        if (!(e instanceof PTNode)) {
            return;
        }
        PTNode ptnode = (PTNode)e;
        for (int i = 0; i < ptnode.getElements().size(); i++) {
            PTElement child = ptnode.getElements().get(i);
            if (!(child instanceof PTNode)) {
                continue;
            }
            PTNode ptnodeChild = (PTNode) child;

            if (nonterminals.contains(ptnodeChild.getNonterminal())) {
                List<PTElement> elements = ptnodeChild.getElements();
                for (int j = 0; j < elements.size(); j++) {
                    ptnode.insertElementBefore(child, elements.get(j));
                }
                System.out.println("(skip node) removed " + ptnodeChild.getNonterminal());
                ptnode.remove(child);
                i--;
            }
        }
    }
}
