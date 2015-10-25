package edu.eltech.moevm;

import edu.eltech.moevm.autogen.ParserVal;

/**
 * Created by lazorg on 10/25/15.
 */

public class Translator {
    public Translator()
    {

    }

    public static ParserVal translation_unit(ParserVal s1)
    {
        return s1;
    }

    public static ParserVal external_declaration(ParserVal s1)
    {
        return s1;
    }

    public static ParserVal function_definition1(ParserVal s1,ParserVal s2,ParserVal s3,ParserVal s4)
    {
        ParserVal value = new ParserVal(s1.sval+s2.sval+s3.sval+s4.sval);
        return value;
    }
    public static ParserVal function_definition2(ParserVal s1,ParserVal s2,ParserVal s3)
    {
        String funcname = s2.sval;
        String result;
        result =  "LABEL\t"+funcname+"\n";
        result += "GOTO\t"+funcname+"_START\n";
        result += "LABEL\t"+funcname+"_START\n";
        result += s3.sval;
        result += "RETURN\n";

        ParserVal value = new ParserVal(result);
        return value;
    }
    public static ParserVal function_definition3(ParserVal s1,ParserVal s2,ParserVal s3)
    {
        ParserVal value = new ParserVal(s1.sval+s2.sval+s3.sval);
        return value;
    }
    public static ParserVal function_definition4(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+s2.sval);
        return value;
    }
    public static ParserVal compound_statement1()
    {
        ParserVal value = new ParserVal("\n");
        return value;
    }
    public static ParserVal compound_statement2(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal compound_statement3(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal compound_statement4(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\n"+s2.sval);
        return value;
    }
    public static ParserVal declaration_list1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval+"\n");
        return value;
    }
    public static ParserVal declaration_list2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+s2.sval+"\n");
        return value;
    }
    public static ParserVal statement_list1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval+"\n");
        return value;
    }
    public static ParserVal statement_list2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+s2.sval+"\n");
        return value;
    }

    public static ParserVal declaration1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal declaration2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\t"+s2.sval);
        return value;
    }
    public static ParserVal declarator1(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\t"+s2.sval);
        return value;
    }
    public static ParserVal declarator2(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }

    public static ParserVal initializer_list1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal initializer_list2(ParserVal s1, ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval + "\n" + s2.sval);
        return value;
    }
    public static ParserVal initializer1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal initializer2(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal initializer3(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }

    public static ParserVal identifier_list1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal identifier_list2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\n"+s2.sval);
        return value;
    }

    public static ParserVal init_declarator_list1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal init_declarator_list2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\n"+s2.sval);
        return value;
    }

    public static ParserVal init_declarator1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal init_declarator2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\n"+"MOV\t"+s2.sval+",R1");
        return value;
    }
    public static ParserVal jump_statement1(ParserVal s1)
    {
        ParserVal value = new ParserVal("GOTO\t"+s1.sval+"\n");
        return value;
    }
    public static ParserVal jump_statement2()
    {
        ParserVal value = new ParserVal("CONTINUE\n");
        return value;
    }
    public static ParserVal jump_statement3()
    {
        ParserVal value = new ParserVal("BREAK\n");
        return value;
    }
    public static ParserVal jump_statement4()
    {
        ParserVal value = new ParserVal("RETURN\n");
        return value;
    }
    public static ParserVal jump_statement5(ParserVal s1)
    {
        ParserVal value = new ParserVal("PUSH\t"+s1.sval+"\n");
        return value;
    }
    public static ParserVal assignment_expression2(ParserVal s1,ParserVal s2,ParserVal s3)
    {
        ParserVal value = new ParserVal(s3.sval+"\nMOV\tR1,EMPTY,"+s1.sval);
        return value;
    }
    public static ParserVal conditional_expression2(ParserVal s1,ParserVal s2,ParserVal s3)
    {
        ParserVal value = new ParserVal("<----QUESTION---->\n");
        return value;
    }
    public static ParserVal logical_or_expression2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal("OR\t"+s1.sval+","+s2.sval+",R1");
        return value;
    }
    public static ParserVal logical_and_expression2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal("AND\t" + s1.sval + "," + s2.sval+",R1");
        return value;
    }
    public static ParserVal multiplicative_expression2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal("MUL\t"+s1.sval+","+s2.sval+",R1");
        return value;
    }
    public static ParserVal unary_expression1(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal unary_expression2(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal unary_expression3(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal unary_expression4(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal("\t"+s1.sval+","+s2.sval);
        return value;
    }
    public static ParserVal unary_expression5(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal unary_expression6(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval);
        return value;
    }
    public static ParserVal postfix_expression2(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s2.sval+"PUSH\tR1"+"CALL\t"+s1.sval);
        return value;
    }
    public static ParserVal postfix_expression3(ParserVal s1)
    {
        ParserVal value = new ParserVal("CALL\t"+s1.sval);
        return value;
    }
    public static ParserVal postfix_expression4(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal("PUSH\t"+s2.sval+"\n"+"CALL\t"+s1.sval);
        return value;
    }



}
