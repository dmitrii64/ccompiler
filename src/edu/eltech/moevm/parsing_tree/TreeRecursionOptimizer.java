package edu.eltech.moevm.parsing_tree;

import edu.eltech.moevm.autogen.Parser;

public class TreeRecursionOptimizer implements PTCallback {
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
            PTNode ptnodeChild = (PTNode)child;
            switch (ptnodeChild.getNonterminal()) {
                case POSTFIX_EXPRESSION:
                    // childs = ["postfix_expression","BRACKETLEFT","expression","BRACKETRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BRACKETLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.EXPRESSION && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.BRACKETRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["postfix_expression","RBLEFT","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["postfix_expression","RBLEFT","argument_expression_list","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ARGUMENT_EXPRESSION_LIST && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["postfix_expression","INC_OP"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.INC_OP) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["postfix_expression","DEC_OP"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.DEC_OP) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case ARGUMENT_EXPRESSION_LIST:
                    // childs = ["argument_expression_list","COMMA","assignment_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.ARGUMENT_EXPRESSION_LIST && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ASSIGNMENT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case UNARY_EXPRESSION:
                    // childs = ["INC_OP","unary_expression"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTLeaf && ((PTLeaf)element0).getToken() == Parser.INC_OP && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.UNARY_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["DEC_OP","unary_expression"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTLeaf && ((PTLeaf)element0).getToken() == Parser.DEC_OP && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.UNARY_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["SIZEOF","unary_expression"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTLeaf && ((PTLeaf)element0).getToken() == Parser.SIZEOF && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.UNARY_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case CAST_EXPRESSION:
                    // childs = ["RBLEFT","type_specifier","RBRIGHT","cast_expression"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTLeaf && ((PTLeaf)element0).getToken() == Parser.RBLEFT && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.TYPE_SPECIFIER && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.RBRIGHT && element3 instanceof PTNode && ((PTNode)element3).getNonterminal() == Nonterminals.CAST_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case MULTIPLICATIVE_EXPRESSION:
                    // childs = ["multiplicative_expression","STAR","cast_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.MULTIPLICATIVE_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.STAR && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.CAST_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["multiplicative_expression","SLASH","cast_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.MULTIPLICATIVE_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.SLASH && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.CAST_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["multiplicative_expression","PERCENT","cast_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.MULTIPLICATIVE_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.PERCENT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.CAST_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case ADDITIVE_EXPRESSION:
                    // childs = ["additive_expression","PLUS","multiplicative_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.ADDITIVE_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.PLUS && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.MULTIPLICATIVE_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["additive_expression","MINUS","multiplicative_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.ADDITIVE_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.MINUS && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.MULTIPLICATIVE_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case SHIFT_EXPRESSION:
                    // childs = ["shift_expression","LEFT_OP","additive_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.SHIFT_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.LEFT_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ADDITIVE_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["shift_expression","RIGHT_OP","additive_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.SHIFT_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RIGHT_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ADDITIVE_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case RELATIONAL_EXPRESSION:
                    // childs = ["relational_expression","LESS","shift_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.LESS && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.SHIFT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["relational_expression","GREATER","shift_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.GREATER && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.SHIFT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["relational_expression","LE_OP","shift_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.LE_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.SHIFT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["relational_expression","GE_OP","shift_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.GE_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.SHIFT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case EQUALITY_EXPRESSION:
                    // childs = ["equality_expression","EQ_OP","relational_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.EQUALITY_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.EQ_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["equality_expression","NE_OP","relational_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.EQUALITY_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.NE_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.RELATIONAL_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case AND_EXPRESSION:
                    // childs = ["and_expression","AMP","equality_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.AND_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.AMP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.EQUALITY_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case EXCLUSIVE_OR_EXPRESSION:
                    // childs = ["exclusive_or_expression","CARET","and_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.EXCLUSIVE_OR_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.CARET && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.AND_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case INCLUSIVE_OR_EXPRESSION:
                    // childs = ["inclusive_or_expression","BAR","exclusive_or_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.INCLUSIVE_OR_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BAR && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.EXCLUSIVE_OR_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case LOGICAL_AND_EXPRESSION:
                    // childs = ["logical_and_expression","AND_OP","inclusive_or_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.LOGICAL_AND_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.AND_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.INCLUSIVE_OR_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case LOGICAL_OR_EXPRESSION:
                    // childs = ["logical_or_expression","OR_OP","logical_and_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.LOGICAL_OR_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.OR_OP && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.LOGICAL_AND_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case CONDITIONAL_EXPRESSION:
                    // childs = ["logical_or_expression","QUESTION","expression","COLON","conditional_expression"]
                    if (ptnodeChild.getElements().size() == 5) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        PTElement element4 = ptnodeChild.getElements().get(4);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.LOGICAL_OR_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.QUESTION && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.EXPRESSION && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.COLON && element4 instanceof PTNode && ((PTNode)element4).getNonterminal() == Nonterminals.CONDITIONAL_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            ptnode.insertElementBefore(child, element4);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case ASSIGNMENT_EXPRESSION:
                    // childs = ["postfix_expression","EQUAL","assignment_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.POSTFIX_EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.EQUAL && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ASSIGNMENT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case EXPRESSION:
                    // childs = ["expression","COMMA","assignment_expression"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.EXPRESSION && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.ASSIGNMENT_EXPRESSION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case DECLARATION_SPECIFIERS:
                    // childs = ["type_specifier","declaration_specifiers"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.TYPE_SPECIFIER && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.DECLARATION_SPECIFIERS) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case INIT_DECLARATOR_LIST:
                    // childs = ["init_declarator_list","COMMA","init_declarator"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.INIT_DECLARATOR_LIST && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.INIT_DECLARATOR) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case DIRECT_DECLARATOR:
                    // childs = ["RBLEFT","direct_declarator","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTLeaf && ((PTLeaf)element0).getToken() == Parser.RBLEFT && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_declarator","BRACKETLEFT","constant_expression","BRACKETRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BRACKETLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.CONSTANT_EXPRESSION && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.BRACKETRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_declarator","BRACKETLEFT","BRACKETRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BRACKETLEFT && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.BRACKETRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_declarator","RBLEFT","parameter_list","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.PARAMETER_LIST && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_declarator","RBLEFT","identifier_list","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.IDENTIFIER_LIST && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_declarator","RBLEFT","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case PARAMETER_LIST:
                    // childs = ["parameter_list","COMMA","parameter_declaration"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.PARAMETER_LIST && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.PARAMETER_DECLARATION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case IDENTIFIER_LIST:
                    // childs = ["identifier_list","COMMA","IDENTIFIER"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.IDENTIFIER_LIST && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.IDENTIFIER) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case DIRECT_ABSTRACT_DECLARATOR:
                    // childs = ["direct_abstract_declarator","BRACKETLEFT","BRACKETRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_ABSTRACT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BRACKETLEFT && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.BRACKETRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_abstract_declarator","BRACKETLEFT","constant_expression","BRACKETRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_ABSTRACT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.BRACKETLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.CONSTANT_EXPRESSION && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.BRACKETRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_abstract_declarator","RBLEFT","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_ABSTRACT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTLeaf && ((PTLeaf)element2).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    // childs = ["direct_abstract_declarator","RBLEFT","parameter_list","RBRIGHT"]
                    if (ptnodeChild.getElements().size() == 4) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        PTElement element3 = ptnodeChild.getElements().get(3);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DIRECT_ABSTRACT_DECLARATOR && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.RBLEFT && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.PARAMETER_LIST && element3 instanceof PTLeaf && ((PTLeaf)element3).getToken() == Parser.RBRIGHT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            ptnode.insertElementBefore(child, element3);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case INITIALIZER_LIST:
                    // childs = ["initializer_list","COMMA","initializer"]
                    if (ptnodeChild.getElements().size() == 3) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        PTElement element2 = ptnodeChild.getElements().get(2);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.INITIALIZER_LIST && element1 instanceof PTLeaf && ((PTLeaf)element1).getToken() == Parser.COMMA && element2 instanceof PTNode && ((PTNode)element2).getNonterminal() == Nonterminals.INITIALIZER) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            ptnode.insertElementBefore(child, element2);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case DECLARATION_LIST:
                    // childs = ["declaration_list","declaration"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.DECLARATION_LIST && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.DECLARATION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case STATEMENT_LIST:
                    // childs = ["statement_list","statement"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.STATEMENT_LIST && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.STATEMENT) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
                case TRANSLATION_UNIT:
                    // childs = ["translation_unit","external_declaration"]
                    if (ptnodeChild.getElements().size() == 2) {
                        PTElement element0 = ptnodeChild.getElements().get(0);
                        PTElement element1 = ptnodeChild.getElements().get(1);
                        if (element0 instanceof PTNode && ((PTNode)element0).getNonterminal() == Nonterminals.TRANSLATION_UNIT && element1 instanceof PTNode && ((PTNode)element1).getNonterminal() == Nonterminals.EXTERNAL_DECLARATION) {
                            ptnode.insertElementBefore(child, element0);
                            ptnode.insertElementBefore(child, element1);
                            System.out.println("(recursive) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(child);
                            i--;
                            break;
                        }
                    }
                    break;
            }
        }
    }
}
