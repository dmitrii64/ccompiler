package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.parsing_tree.*;

import java.io.*;


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

            System.out.println("------------+======---------------");

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
            FileWriter fileWriter = new FileWriter("tree.html");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String str = treeJsGenerator.genTree();
            bufferedWriter.write(str);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
