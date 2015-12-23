package edu.eltech.moevm.intermediate;

/**
 * Created by lazorg on 12/10/15.
 */
public class IROperand {
    private String value;

    public IROperand(String val) {
        value = val;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
