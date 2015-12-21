package edu.eltech.moevm.assembler;

import edu.eltech.moevm.intermediate.IRCodeList;

/**
 * Created by lazorg on 12/21/15.
 */
public class AsmCodeGenerator {

    AsmCodeGenerator() {

    }

    public AsmCode generate(IRCodeList codeList) {
        AsmCode asmCode = new AsmCode();
        if (codeList != null) {
            for (int i = 0; i < codeList.size(); i++) {

            }
        }
        return asmCode;
    }


}
