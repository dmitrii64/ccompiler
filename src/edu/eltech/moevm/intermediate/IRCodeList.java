package edu.eltech.moevm.intermediate;

import java.util.ArrayList;

/**
 * Created by lazorg on 12/10/15.
 */
public class IRCodeList {
    private ArrayList<IRInstruction> IRInstructions;

    public IRCodeList() {
        IRInstructions = new ArrayList<>();
    }

    public String print() {
        String str = "";
        for (IRInstruction inst : IRInstructions) {
            if (inst != null) {
                str += "[" + inst.getType().name() + "]" + inst + "\n";
            }
        }
        return str;
    }

    public void add(IRInstruction IRInstruction) {
        IRInstructions.add(IRInstruction);
    }

    public IRInstruction get(int i) {
        return IRInstructions.get(i);
    }

    public int size() {
        return IRInstructions.size();
    }

    public ArrayList<IRInstruction> getIRInstructions() {
        return IRInstructions;
    }

    public void addAll(IRCodeList code) {
        if (code != null)
            if (code.getIRInstructions() != null)
                this.IRInstructions.addAll(code.getIRInstructions());
    }
}
