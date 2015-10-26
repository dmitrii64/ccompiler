/* The following code was generated by JFlex 1.4.3 on 10/26/15 3:34 PM */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 2000 Gerwin Klein <lsf@jflex.de>                          *
 * All rights reserved.                                                    *
 *                                                                         *
 * Thanks to Larry Bell and Bob Jamison for suggestions and comments.      *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package edu.eltech.moevm.autogen;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 10/26/15 3:34 PM from the specification file
 * <tt>grammar.flex</tt>
 */
class Yylex {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\0\1\47\1\0\1\0\23\0\1\0\1\62\1\51\2\0"+
    "\1\56\1\57\1\45\1\70\1\71\1\15\1\55\1\66\1\5\1\50"+
    "\1\14\1\43\11\1\1\67\1\63\1\54\1\53\1\52\1\75\1\0"+
    "\4\3\1\4\1\7\5\2\1\11\10\2\1\13\2\2\1\44\2\2"+
    "\1\72\1\46\1\73\1\60\1\2\1\0\1\16\1\21\1\25\1\32"+
    "\1\23\1\6\1\35\1\27\1\31\1\2\1\24\1\10\1\33\1\30"+
    "\1\20\1\41\1\2\1\22\1\26\1\17\1\12\1\42\1\37\1\34"+
    "\1\40\1\36\1\64\1\61\1\65\1\74\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\4\3\1\5\1\6"+
    "\14\3\1\2\1\1\1\7\1\1\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\1\0\2\2\1\34\1\35\1\36\3\3\1\0"+
    "\1\3\1\37\1\40\16\3\1\41\1\3\1\42\4\3"+
    "\5\0\1\43\1\44\1\45\1\46\1\47\1\50\1\51"+
    "\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61"+
    "\1\2\1\0\1\2\1\3\1\62\24\3\1\63\6\3"+
    "\1\2\1\64\1\65\1\66\1\3\1\67\2\3\1\70"+
    "\4\3\1\71\1\72\1\3\1\73\2\3\1\74\10\3"+
    "\1\75\2\3\1\76\1\77\1\3\1\100\1\3\1\101"+
    "\4\3\1\102\2\3\1\103\5\3\1\104\3\3\1\105"+
    "\1\3\1\106\1\3\1\107\1\110\1\111\1\112\1\113"+
    "\1\114\3\3\1\115\2\3\1\116\1\3\1\117\1\120"+
    "\1\121\1\122";

