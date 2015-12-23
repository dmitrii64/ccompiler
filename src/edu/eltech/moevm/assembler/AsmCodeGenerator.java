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
            Integer.parseInt(str);
        } catch (Exception e) {
            if (str.contains("\""))
                return true;
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

    public static String binaryOp(String op, String IRfirst, String IRsecond, String IRresult, String size) {
        String result = "";
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
        if (isRegister(IRsecond)) {
            result += "\tmov " + size + "bx," + getRegister(size, IRsecond) + "\n";
        }
        result += "\t" + op + " " + size + "ax," + size + "bx\n";
        if (isRegister(IRresult)) {
            result += "\tmov " + getRegister(size, IRresult) + "," + size + "ax\n";
        } else if (isVariable(IRresult)) {
            result += "\tmov [" + IRresult + "]," + size + "ax\n";
        }
        return result;
    }

    public AsmCode generate(IRCodeList codeList) {
        AsmCode asmCode = new AsmCode();
        if (codeList != null) {
            for (int i = 0; i < codeList.size(); i++) {
                IRInstruction el = codeList.get(i);
                String result = "";
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
                switch (el.getType()) {
                    case INT:
                    case FLOAT:
                        size = "e";
                        break;
                    case LONG:
                    case DOUBLE:
                        size = "r";
                        break;
                }


                switch (el.getOperation()) {
                    case MOV:

                        //second = getRegister(second);
                        //first = getRegister(first);


                        if (isConstant(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result += "\tmov " + size + "ax," + IRfirst + "\n";
                                result += "\tmov [" + IRresult + "]," + size + "ax\n";
                            }
                            if (isRegister(IRresult)) {
                                result += "\tmov " + getRegister(size, IRresult) + "," + IRfirst + "\n";
                            }
                        } else if (isRegister(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result += "\tmov [" + IRresult + "]," + getRegister(size, IRfirst) + "\n";
                            }
                            if (isRegister(IRresult)) {
                                result += "\tmov " + getRegister(size, IRresult) + "," + getRegister(size, IRfirst) + "\n";
                            }
                        } else if (isVariable(IRfirst)) {
                            if (isVariable(IRresult)) {
                                result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
                                result += "\tmov [" + IRresult + "]," + size + "ax\n";
                            }
                            if (isRegister(IRresult)) {
                                result += "\tmov " + getRegister(size, IRresult) + ",[" + IRfirst + "]\n";
                            }
                        }

                        asmCode.addCode(result);
                        break;
                    case ADD:
                        result = binaryOp("add", IRfirst, IRsecond, IRresult, size);
                        asmCode.addCode(result);
                        break;
                    case MUL:
                        result = binaryOp("imul", IRfirst, IRsecond, IRresult, size);
                        asmCode.addCode(result);
                        break;
                    case CMP:
                        result = binaryOp("cmp", IRfirst, IRsecond, IRresult, size);
                        asmCode.addCode(result);
                        break;
                    case SUB:
                        result = binaryOp("sub", IRsecond, IRfirst, IRresult, size);
                        asmCode.addCode(result);
                        break;
                    case DEFL:
                        result = IRfirst + ":";
                        asmCode.addCode(result);
                        break;
                    case BRL:
                        result = "\tjmp " + IRfirst + "\n";
                        asmCode.addCode(result);
                        break;
                    case BNZ:
                        result = "\tjnz " + IRfirst + "\n";
                        asmCode.addCode(result);
                        break;
                    case INTEGER:
                        result = IRfirst + ":\tdd 0\n";
                        asmCode.addData(result);
                        break;
                    case LONG:
                        result = IRfirst + ":\tdd 0,0\n";
                        asmCode.addData(result);
                        break;
                    case FLOAT:
                        result = IRfirst + ":\tdd 0\n";
                        asmCode.addData(result);
                        break;
                    case DOUBLE:
                        result = IRfirst + ":\tdd 0,0\n";
                        asmCode.addData(result);
                        break;

                    case PRINT:
                        result = "\txor rax,rax\n";
                        if (isVariable(IRfirst)) {
                            result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
                            result += "\tcall print_num\n";
                        } else if (isConstant(IRfirst)) {
                            int stringid = asmCode.getStringid();
                            asmCode.addData("temp" + stringid + ": db " + IRfirst + "\n");
                            asmCode.addData(".len: equ\t$ - temp" + stringid + "\n");
                            result += "\tmov ecx, temp" + stringid + "\n" +
                                    "\tmov edx, temp" + stringid + ".len\n";
                            result += "\tcall print_str\n";


                        }


                        asmCode.addCode(result);
                        break;
                }

            }
        }
        return asmCode;
    }


}
