package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public class Instruction {
    private IROperation operation;
    private Address first;
    private Address second;
    private Address result;

    public Instruction(IROperation op, Address address) {
        operation = op;
        first = address;
        second = null;
        result = null;
    }

    public Instruction(IROperation op, Address address1, Address address2) {
        operation = op;
        first = address1;
        second = address2;
        result = null;
    }

    public Instruction(IROperation op, Address address1, Address address2, Address address3) {
        operation = op;
        first = address1;
        second = address2;
        result = address3;
    }

    @Override
    public String toString() {
        String str = "";
        str += operation.name() + "\t";
        if (first != null)
            str += first.getValue() + " ";
        if (second != null)
            str += second.getValue() + " ";
        if (result != null)
            str += result.getValue();
        return str;
    }

    public IROperation getOperation() {
        return operation;
    }

    public Address getFirst() {
        return first;
    }

    public Address getSecond() {
        return second;
    }

    public Address getResult() {
        return result;
    }

    public void setOperation(IROperation operation) {
        this.operation = operation;
    }

    public void setFirst(Address first) {
        this.first = first;
    }

    public void setSecond(Address second) {
        this.second = second;
    }

    public void setResult(Address result) {
        this.result = result;
    }
}
