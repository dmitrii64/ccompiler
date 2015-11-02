package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.syntax_tree.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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
            Node root;
            root = (Node) Parser.ParseFile(filename).obj;
            SyntaxTree tree = new SyntaxTree(root);
            //tree.infixVisit(new TreeOptimizer());
            tree.infixVisit(new TreeCallback() {
                @Override
                public void processElement(TreeElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof Leaf) {
                        System.out.print("leaf [" + ((Leaf) e).getOperand() + "]");
                        if (((Leaf) e).getValue() != null)
                            System.out.print(" (" + ((Leaf) e).getValue() + ")");
                        if (((Leaf) e).getType() != null)
                            System.out.print(" <" + ((Leaf) e).getType() + ">");
                    }
                    else {
                        System.out.print("node [" + ((Node) e).getOperation() + "]");
                        if (((Node) e).getValue() != null)
                            System.out.print(" (" + ((Node) e).getValue() + ")");
                        if (((Node) e).getType() != null)
                            System.out.print(" <" + ((Node) e).getType() + ">");
                    }
                    System.out.println();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
