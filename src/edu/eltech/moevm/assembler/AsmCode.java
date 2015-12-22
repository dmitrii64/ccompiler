package edu.eltech.moevm.assembler;

import java.util.ArrayList;

/**
 * Created by lazorg on 12/21/15.
 */
public class AsmCode {
    private ArrayList<String> header;
    private ArrayList<String> data;
    private ArrayList<String> bss;
    private ArrayList<String> code;
    private ArrayList<String> footer;

    public AsmCode() {
        header = new ArrayList<>();
        data = new ArrayList<>();
        bss = new ArrayList<>();
        code = new ArrayList<>();
        footer = new ArrayList<>();

        header.add("global _start\n\n");

        data.add("section .data\n");

        bss.add("section .bss\n" +
                "\tnumbuf resb 10\n");

        code.add("section .text\n");

        code.add("itoa:\n" +
                "\tenter 4,0\n" +
                "\tlea r8,[numbuf+10]\n" +
                "\tmov rcx,10\n" +
                "\tmov [rbp-4],dword 0\n" +
                "\n" +
                "\t.divloop:\n" +
                "\txor rdx,rdx\n" +
                "\tidiv rcx\n" +
                "\tadd rdx,0x30\n" +
                "\tdec r8\n" +
                "\tmov byte [r8],dl\n" +
                "\tinc dword [rbp-4]\n" +
                "\n" +
                "\tcmp rax,0\n" +
                "\tjnz .divloop\n" +
                "\n" +
                "\tmov rax,r8\n" +
                "\tmov rcx,[rbp-4]\n" +
                "\n" +
                "\tleave\n" +
                "\tret\n" +
                "\n" +
                "clean_buf:\n" +
                "\tlea r8,[numbuf+10]\n" +
                "\tmov rcx,10\n" +
                "\t.clear_loop:\n" +
                "\tmov byte[r8],0\n" +
                "\tdec r8\n" +
                "\tdec rcx\n" +
                "\tcmp rcx,0\n" +
                "\tjnz .clear_loop\n" +
                "\txor rcx,rcx\n" +
                "\txor r8,r8\n" +
                "\tret\n" +
                "\n" +
                "print_num:\n" +
                "\tcall clean_buf\n" +
                "\tcall itoa\n" +
                "\tmov eax, 4\n" +
                "    mov ebx, 1\n" +
                "    mov ecx, numbuf\n" +
                "    mov edx, 10\n" +
                "    int 0x80\n" +
                "    ret\n" +
                "\n" +
                "print_str:\n" +
                "\tmov eax, 4\n" +
                "    mov ebx, 1\n" +
                "    int 0x80\n" +
                "    ret\n" +
                "\n");

        code.add("_start:\n");
        //code.add("\txor rax,rax\n");
        //code.add("\txor rbx,rbx\n");
        //code.add("\txor rcx,rcx\n");
        //code.add("\txor rdx,rdx\n");

        footer.add("\n" +
                "\tmov\teax, 1 ; exit\n" +
                "\tmov\tebx, 0\n" +
                "\tint\t0x80   ; exit(0)");
    }


    public void addCode(String line) {
        code.add(line);
    }

    public void addData(String line) {
        data.add(line);
    }

    public void addBss(String line) {
        bss.add(line);
    }

    public String print() {
        String codeText = new String();
        for (String str : header) {
            codeText += str;
        }
        for (String str : data) {
            codeText += str;
        }
        for (String str : bss) {
            codeText += str;
        }
        for (String str : code) {
            codeText += str;
        }
        for (String str : footer) {
            codeText += str;
        }

        return codeText;
    }

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }
}
