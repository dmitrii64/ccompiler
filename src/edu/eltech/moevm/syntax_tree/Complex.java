package edu.eltech.moevm.syntax_tree;

/**
 * Created by vladimir on 20.12.15.
 */
public class Complex {
    private String re;
    private String im;

    public static Complex parse(String str) {
        int pos = 0;
        str = str.replaceAll(" ", "");
        String re = "";
        String im = "";
        if (str.charAt(0) == '-') {
            re += '-';
            pos++;
        }
        try {
            while (Character.isDigit(str.charAt(pos)) || str.charAt(pos) == '.') {
                re += str.charAt(pos);
                pos++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return new Complex(re);
        }
        if (str.charAt(pos) == 'i') {
            im = re;
            return new Complex(null, im);
        }
        if (str.charAt(pos) == '-') {
            im += '-';
        }
        pos++;
        while (Character.isDigit(str.charAt(pos)) || str.charAt(pos) == '.') {
            im += str.charAt(pos);
            pos++;
        }
        return new Complex(re, im);
    }

    public Complex(String re, String im) {
        this.re = re;
        this.im = im;
    }

    public Complex(String re) {
        this.re = re;
    }

    public String getRe() {
        return re;
    }

    public String getIm() {
        return im;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public void setIm(String im) {
        this.im = im;
    }
}