  private static int [] zzUnpackAction() {
    int [] result = new int[209];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\76\0\174\0\272\0\370\0\u0136\0\u0174\0\u01b2"+
    "\0\u01f0\0\u022e\0\u026c\0\u02aa\0\u02e8\0\u0326\0\u0364\0\u03a2"+
    "\0\u03e0\0\u041e\0\u045c\0\u049a\0\u04d8\0\u0516\0\u0554\0\u0592"+
    "\0\u05d0\0\u060e\0\u064c\0\u068a\0\u06c8\0\u0706\0\u0744\0\u0782"+
    "\0\u07c0\0\u07fe\0\u083c\0\u087a\0\76\0\76\0\76\0\76"+
    "\0\76\0\76\0\76\0\76\0\76\0\76\0\76\0\u08b8"+
    "\0\u08f6\0\u0934\0\76\0\76\0\76\0\u0972\0\u09b0\0\u09ee"+
    "\0\u05d0\0\u0a2c\0\76\0\76\0\u0a6a\0\u0aa8\0\u0ae6\0\u0b24"+
    "\0\u0b62\0\u0ba0\0\u0bde\0\u0c1c\0\u0c5a\0\u0c98\0\u0cd6\0\u0d14"+
    "\0\u0d52\0\u0d90\0\272\0\u0dce\0\u0e0c\0\u0e4a\0\u0e88\0\u0ec6"+
    "\0\u0f04\0\u0f42\0\u0f80\0\u0fbe\0\u0ffc\0\u064c\0\u064c\0\u103a"+
    "\0\76\0\76\0\76\0\u1078\0\76\0\76\0\76\0\76"+
    "\0\76\0\76\0\76\0\76\0\76\0\u10b6\0\u10f4\0\76"+
    "\0\u1132\0\272\0\u1170\0\u11ae\0\u11ec\0\u122a\0\u1268\0\u12a6"+
    "\0\u12e4\0\u1322\0\u1360\0\u139e\0\u13dc\0\u141a\0\u1458\0\u1496"+
    "\0\u14d4\0\u1512\0\u1550\0\u158e\0\u15cc\0\u160a\0\272\0\u1648"+
    "\0\u1686\0\u16c4\0\u1702\0\u1740\0\u177e\0\u17bc\0\76\0\76"+
    "\0\76\0\u17fa\0\272\0\u1838\0\u1876\0\272\0\u18b4\0\u18f2"+
    "\0\u1930\0\u196e\0\272\0\272\0\u19ac\0\272\0\u19ea\0\u1a28"+
    "\0\272\0\u1a66\0\u1aa4\0\u1ae2\0\u1b20\0\u1b5e\0\u1b9c\0\u1bda"+
    "\0\u1c18\0\272\0\u1c56\0\u1c94\0\272\0\272\0\u1cd2\0\272"+
    "\0\u1d10\0\272\0\u1d4e\0\u1d8c\0\u1dca\0\u1e08\0\272\0\u1e46"+
    "\0\u1e84\0\272\0\u1ec2\0\u1f00\0\u1f3e\0\u1f7c\0\u1fba\0\272"+
    "\0\u1ff8\0\u2036\0\u2074\0\272\0\u20b2\0\272\0\u20f0\0\272"+
    "\0\272\0\272\0\272\0\272\0\272\0\u212e\0\u216c\0\u21aa"+
    "\0\272\0\u21e8\0\u2226\0\272\0\u2264\0\272\0\272\0\272"+
    "\0\272";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[209];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\3\4\1\5\1\6\1\4\1\7\1\10"+
    "\1\11\1\4\1\12\1\13\1\14\1\15\1\4\1\16"+
    "\1\17\1\20\1\4\1\21\1\22\2\4\1\23\1\24"+
    "\2\4\1\25\1\4\1\26\2\4\1\27\1\30\1\4"+
    "\1\31\2\2\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57"+
    "\77\0\1\3\2\0\1\60\3\0\4\61\7\0\1\60"+
    "\17\0\1\3\4\0\1\62\26\0\4\4\1\0\6\4"+
    "\2\0\27\4\36\0\1\63\44\0\1\64\1\65\23\0"+
    "\4\4\1\0\2\4\1\66\3\4\2\0\2\4\1\67"+
    "\24\4\32\0\4\4\1\0\6\4\2\0\2\4\1\70"+
    "\24\4\32\0\4\4\1\0\6\4\2\0\27\4\1\71"+
    "\31\0\4\4\1\0\6\4\2\0\12\4\1\72\14\4"+
    "\46\0\1\2\35\0\1\73\75\0\1\74\23\0\4\4"+
    "\1\0\4\4\1\75\1\4\2\0\27\4\32\0\4\4"+
    "\1\0\6\4\2\0\22\4\1\76\4\4\32\0\4\4"+
    "\1\0\6\4\2\0\4\4\1\77\22\4\32\0\4\4"+
    "\1\0\6\4\2\0\5\4\1\100\21\4\32\0\4\4"+
    "\1\0\2\4\1\101\3\4\2\0\12\4\1\102\3\4"+
    "\1\103\10\4\32\0\4\4\1\0\6\4\2\0\1\104"+
    "\1\4\1\105\6\4\1\106\15\4\32\0\4\4\1\0"+
    "\6\4\2\0\1\4\1\107\7\4\1\110\1\4\1\111"+
    "\5\4\1\112\5\4\32\0\4\4\1\0\1\113\5\4"+
    "\2\0\12\4\1\114\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\2\4\1\115\2\4\1\116\21\4\32\0\4\4"+
    "\1\0\6\4\2\0\2\4\1\117\24\4\32\0\4\4"+
    "\1\0\6\4\2\0\11\4\1\120\15\4\32\0\4\4"+
    "\1\0\6\4\2\0\2\4\1\121\24\4\32\0\1\3"+
    "\2\0\1\60\3\0\4\61\7\0\1\60\10\0\1\122"+
    "\6\0\1\3\1\122\3\0\1\62\25\0\45\123\1\0"+
    "\1\124\27\123\1\0\1\62\41\0\1\62\4\0\1\125"+
    "\25\0\47\126\1\0\1\126\1\127\24\126\52\0\1\130"+
    "\1\131\75\0\1\132\75\0\1\133\1\134\74\0\1\135"+
    "\1\0\1\136\73\0\1\137\75\0\1\140\3\0\1\141"+
    "\71\0\1\142\75\0\1\143\5\0\1\144\67\0\1\145"+
    "\23\0\1\146\3\0\1\147\35\0\1\146\11\0\1\147"+
    "\30\0\4\61\63\0\1\62\2\0\1\60\1\0\4\150"+
    "\11\0\1\60\17\0\1\62\33\0\4\4\1\0\6\4"+
    "\2\0\2\4\1\151\24\4\32\0\4\4\1\0\6\4"+
    "\2\0\4\4\1\152\22\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\153\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\10\4\1\154\2\4\1\155\13\4\32\0\4\4"+
    "\1\0\6\4\2\0\1\4\1\156\25\4\32\0\4\4"+
    "\1\0\6\4\2\0\23\4\1\157\3\4\32\0\4\4"+
    "\1\0\6\4\2\0\5\4\1\160\21\4\32\0\4\4"+
    "\1\0\6\4\2\0\1\4\1\161\15\4\1\162\7\4"+
    "\32\0\4\4\1\0\6\4\2\0\10\4\1\163\16\4"+
    "\32\0\4\4\1\0\4\4\1\164\1\4\2\0\27\4"+
    "\32\0\4\4\1\0\6\4\2\0\1\4\1\165\25\4"+
    "\32\0\4\4\1\0\6\4\2\0\10\4\1\166\16\4"+
    "\32\0\4\4\1\0\6\4\2\0\12\4\1\167\14\4"+
    "\32\0\4\4\1\0\6\4\2\0\1\170\26\4\32\0"+
    "\4\4\1\0\6\4\2\0\1\171\3\4\1\172\22\4"+
    "\32\0\4\4\1\0\6\4\2\0\2\4\1\173\24\4"+
    "\32\0\4\4\1\0\6\4\2\0\17\4\1\174\1\175"+
    "\6\4\32\0\4\4\1\0\6\4\2\0\13\4\1\176"+
    "\13\4\32\0\4\4\1\0\6\4\2\0\1\4\1\177"+
    "\25\4\32\0\4\4\1\0\4\4\1\200\1\4\2\0"+
    "\27\4\32\0\4\4\1\0\1\201\5\4\2\0\27\4"+
    "\32\0\4\4\1\0\6\4\2\0\1\4\1\202\25\4"+
    "\32\0\4\4\1\0\6\4\2\0\13\4\1\203\13\4"+
    "\32\0\4\4\1\0\2\4\1\204\3\4\2\0\13\4"+
    "\1\205\13\4\32\0\1\206\1\0\2\206\1\0\2\206"+
    "\6\0\1\206\2\0\1\206\1\0\1\206\1\0\1\206"+
    "\4\0\1\206\10\0\1\206\32\0\45\123\1\150\1\124"+
    "\76\123\1\0\26\123\50\0\1\207\100\0\1\210\75\0"+
    "\1\211\23\0\1\146\4\0\4\150\31\0\1\146\33\0"+
    "\1\146\41\0\1\146\33\0\4\4\1\0\6\4\2\0"+
    "\1\212\26\4\32\0\4\4\1\0\6\4\2\0\17\4"+
    "\1\213\7\4\32\0\4\4\1\0\6\4\2\0\13\4"+
    "\1\214\13\4\32\0\4\4\1\0\6\4\2\0\2\4"+
    "\1\215\24\4\32\0\4\4\1\0\6\4\2\0\2\4"+
    "\1\216\24\4\32\0\4\4\1\0\6\4\2\0\5\4"+
    "\1\217\21\4\32\0\4\4\1\0\6\4\2\0\1\220"+
    "\26\4\32\0\4\4\1\0\4\4\1\221\1\4\2\0"+
    "\27\4\32\0\4\4\1\0\6\4\2\0\13\4\1\222"+
    "\13\4\32\0\4\4\1\0\6\4\2\0\5\4\1\223"+
    "\21\4\32\0\4\4\1\0\6\4\2\0\15\4\1\224"+
    "\11\4\32\0\4\4\1\0\6\4\2\0\5\4\1\225"+
    "\21\4\32\0\4\4\1\0\6\4\2\0\5\4\1\226"+
    "\21\4\32\0\4\4\1\0\6\4\2\0\1\4\1\227"+
    "\6\4\1\230\16\4\32\0\4\4\1\0\6\4\2\0"+
    "\4\4\1\231\22\4\32\0\4\4\1\0\6\4\2\0"+
    "\1\4\1\232\25\4\32\0\4\4\1\0\4\4\1\233"+
    "\1\4\2\0\27\4\32\0\4\4\1\0\6\4\2\0"+
    "\4\4\1\234\22\4\32\0\4\4\1\0\6\4\2\0"+
    "\12\4\1\235\14\4\32\0\4\4\1\0\6\4\2\0"+
    "\5\4\1\236\21\4\32\0\4\4\1\0\6\4\2\0"+
    "\1\4\1\237\25\4\32\0\4\4\1\0\6\4\2\0"+
    "\3\4\1\240\23\4\32\0\4\4\1\0\6\4\2\0"+
    "\1\241\26\4\32\0\4\4\1\0\6\4\2\0\2\4"+
    "\1\242\24\4\32\0\4\4\1\0\2\4\1\243\3\4"+
    "\2\0\27\4\32\0\4\4\1\0\6\4\2\0\1\244"+
    "\26\4\32\0\4\4\1\0\6\4\2\0\14\4\1\245"+
    "\12\4\32\0\1\206\1\0\2\206\1\0\2\206\4\61"+
    "\2\0\1\206\2\0\1\206\1\0\1\206\1\0\1\206"+
    "\4\0\1\206\10\0\1\206\33\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\246\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\17\4\1\247\7\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\250\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\14\4\1\251\12\4\32\0\4\4\1\0\6\4"+
    "\2\0\6\4\1\252\20\4\32\0\4\4\1\0\6\4"+
    "\2\0\4\4\1\253\22\4\32\0\4\4\1\0\6\4"+
    "\2\0\10\4\1\254\16\4\32\0\4\4\1\0\6\4"+
    "\2\0\4\4\1\255\22\4\32\0\4\4\1\0\6\4"+
    "\2\0\13\4\1\256\13\4\32\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\257\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\13\4\1\260\13\4\32\0\4\4\1\0\6\4"+
    "\2\0\7\4\1\261\17\4\32\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\262\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\5\4\1\263\21\4\32\0\4\4\1\0\6\4"+
    "\2\0\2\4\1\264\24\4\32\0\4\4\1\0\6\4"+
    "\2\0\7\4\1\265\17\4\32\0\4\4\1\0\2\4"+
    "\1\266\3\4\2\0\27\4\32\0\4\4\1\0\4\4"+
    "\1\267\1\4\2\0\27\4\32\0\4\4\1\0\6\4"+
    "\2\0\5\4\1\270\21\4\32\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\271\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\272\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\5\4\1\273\21\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\274\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\275\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\276\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\12\4\1\277\14\4\32\0\4\4\1\0\6\4"+
    "\2\0\7\4\1\300\17\4\32\0\4\4\1\0\6\4"+
    "\2\0\1\4\1\301\25\4\32\0\4\4\1\0\6\4"+
    "\2\0\14\4\1\302\12\4\32\0\4\4\1\0\1\303"+
    "\5\4\2\0\27\4\32\0\4\4\1\0\6\4\2\0"+
    "\11\4\1\304\15\4\32\0\4\4\1\0\6\4\2\0"+
    "\5\4\1\305\21\4\32\0\4\4\1\0\2\4\1\306"+
    "\3\4\2\0\27\4\32\0\4\4\1\0\6\4\2\0"+
    "\13\4\1\307\13\4\32\0\4\4\1\0\6\4\2\0"+
    "\5\4\1\310\21\4\32\0\4\4\1\0\1\311\5\4"+
    "\2\0\27\4\32\0\4\4\1\0\6\4\2\0\5\4"+
    "\1\312\21\4\32\0\4\4\1\0\4\4\1\313\1\4"+
    "\2\0\27\4\32\0\4\4\1\0\6\4\2\0\1\4"+
    "\1\314\25\4\32\0\4\4\1\0\2\4\1\315\3\4"+
    "\2\0\27\4\32\0\4\4\1\0\6\4\2\0\14\4"+
    "\1\316\12\4\32\0\4\4\1\0\6\4\2\0\4\4"+
    "\1\317\22\4\32\0\4\4\1\0\6\4\2\0\5\4"+
    "\1\320\21\4\32\0\4\4\1\0\6\4\2\0\5\4"+
    "\1\321\21\4\31\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[8866];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\42\1\13\11\1\0\2\1\3\11\3\1"+
    "\1\0\1\1\2\11\25\1\5\0\2\1\3\11\1\1"+
    "\11\11\1\1\1\0\1\11\36\1\3\11\110\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[209];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 154) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 26: 
          { return Parser.TILDE;
          }
        case 83: break;
        case 18: 
          { return Parser.BRACELEFT;
          }
        case 84: break;
        case 5: 
          { return Parser.SLASH;
          }
        case 85: break;
        case 31: 
          { return Parser.DIV_ASSIGN;
          }
        case 86: break;
        case 37: 
          { return Parser.GE_OP;
          }
        case 87: break;
        case 44: 
          { return Parser.AND_ASSIGN;
          }
        case 88: break;
        case 10: 
          { return Parser.LESS;
          }
        case 89: break;
        case 47: 
          { return Parser.OR_ASSIGN;
          }
        case 90: break;
        case 13: 
          { return Parser.AMP;
          }
        case 91: break;
        case 45: 
          { return Parser.AND_OP;
          }
        case 92: break;
        case 27: 
          { return Parser.QUESTION;
          }
        case 93: break;
        case 4: 
          { return Parser.MINUS;
          }
        case 94: break;
        case 25: 
          { return Parser.BRACKETRIGHT;
          }
        case 95: break;
        case 15: 
          { return Parser.BAR;
          }
        case 96: break;
        case 49: 
          { return Parser.NE_OP;
          }
        case 97: break;
        case 36: 
          { return Parser.RIGHT_OP;
          }
        case 98: break;
        case 56: 
          { return Parser.AUTO;
          }
        case 99: break;
        case 62: 
          { return Parser.VOID;
          }
        case 100: break;
        case 32: 
          { return Parser.MUL_ASSIGN;
          }
        case 101: break;
        case 3: 
          { if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.IDENTIFIER;
          }
        case 102: break;
        case 82: 
          { return Parser.VOLATILE;
          }
        case 103: break;
        case 67: 
          { return Parser.SHORT;
          }
        case 104: break;
        case 46: 
          { return Parser.XOR_ASSIGN;
          }
        case 105: break;
        case 8: 
          { return Parser.GREATER;
          }
        case 106: break;
        case 79: 
          { return Parser.UNSIGNED;
          }
        case 107: break;
        case 73: 
          { return Parser.SIGNED;
          }
        case 108: break;
        case 12: 
          { return Parser.PERCENT;
          }
        case 109: break;
        case 9: 
          { return Parser.EQUAL;
          }
        case 110: break;
        case 64: 
          { return Parser.UNION;
          }
        case 111: break;
        case 68: 
          { return Parser.WHILE;
          }
        case 112: break;
        case 43: 
          { return Parser.MOD_ASSIGN;
          }
        case 113: break;
        case 66: 
          { return Parser.CONST;
          }
        case 114: break;
        case 71: 
          { return Parser.STATIC;
          }
        case 115: break;
        case 28: 
          { return Parser.DEC_OP;
          }
        case 116: break;
        case 17: 
          { return Parser.SEMICOLON;
          }
        case 117: break;
        case 21: 
          { return Parser.COLON;
          }
        case 118: break;
        case 65: 
          { return Parser.BREAK;
          }
        case 119: break;
        case 24: 
          { return Parser.BRACKETLEFT;
          }
        case 120: break;
        case 80: 
          { return Parser.REGISTER;
          }
        case 121: break;
        case 55: 
          { return Parser.LONG;
          }
        case 122: break;
        case 11: 
          { return Parser.PLUS;
          }
        case 123: break;
        case 7: 
          { return Parser.DOT;
          }
        case 124: break;
        case 70: 
          { return Parser.EXTERN;
          }
        case 125: break;
        case 77: 
          { return Parser.TYPEDEF;
          }
        case 126: break;
        case 38: 
          { return Parser.EQ_OP;
          }
        case 127: break;
        case 14: 
          { return Parser.CARET;
          }
        case 128: break;
        case 16: 
          { return Parser.EXCL;
          }
        case 129: break;
        case 58: 
          { return Parser.ENUM;
          }
        case 130: break;
        case 76: 
          { return Parser.DOUBLE;
          }
        case 131: break;
        case 60: 
          { return Parser.CHAR;
          }
        case 132: break;
        case 20: 
          { return Parser.COMMA;
          }
        case 133: break;
        case 72: 
          { return Parser.STRUCT;
          }
        case 134: break;
        case 69: 
          { return Parser.RETURN;
          }
        case 135: break;
        case 51: 
          { return Parser.INT;
          }
        case 136: break;
        case 22: 
          { return Parser.RBLEFT;
          }
        case 137: break;
        case 29: 
          { return Parser.PTR_OP;
          }
        case 138: break;
        case 34: 
          { return Parser.DO;
          }
        case 139: break;
        case 53: 
          { return Parser.RIGHT_ASSIGN;
          }
        case 140: break;
        case 75: 
          { return Parser.SWITCH;
          }
        case 141: break;
        case 35: 
          { return Parser.STRING_LITERAL;
          }
        case 142: break;
        case 78: 
          { return Parser.DEFAULT;
          }
        case 143: break;
        case 40: 
          { return Parser.LEFT_OP;
          }
        case 144: break;
        case 41: 
          { return Parser.ADD_ASSIGN;
          }
        case 145: break;
        case 42: 
          { return Parser.INC_OP;
          }
        case 146: break;
        case 2: 
          { if(yyparser!=null) yyparser.yylval = new ParserVal(yytext()); return Parser.CONSTANT;
          }
        case 147: break;
        case 50: 
          { return Parser.FOR;
          }
        case 148: break;
        case 74: 
          { return Parser.SIZEOF;
          }
        case 149: break;
        case 81: 
          { return Parser.CONTINUE;
          }
        case 150: break;
        case 57: 
          { return Parser.ELSE;
          }
        case 151: break;
        case 39: 
          { return Parser.LE_OP;
          }
        case 152: break;
        case 48: 
          { return Parser.OR_OP;
          }
        case 153: break;
        case 6: 
          { return Parser.STAR;
          }
        case 154: break;
        case 61: 
          { return Parser.GOTO;
          }
        case 155: break;
        case 23: 
          { return Parser.RBRIGHT;
          }
        case 156: break;
        case 59: 
          { return Parser.CASE;
          }
        case 157: break;
        case 52: 
          { return Parser.ELLIPSIS;
          }
        case 158: break;
        case 30: 
          { return Parser.SUB_ASSIGN;
          }
        case 159: break;
        case 33: 
          { return Parser.IF;
          }
        case 160: break;
        case 54: 
          { return Parser.LEFT_ASSIGN;
          }
        case 161: break;
        case 63: 
          { return Parser.FLOAT;
          }
        case 162: break;
        case 19: 
          { return Parser.BRACERIGHT;
          }
        case 163: break;
        case 1: 
          { 
          }
        case 164: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return 0; }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
