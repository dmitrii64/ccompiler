package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.autogen.ParserVal;
import edu.eltech.moevm.syntax_tree.Leaf;
import edu.eltech.moevm.syntax_tree.Node;
import edu.eltech.moevm.syntax_tree.Operation;
import edu.eltech.moevm.syntax_tree.SyntaxTree;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        System.out.println("BYACC/Java C compiler");



        try {
            Node root;
            root = (Node) Parser.ParseFile("test.c").obj;
            SyntaxTree tree = new SyntaxTree(root);
            tree.visit();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        Leaf a = new Leaf("a");
//        Leaf b = new Leaf("b");
//        Leaf c = new Leaf("c");
//        Node uminus = new Node(Operation.UMINUS, new ParserVal(c));
//        Node mul = new Node(Operation.MULTIPLY, new ParserVal(b), new ParserVal(uminus));
//        Node plus = new Node(Operation.PLUS, new ParserVal(a), new ParserVal(mul));

//        new SyntaxTree(plus).visit();
    }
}
