package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.common.Type;

/**
 * Created by lazorg on 12/10/15.
 */
public class IRInstruction {
    private IROperation operation;
    private IROperand first;
    private IROperand second;
    private IROperand result;
    private Type type;
    private int operands;

    public IRInstruction(IROperation op, IROperand IROperand, Type irType) {
        operation = op;
        type = irType;
        first = IROperand;
        second = null;
        result = null;
        operands = 1;
    }

    public IRInstruction(IROperation op, IROperand IROperand1, IROperand IROperand2, Type irType) {
        operation = op;
        type = irType;
        first = IROperand1;
        second = null;
        result = IROperand2;

        operands = 2;
    }

    public IRInstruction(IROperation op, IROperand IROperand1, IROperand IROperand2, IROperand IROperand3, Type irType) {
        operation = op;
        type = irType;
        first = IROperand1;
        second = IROperand2;
        result = IROperand3;
        operands = 3;
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

    public IROperand getFirst() {
        return first;
    }

    public IROperand getSecond() {
        return second;
    }

    public IROperand getResult() {
        return result;
    }

    public void setOperation(IROperation operation) {
        this.operation = operation;
    }

    public void setFirst(IROperand first) {
        this.first = first;
    }

    public void setSecond(IROperand second) {
        this.second = second;
    }

    public void setResult(IROperand result) {
        this.result = result;
    }

    public int getOperands() {
        return operands;
    }

    public Type getType() {
        return type;
    }
}
