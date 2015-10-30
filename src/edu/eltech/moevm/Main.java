package edu.eltech.moevm;


import edu.eltech.moevm.autogen.Parser;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        System.out.println("BYACC/Java C compiler");

        try {
            Parser.ParseFile("test.c");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
