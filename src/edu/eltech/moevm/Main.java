package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.parsing_tree.*;
import edu.eltech.moevm.syntax_tree.*;

import java.io.*;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("================Input file================");
        String filename = "test.c";
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader br = new BufferedReader(reader);
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            ParsingTree tree = (ParsingTree) Parser.ParseFile(filename).obj;
            //tree.infixVisit(new TreeOptimizer());
            tree.infixVisit(new PTCallback() {
                @Override
                public void processElement(PTElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof PTLeaf) {
                        System.out.print("leaf [" + Parser.getTokenName(((PTLeaf) e).getToken()) + "]");
                        if (((PTLeaf) e).getValue() != null)
                            System.out.print(" (" + ((PTLeaf) e).getValue() + ")");

                    }
                    else {
                        System.out.print("node [" + ((PTNode) e).getNonterminal() + "]");
                        if (((PTNode) e).getValue() != null)
                            System.out.print(" (" + ((PTNode) e).getValue() + ")");

                    }
                    System.out.println();

                }
            });

            tree.infixVisit(new TreeOptimizer());
            tree.infixVisit(new PTCallback() {
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
                        if (ptnodeChild.getElements().size() == 1) {
                            ptnode.insertElementBefore(ptnodeChild, ptnodeChild.getElements().get(0));
                            System.out.println("(one child) removed " + ptnodeChild.getNonterminal());
                            ptnode.remove(ptnodeChild);
                            i--;
                        }
                    }
                }
            });
            tree.infixVisit(new PTCallback() {
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
                        switch (ptnodeChild.getNonterminal()) {
                            case DIRECT_DECLARATOR:
                            case PARAMETER_LIST:
                                List<PTElement> elements = ptnodeChild.getElements();
                                for (int j = 0; j < elements.size(); j++) {
                                    ptnode.insertElementBefore(child, elements.get(j));
                                }
                                ptnode.remove(child);
                                i--;
                        }
                    }
                }
            });

            System.out.println("================Parsing Tree================");

            tree.infixVisit(new PTCallback() {
                @Override
                public void processElement(PTElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof PTLeaf) {
                        System.out.print("leaf [" + Parser.getTokenName(((PTLeaf) e).getToken()) + "]");
                        if (((PTLeaf) e).getValue() != null)
                            System.out.print(" (" + ((PTLeaf) e).getValue() + ")");


                    }
                    else {
                        System.out.print("node [" + ((PTNode) e).getNonterminal() + "]");
                        if (((PTNode) e).getValue() != null)
                            System.out.print(" (" + ((PTNode) e).getValue() + ")");

                    }
                    System.out.println();

                }
            });
            TreeJsGenerator treeJsGenerator = new TreeJsGenerator();
            tree.infixVisit(treeJsGenerator);
            FileWriter fileWriter = new FileWriter("parsing_tree.html");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String str = treeJsGenerator.genTree();
            bufferedWriter.write(str);
            bufferedWriter.close();
            fileWriter.close();

            TreeGenerator treeGenerator = new TreeGenerator();
            SyntaxTree syntaxTree = treeGenerator.generate(tree);

            System.out.println("================Syntax Tree================");
            syntaxTree.infixVisit(new TreeCallback() {
                @Override
                public void processElement(TreeElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof Leaf) {
                        System.out.print("leaf [" + ((Leaf) e).getOperand().name() + "]");
                        if (((Leaf) e).getValue() != null)
                            System.out.print(" (" + ((Leaf) e).getValue() + ")");
                        if (((Leaf) e).getType() != null)
                            System.out.print(" <" + ((Leaf) e).getType().name() + ">");
                    } else {
                        System.out.print("node [" + ((Node) e).getOperation().name() + "]");
                        if (((Node) e).getValue() != null)
                            System.out.print(" (" + ((Node) e).getValue() + ")");
                        if (((Node) e).getType() != null)
                            System.out.print(" <" + ((Node) e).getType().name() + ">");

                    }
                    System.out.println();
                }
            });

            //Reset ids
            syntaxTree.infixVisit(new TreeCallback() {
                int id = 0;

                @Override
                public void processElement(TreeElement e, int level) {
                    e.setId(id);
                    id++;
                }
            });


            SyntaxTreeJsGenerator syntaxTreeJsGenerator = new SyntaxTreeJsGenerator();
            syntaxTree.infixVisit(syntaxTreeJsGenerator);
            FileWriter stfileWriter = new FileWriter("syntax_tree.html");
            BufferedWriter stbufferedWriter = new BufferedWriter(stfileWriter);
            String ststr = syntaxTreeJsGenerator.genTree();
            stbufferedWriter.write(ststr);
            stbufferedWriter.close();
            stfileWriter.close();

            syntaxTree.verifyNameScopes();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SyntaxTree.NameScopeException e) {
            e.printStackTrace();
        }
    }
}
