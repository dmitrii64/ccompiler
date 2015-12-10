package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public class Instruction {
    private IROperation operation;
    private Adress first;
    private Adress second;
    private Adress result;

    public Instruction(IROperation op, Adress adress) {
        operation = op;
        first = adress;
        second = null;
        result = null;
    }

    public Instruction(IROperation op, Adress adress1, Adress adress2) {
        operation = op;
        first = adress1;
        second = adress2;
        result = null;
    }

    public Instruction(IROperation op, Adress adress1, Adress adress2, Adress adress3) {
        operation = op;
        first = adress1;
        second = adress2;
        result = adress3;
    }

    public void print() {
        System.out.print(operation.name() + "\t");
        if (first != null)
            System.out.print(first.getValue() + " ");
        if (second != null)
            System.out.print(second.getValue() + " ");
        if (result != null)
            System.out.print(result.getValue());
    }
}
