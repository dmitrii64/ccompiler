package edu.eltech.moevm.assembler;

import edu.eltech.moevm.intermediate.IRCodeList;
import edu.eltech.moevm.intermediate.IRInstruction;

/**
 * Created by lazorg on 12/21/15.
 */
public class AsmCodeGenerator {

    public AsmCodeGenerator() {

    }


    public static boolean isRegister(String str) {
        return str.charAt(0) == 'R';
    }

    public static boolean isConstant(String str) {
        try {
            double d = Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isVariable(String str) {
        return !isRegister(str) && !isConstant(str);
    }

    public static String getRegister(String size, String irregister) {
        if (irregister.charAt(0) == 'R') {
            String reg = "";
            String num = irregister.substring(1);
            int register = Integer.parseInt(num);
            switch (register) {
                case 0:
                    reg = size + "cx";
                    break;
                case 1:
                    reg = size + "dx";
                    break;

            }
            return reg;
        } else
            return irregister;

    }

    public AsmCode generate(IRCodeList codeList) {
        AsmCode asmCode = new AsmCode();
        if (codeList != null) {
            for (int i = 0; i < codeList.size(); i++) {
                IRInstruction el = codeList.get(i);
                String result = "=empty=";
                String IRfirst = null;
                if (el.getFirst() != null)
                    IRfirst = el.getFirst().getValue();
                String IRsecond = null;
                if (el.getSecond() != null)
                    IRsecond = el.getSecond().getValue();
                String IRresult = null;
                if (el.getResult() != null)
                    IRresult = el.getResult().getValue();

                String size = "e";

                switch (el.getOperation()) {
                    case MOV:

                        //second = getRegister(second);
                        //first = getRegister(first);


                        if (isConstant(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result = "\tmov " + size + "ax," + IRfirst + "\n";
                                result += "\tmov [" + IRresult + "]," + size + "ax\n";
                            }
                            if (isRegister(IRresult)) {
                                result = "\tmov " + getRegister(size, IRresult) + "," + IRfirst + "\n";
                            }
                        } else if (isRegister(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result = "\tmov [" + IRresult + "]," + getRegister(size, IRfirst) + "\n";
                            }
                            if (isRegister(IRresult)) {
                                result = "\tmov " + getRegister(size, IRresult) + "," + getRegister(size, IRfirst) + "\n";
                            }
                        } else if (isVariable(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result = "\tmov " + size + "ax,[" + IRfirst + "]\n";
                                result += "\tmov [" + IRresult + "]," + size + "ax\n";
                            }
                            if (isRegister(IRresult)) {
                                result = "\tmov " + getRegister(size, IRresult) + ",[" + IRfirst + "]\n";
                            }
                        }

                        asmCode.addCode(result);
                        break;
                    case ADD:
                        result = "";
                        if (isVariable(IRfirst)) {
                            result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
                        }
                        if (isConstant(IRfirst)) {
                            result += "\tmov " + size + "ax," + IRfirst + "\n";
                        }
                        if (isVariable(IRsecond)) {
                            result += "\tmov " + size + "bx,[" + IRsecond + "]\n";
                        }
                        if (isConstant(IRsecond)) {
                            result += "\tmov " + size + "bx," + IRsecond + "\n";
                        }
                        result += "\tadd " + size + "ax," + size + "bx\n";
                        if (isRegister(IRresult)) {
                            result += "\tmov " + getRegister(size, IRresult) + "," + size + "ax\n";
                        } else if (isVariable(IRresult)) {
                            result += "\tmov [" + IRresult + "]," + size + "ax\n";
                        }

                        asmCode.addCode(result);
                        break;
                    case MUL:

                        break;
                    case INTEGER:
                        result = el.getFirst().getValue() + ":\tdd 0\n";
                        asmCode.addData(result);
                        break;
                    case PRINT:
                        result = "\txor rax,rax\n";
                        result += "\tmov " + size + "ax,[" + el.getFirst().getValue() + "]\n";
                        result += "\tcall print_num\n";
                        asmCode.addCode(result);
                        break;
                }

            }
        }
        return asmCode;
    }


}
