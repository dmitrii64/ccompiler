package edu.eltech.moevm.assembler;

import edu.eltech.moevm.common.Type;
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
            Double.parseDouble(str);
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
                case 2:
                    reg = "r8";

            }
            return reg;
        } else
            return irregister;
    }

    public static String binaryOp(String op, String IRfirst, String IRsecond, String IRresult, Type type) {
        String result = "";
        String size = "e";
        if (type == Type.LONG)
            size = "r";
        if (isVariable(IRfirst)) {
            result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
        }
        if (isConstant(IRfirst)) {
            result += "\tmov " + size + "ax," + IRfirst + "\n";
        }
        if (isRegister(IRfirst)) {
            result += "\tmov " + size + "ax," + getRegister(size, IRfirst) + "\n";
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

    public static String floatOp(AsmCode code, String op, String IRfirst, String IRsecond, String IRresult, Type type) {
        String result = "";
        if (type == Type.FLOAT) {
            if (isConstant(IRfirst)) {
                int temp1 = code.getTempid();
                code.addData("const_" + temp1 + ": dd " + IRfirst + "\n");
                result += "\tmov eax,[" + "const_" + temp1 + "]\n";
                result += "\tmov [float_buff],eax\n";
                result += "\tfld dword[float_buff]" + "\n";
                if (isConstant(IRsecond)) {
                    int temp2 = code.getTempid();
                    code.addData("const_" + temp2 + ": dd " + IRsecond + "\n");
                    result += "\tmov eax,[" + "const_" + temp2 + "]\n";
                    result += "\tmov [float_buff],eax\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                } else if (isVariable(IRsecond)) {
                    result += "\tfld dword[" + IRsecond + "]" + "\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                } else if (isRegister(IRsecond)) {
                    result += "\tmov [float_buff]," + getRegister("e", IRsecond) + "\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                }

            } else if (isVariable(IRfirst)) {
                result += "\tfld dword[" + IRfirst + "]" + "\n";
                if (isConstant(IRsecond)) {
                    int temp2 = code.getTempid();
                    code.addData("const_" + temp2 + ": dd " + IRsecond + "\n");
                    result += "\tmov eax,[" + "const_" + temp2 + "]\n";
                    result += "\tmov [float_buff],eax\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                } else if (isVariable(IRsecond)) {
                    result += "\t" + op + " dword[" + IRsecond + "]" + "\n";
                } else if (isRegister(IRsecond)) {
                    result += "\tmov [float_buff]," + getRegister("e", IRsecond) + "\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                }
            } else if (isRegister(IRfirst)) {
                result += "\tmov [float_buff]," + getRegister("e", IRfirst) + "\n";
                result += "\tfld dword[float_buff]" + "\n";
                if (isConstant(IRsecond)) {
                    int temp2 = code.getTempid();
                    code.addData("const_" + temp2 + ": dd " + IRsecond + "\n");
                    result += "\tmov eax,[" + "const_" + temp2 + "]\n";
                    result += "\tmov [float_buff],eax\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                } else if (isVariable(IRsecond)) {
                    result += "\t" + op + " dword[" + IRsecond + "]" + "\n";
                } else if (isRegister(IRsecond)) {
                    result += "\tmov [float_buff]," + getRegister("e", IRsecond) + "\n";
                    result += "\t" + op + " dword[float_buff]" + "\n";
                }

            }

            if (isVariable(IRresult))
                result += "\tfst dword[" + IRresult + "]\n";
            else if (isRegister(IRresult)) {
                result += "\tfst dword[float_buff]\n";
                result += "\tmov " + getRegister("e", IRresult) + ",[float_buff]\n";
            }

        }


        return result;
    }

    public static String complexOp(AsmCode code, String op, String IRfirst, String IRsecond, String IRresult, Type type) {
        String result = "";
        if (isVariable(IRfirst)) {
            result += "\tmov eax,dword[" + IRfirst + "_re]\n";
        } else if (isRegister(IRfirst)) {
            result += "\tmov rax," + getRegister("r", IRfirst) + "\n";
            result += "\tshr rax,32\n";
        }
        result += "\tmov dword[float_buff],eax\n";
        result += "\tfld dword[float_buff]\n";
        if (isVariable(IRsecond)) {
            result += "\tmov eax,dword[" + IRsecond + "_re]\n";
        } else if (isRegister(IRsecond)) {
            result += "\tmov rax," + getRegister("r", IRsecond) + "\n";
            result += "\tshr rax,32\t";
        }
        result += "\tmov dword[float_buff],eax\n";
        result += "\t" + op + " dword[float_buff]\n";
        result += "\tfst dword[float_buff]\n";
        result += "\tmov eax,dword[float_buff]\n";
        if (isVariable(IRresult)) {
            result += "\tmov [" + IRresult + "_re],eax\n";
        } else if (isRegister(IRresult)) {
            result += "\tmov " + getRegister("e", IRresult) + ",eax\n";
            result += "\tshl " + getRegister("r", IRresult) + ",32\n";
        }

        if (isVariable(IRfirst)) {
            result += "\tmov eax,dword[" + IRfirst + "_im]\n";
        } else if (isRegister(IRfirst)) {
            result += "\tmov rax," + getRegister("r", IRfirst) + "\n";
        }
        result += "\tmov dword[float_buff],eax\n";
        result += "\tfld dword[float_buff]\n";
        if (isVariable(IRsecond)) {
            result += "\tmov eax,[" + IRsecond + "_im]\n";
        } else if (isRegister(IRsecond)) {
            result += "\tmov rax," + getRegister("r", IRsecond) + "\n";
        }
        result += "\tmov dword[float_buff],eax\n";
        result += "\t" + op + " dword[float_buff]\n";
        result += "\tfst dword[float_buff]\n";
        result += "\txor rax,rax\n";
        result += "\tmov eax,dword[float_buff]\n";
        if (isVariable(IRresult)) {
            result += "\tmov [" + IRresult + "_im],eax\n";
        } else if (isRegister(IRresult)) {
            result += "\tor " + getRegister("r", IRresult) + ",rax\n";
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
                    case COMPLEX:
                        size = "r";
                        break;
                }


                switch (el.getOperation()) {
                    case MOV:

                        //second = getRegister(second);
                        //first = getRegister(first);


                        if (isConstant(IRfirst)) {
                            if (el.getType() == Type.FLOAT) {
                                int temp = asmCode.getTempid();
                                asmCode.addData("const_" + temp + ": dd " + IRfirst + "\n");
                                if (isVariable(IRresult)) {
                                    result += "\tmov " + size + "ax," + "[" + "const_" + temp + "]" + "\n";
                                    result += "\tmov [" + IRresult + "]," + size + "ax\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov " + getRegister(size, IRresult) + "," + "[const_" + temp + "]\n";
                                }
                            } else if (el.getType() == Type.INT || el.getType() == Type.LONG) {
                                if (isVariable(IRresult)) {
                                    result += "\tmov " + size + "ax," + IRfirst + "\n";
                                    result += "\tmov [" + IRresult + "]," + size + "ax\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov " + getRegister(size, IRresult) + "," + IRfirst + "\n";
                                }
                            }
                        } else if (isRegister(IRfirst)) {
                            if (el.getType() == Type.INT || el.getType() == Type.LONG || el.getType() == Type.FLOAT) {
                                if (isVariable(IRresult)) {
                                    result += "\tmov [" + IRresult + "]," + getRegister(size, IRfirst) + "\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov " + getRegister(size, IRresult) + "," + getRegister(size, IRfirst) + "\n";
                                }
                            } else if (el.getType() == Type.COMPLEX) {
                                if (isVariable(IRresult)) {
                                    result += "\tmov rax," + getRegister("r", IRfirst) + "\n";
                                    result += "\tshr rax,32\n";
                                    result += "\tmov [" + IRresult + "_re],eax\n";
                                    result += "\tmov eax," + getRegister("e", IRfirst) + "\n";
                                    result += "\tmov [" + IRresult + "_im],eax\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov " + getRegister("r", IRresult) + "," + getRegister("r", IRfirst) + "\n";
                                }
                            }
                        } else if (isVariable(IRfirst)) {
                            if (el.getType() == Type.INT || el.getType() == Type.LONG || el.getType() == Type.FLOAT) {
                                if (isVariable(IRresult)) {
                                    result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
                                    result += "\tmov [" + IRresult + "]," + size + "ax\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov " + getRegister(size, IRresult) + ",[" + IRfirst + "]\n";
                                }
                            } else if (el.getType() == Type.COMPLEX) {
                                if (isVariable(IRresult)) {
                                    result += "\tmov eax,[" + IRfirst + "_re]\n";
                                    result += "\tmov [" + IRresult + "_re],eax\n";
                                    result += "\tmov eax,[" + IRfirst + "_im]\n";
                                    result += "\tmov [" + IRresult + "_im],eax\n";
                                } else if (isRegister(IRresult)) {
                                    result += "\tmov eax,[" + IRfirst + "_re]\n";
                                    result += "\tshl rax,32\n";
                                    result += "\tmov eax,[" + IRfirst + "_im]\n";
                                    result += "\tmov " + getRegister("r", IRresult) + ",rax\n";
                                }
                            }
                        }

                        asmCode.addCode(result);
                        break;
                    case ADD:
                        if (el.getType() == Type.INT || el.getType() == Type.LONG)
                            result = binaryOp("add", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.FLOAT)
                            result = floatOp(asmCode, "fadd", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.COMPLEX)
                            result = complexOp(asmCode, "fadd", IRfirst, IRsecond, IRresult, el.getType());

                        asmCode.addCode(result);
                        break;
                    case MUL:
                        if (el.getType() == Type.INT || el.getType() == Type.LONG)
                            result = binaryOp("imul", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.FLOAT)
                            result = floatOp(asmCode, "fmul", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.COMPLEX)
                            result = complexOp(asmCode, "fmul", IRfirst, IRsecond, IRresult, el.getType());
                        asmCode.addCode(result);
                        break;
                    case DIV:
                        if (el.getType() == Type.INT || el.getType() == Type.LONG)
                            result = binaryOp("idiv", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.FLOAT)
                            result = floatOp(asmCode, "fdiv", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.COMPLEX)
                            result = complexOp(asmCode, "fdiv", IRfirst, IRsecond, IRresult, el.getType());
                        asmCode.addCode(result);
                        break;
                    case CMP:
                        result = binaryOp("cmp", IRfirst, IRsecond, IRresult, el.getType());
                        asmCode.addCode(result);
                        break;
                    case SUB:
                        if (el.getType() == Type.INT || el.getType() == Type.LONG)
                            result = binaryOp("sub", IRsecond, IRfirst, IRresult, el.getType());
                        else if (el.getType() == Type.FLOAT)
                            result = floatOp(asmCode, "fsub", IRfirst, IRsecond, IRresult, el.getType());
                        else if (el.getType() == Type.COMPLEX)
                            result = complexOp(asmCode, "fsub", IRfirst, IRsecond, IRresult, el.getType());
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
                    case BML:
                        result = "\tjna " + IRfirst + "\n";
                        asmCode.addCode(result);
                        break;
                    case INC:
                        if (isRegister(IRfirst))
                            result = "\tinc " + getRegister(size, IRfirst) + "\n";
                        else
                            result = "\tinc dword[" + IRfirst + "]\n";
                        asmCode.addCode(result);
                        break;
                    case DEC:
                        if (isRegister(IRfirst))
                            result = "\tdec " + getRegister(size, IRfirst) + "\n";
                        else
                            result = "\tdec dword[" + IRfirst + "]\n";
                        asmCode.addCode(result);
                        break;
                    case RE:
                        if (isRegister(IRfirst)) {
                            result += "\tmov rax," + getRegister("r", IRfirst) + "\n";
                            result += "\tshr rax,32\n";
                        } else
                            result += "\tmov eax,[" + IRfirst + "_re]\n";
                        if (isRegister(IRresult))
                            result += "\tmov " + getRegister("e", IRresult) + ",eax\n";
                        else
                            result += "\tmov [" + IRresult + "],eax\n";
                        asmCode.addCode(result);
                        break;
                    case IM:
                        if (isRegister(IRfirst)) {
                            result += "\tmov rax," + getRegister("r", IRfirst) + "\n";
                        } else
                            result += "\tmov eax,[" + IRfirst + "_im]\n";
                        if (isRegister(IRresult))
                            result += "\tmov " + getRegister("e", IRresult) + ",eax\n";
                        else
                            result += "\tmov [" + IRresult + "],eax\n";
                        asmCode.addCode(result);
                        break;
                    case SRE:
                        if (isRegister(IRfirst)) {
                            result += "\tmov " + getRegister("e", IRfirst) + ",eax\n";
                        } else if (isVariable(IRfirst)) {
                            result += "\tmov [" + IRfirst + "],eax\n";
                        } else {
                            int id = asmCode.getTempid();
                            asmCode.addData("_const_" + id + ": dd " + IRfirst + "\n");
                            result += "\tmov eax,[" + "_const_" + id + "]\n";
                        }
                        if (isVariable(IRresult))
                            result += "\tmov [" + IRresult + "_re],eax\n";
                        asmCode.addCode(result);
                        break;
                    case SIM:
                        if (isRegister(IRfirst)) {
                            result += "\tmov " + getRegister("e", IRfirst) + ",eax\n";
                        } else if (isVariable(IRfirst)) {
                            result += "\tmov [" + IRfirst + "],eax\n";
                        } else {
                            int id = asmCode.getTempid();
                            asmCode.addData("_const_" + id + ": dd " + IRfirst + "\n");
                            result += "\tmov eax,[" + "_const_" + id + "]\n";
                        }
                        if (isVariable(IRresult))
                            result += "\tmov [" + IRresult + "_im],eax\n";
                        asmCode.addCode(result);
                        break;
                    case INTEGER:
                        result = IRfirst + ":\tdd " + IRresult + "\n";
                        asmCode.addData(result);
                        break;
                    case LONG:
                        result = IRfirst + ":\tdq " + IRresult + "\n";
                        asmCode.addData(result);
                        break;
                    case FLOAT:
                        result = IRfirst + ":\tdd " + IRresult + "\n";
                        asmCode.addData(result);
                        break;
                    case DOUBLE:
                        result = IRfirst + ":\tdq " + IRresult + "\n";
                        asmCode.addData(result);
                        break;
                    case COMPLEX:
                        result = IRfirst + "_re" + ":\tdd 0\n";
                        result += IRfirst + "_im" + ":\tdd 0\n";
                        asmCode.addData(result);
                        break;
                    case UMINUS:
                        if (el.getType() == Type.FLOAT) {
                            if (isVariable(IRfirst)) {
                                result = "\tfld dword[" + IRfirst + "]\n";
                                result += "\tfchs\n";
                                result += "\tfst dword[float_buff]\n";
                                result += "\tmov eax,dword[float_buff]\n";
                            } else if (isRegister(IRfirst)) {
                                result = "\tmov dword[float_buff]," + getRegister(size, IRresult) + "\n";
                                result = "\tfld [float_buff]\n";
                                result += "\tfchs\n";
                                result += "\tfst dword[float_buff]\n";
                                result += "\tmov eax,dword[float_buff]\n";
                            }

                            if (isRegister(IRresult))
                                result += "\tmov " + getRegister(size, IRresult) + ",eax\n";
                            else
                                result += "\tmov [" + IRresult + "],eax\n";
                        } else {
                            String size_prefix = "dword";
                            if (isVariable(IRfirst)) {
                                result = "\tneg " + size_prefix + "[" + IRfirst + "]\n";
                                result += "\tmov " + size + "ax," + "[" + IRfirst + "]\n";
                            } else if (isRegister(IRfirst)) {
                                result = "\tneg " + getRegister(size, IRfirst) + "\n";
                                result += "\tmov " + size + "ax," + getRegister(size, IRfirst) + "\n";
                            } else if (isConstant(IRfirst)) {
                                int id = asmCode.getTempid();
                                asmCode.addData("_buff_" + id + ": dd " + IRfirst + "\n");
                                result = "\tneg " + size_prefix + "[_buff_" + id + "]\n";
                                result += "\tmov " + size + "ax," + size_prefix + "[_buff_" + id + "]\n";
                            }
                            result += "\tmov " + getRegister(size, IRresult) + "," + size + "ax\n";
                        }
                        asmCode.addCode(result);
                        break;
                    case PRINT:
                        result = "\txor rax,rax\n";
                        if (isVariable(IRfirst)) {
                            if (el.getType() == Type.FLOAT) {
                                result += "\tfld dword[" + IRfirst + "]\n";
                                int id = asmCode.getTempid();
                                asmCode.addData("_buff_" + id + ": dd 0\n");
                                result += "\tfist dword[_buff_" + id + "]\n";
                                result += "\tmov eax,[_buff_" + id + "]\n";
                                result += "\tcall print_num\n";
                            } else {
                                result += "\tmov " + size + "ax,[" + IRfirst + "]\n";
                                result += "\tcall print_num\n";
                            }
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
