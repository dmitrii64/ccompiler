package edu.eltech.moevm.intermediate;

import edu.eltech.moevm.syntax_tree.SyntaxTree;

/**
 * Created by lazorg on 12/10/15.
 */
public class CodeGenerator {
    public CodeGenerator() {

    }

    public CodeList Generate(SyntaxTree tree) {
        CodeList code = new CodeList();

        code.add(new Instruction(IROperation.MOV, new Adress("addr1"), new Adress("addr2")));
        code.add(new Instruction(IROperation.ADD, new Adress("addr1"), new Adress("addr2")));
        code.add(new Instruction(IROperation.DIV, new Adress("addr1"), new Adress("addr2")));
        code.add(new Instruction(IROperation.MOV, new Adress("addr1"), new Adress("addr2"), new Adress("addr3")));
        code.add(new Instruction(IROperation.ADD, new Adress("addr1"), new Adress("addr2"), new Adress("addr2")));
        code.add(new Instruction(IROperation.DIV, new Adress("addr1"), new Adress("addr2"), new Adress("addr2")));

        return code;
    }

}
