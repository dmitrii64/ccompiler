package edu.eltech.moevm;


import edu.eltech.moevm.assembler.AsmCode;
import edu.eltech.moevm.assembler.AsmCodeGenerator;
import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.autogen.TokenNotFoundException;
import edu.eltech.moevm.common.Nonterminals;
import edu.eltech.moevm.intermediate.IRCodeGenerator;
import edu.eltech.moevm.intermediate.IRCodeList;
import edu.eltech.moevm.parsing_tree.*;
import edu.eltech.moevm.syntax_tree.*;

import java.io.*;


public class Main {

    public static void main(String[] args) {
        System.out.println("================Input file================");
        String filename = "tests/logical.c";
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
            //tree.infixVisit(new TreeRecursionOptimizer());
            tree.infixVisit(new PTCallback() {
                @Override
                public void processElement(PTElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof PTLeaf) {
                        try {
                            System.out.print("leaf [" + Parser.getTokenName(((PTLeaf) e).getToken()) + "]");
                        } catch (TokenNotFoundException e1) {
                            System.out.println("Print error!");
                            e1.printStackTrace();
                        }
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


            // OPTIMIZATION PARSING TREE START
            tree.infixVisit(new TreeRecursionOptimizer());
            tree.infixVisit(new TreeOneChildOptimizer());
            tree.infixVisit(new TreeSkipNodeOptimizer(Nonterminals.DIRECT_DECLARATOR, Nonterminals.PARAMETER_LIST));
            tree.infixVisit(new TreeParenthesesOptimizer());
            // OPTIMIZATION PARSING TREE END


            System.out.println("================Parsing Tree================");

            tree.infixVisit(new PTCallback() {
                @Override
                public void processElement(PTElement e, int level) {
                    System.out.print("|");
                    for (int i = 0; i < level; i++) {
                        System.out.print("--");
                    }
                    if (e instanceof PTLeaf) {
                        try {
                            System.out.print("leaf [" + Parser.getTokenName(((PTLeaf) e).getToken()) + "]");
                        } catch (TokenNotFoundException e1) {
                            System.out.println("Print error!");
                            e1.printStackTrace();
                        }
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
            FileWriter fileWriter = new FileWriter("tree_output/parsing_tree.html");
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

//            syntaxTree.postfixVisit(new TreeCallback() {
//                @Override
//                public void processElement(TreeElement e, int level) {
//                    ReduceObserver.getInstance().generate(e);
//                }
//            });
//
//            ReduceObserver.getInstance().printCode();

            //Reset ids
            syntaxTree.infixVisit(new TreeCallback() {
                int id = 0;

                @Override
                public void processElement(TreeElement e, int level) {
                    e.setId(id);
                    id++;
                }
            });




            System.out.println("=============== Verify name scopes ==============");
            syntaxTree.verifyNameScopes();

            syntaxTree.postfixVisit(new TreeCallback() {
                @Override
                public void processElement(TreeElement e, int level) {
                    if (e instanceof Node) {
                        Node node = (Node) e;
                        switch (node.getOperation()) {
                            case EQUAL:
                            case STAR:
                            case PLUS:
                            case MINUS:
                            case DIVIDE:
                                TreeElement left = node.getElements().get(0);
                                TreeElement right = node.getElements().get(1);
                                if (left.getType() == right.getType())
                                    node.setType(left.getType());
                                else
                                    System.out.println("Type mismatch at " + node.getOperation().name());
                                break;
                        }
                    }
                }
            });

            System.out.println("============== Printing tree to file ============");
            SyntaxTreeJsGenerator syntaxTreeJsGenerator = new SyntaxTreeJsGenerator();
            syntaxTree.infixVisit(syntaxTreeJsGenerator);
            FileWriter stfileWriter = new FileWriter("tree_output/syntax_tree.html");
            BufferedWriter stbufferedWriter = new BufferedWriter(stfileWriter);
            String ststr = syntaxTreeJsGenerator.genTree();
            stbufferedWriter.write(ststr);
            stbufferedWriter.close();
            stfileWriter.close();

            System.out.println("================ Generated IR code ==============");

            IRCodeGenerator generator = new IRCodeGenerator();
            IRCodeList IRCodeList = generator.generate(syntaxTree);
            IRCodeList.print();

            System.out.println("=========== Generated Assembler code ============");
            AsmCodeGenerator asmCodeGenerator = new AsmCodeGenerator();
            AsmCode asmCode = asmCodeGenerator.generate(IRCodeList);
            String asmCodeToFile = asmCode.print();
            System.out.println(asmCodeToFile);

            FileWriter fileWriter1 = new FileWriter("asm/input.asm");
            fileWriter1.write(asmCodeToFile);
            fileWriter1.close();






        } catch (IOException e) {
            e.printStackTrace();
        } catch (IdentifierDefinedException e) {
            e.printStackTrace();
        } catch (UnexpectedChildCountException e) {
            e.printStackTrace();
        } catch (UnexpectedNodeException e) {
            e.printStackTrace();
        } catch (IdentifierNotDefinedException e) {
            e.printStackTrace();
        } catch (UnusedIdentifierException e) {
            e.printStackTrace();
        } catch (DuplicateArgumentException e) {
            e.printStackTrace();
        }
    }
}
