package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;

import java.io.IOException;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("BYACC/Java C compiler");

        try {
            Parser yyparser = new Parser(new FileReader("test_decl.c"));
            System.out.println("Parser:");
            yyparser.doParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
