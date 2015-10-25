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
        ParserVal value = new ParserVal(s1.sval+"\n");
        return value;
    }
    public static ParserVal compound_statement3(ParserVal s1)
    {
        ParserVal value = new ParserVal(s1.sval+"\n");
        return value;
    }
    public static ParserVal compound_statement4(ParserVal s1,ParserVal s2)
    {
        ParserVal value = new ParserVal(s1.sval+"\n"+s2.sval+"\n");
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
        ParserVal value = new ParserVal(s1.sval+"\n"+"MOV\t"+s2.sval);
        return value;
    }

}
