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
        System.out.println("======= Generated code =======");
        for (Instruction inst : instructions) {
            if (inst != null) {
                System.out.println(inst);
            }
        }
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

    public Instruction get(int i) {
        return instructions.get(i);
    }

    public int size() {
        return instructions.size();
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }
}
