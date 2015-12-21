package edu.eltech.moevm.assembler;

import edu.eltech.moevm.intermediate.IRCodeList;
import edu.eltech.moevm.intermediate.IRInstruction;

/**
 * Created by lazorg on 12/21/15.
 */
public class AsmCodeGenerator {

    public AsmCodeGenerator() {

    }

    public static boolean isNumeric(String str) {
        try {
            double d = Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public AsmCode generate(IRCodeList codeList) {
        AsmCode asmCode = new AsmCode();
        if (codeList != null) {
            for (int i = 0; i < codeList.size(); i++) {
                IRInstruction el = codeList.get(i);
                String result = "=empty=";
                switch (el.getOperation()) {
                    case MOV:
                        String first = el.getFirst().getValue();
                        String second = el.getSecond().getValue();

                        if (isNumeric(first)) {
                            result = "\txor rax,rax\n";
                            result += "\tmov rax," + first + "\n";
                            result += "\tmov [" + second + "],rax\n";
                        } else {
                            result = "\tmov " + second + "," + first;
                        }
                        asmCode.addCode(result);
                        break;
                    case INTEGER:
                        result = el.getFirst().getValue() + ":\tdd 0\n";
                        asmCode.addData(result);
                        break;
                    case PRINT:
                        result = "\tmov eax,[" + el.getFirst().getValue() + "]\n";
                        result += "\tcall print_num\n";
                        asmCode.addCode(result);
                        break;
                }

            }
        }
        return asmCode;
    }


}
