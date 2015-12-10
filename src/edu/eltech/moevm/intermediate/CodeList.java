package edu.eltech.moevm.intermediate;

import java.util.ArrayList;

/**
 * Created by lazorg on 12/10/15.
 */
public class CodeList {
    private ArrayList<Instruction> instructions;

    public CodeList() {
        instructions = new ArrayList<>();

    }

    public void print() {
        for (Instruction inst : instructions) {
            inst.print();
            System.out.println();
        }
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

}
