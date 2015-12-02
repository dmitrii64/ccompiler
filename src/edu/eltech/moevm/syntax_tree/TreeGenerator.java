package edu.eltech.moevm.syntax_tree;

import edu.eltech.moevm.autogen.Parser;
import edu.eltech.moevm.parsing_tree.*;

import java.util.Iterator;

/**
 * Created by lazorg on 11/4/15.
 */
public class TreeGenerator {


    public SyntaxTree generate(ParsingTree parsingTree) {
        PTNode root = (PTNode) parsingTree.getRoot();
        SyntaxTree syntaxTree = null;
        try {
            syntaxTree = new SyntaxTree(visitNode(root));
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return syntaxTree;
    }

    private boolean operationExist(String operation) {
        for (Operation op : Operation.values())
            if (op.name().compareTo(operation) == 0)
                return true;
        return false;
    }

    private void setBinaryExpr(Node output, PTNode input) throws UnsupportedOperationException {
        PTElement first = input.getElements().get(0);
        PTElement second = input.getElements().get(2);
        if ((first instanceof PTLeaf) && (second instanceof PTLeaf)) {
            Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())), ((PTLeaf) first).getValue(), ((PTLeaf) first).getLine());
            output.add(leaf);
            Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue(), ((PTLeaf) second).getLine());
            output.add(leaf2);
        } else if ((first instanceof PTLeaf) && !(second instanceof PTLeaf)) {
            Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) first).getToken())), ((PTLeaf) first).getValue(), ((PTLeaf) first).getLine());
            output.add(leaf);
        } else if (!(first instanceof PTLeaf) && (second instanceof PTLeaf)) {
            Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) second).getToken())), ((PTLeaf) second).getValue(), ((PTLeaf) second).getLine());
            output.addleft(leaf);
        }
        PTElement op = input.getElements().get(1);
        output.setOperation(Operation.valueOf(Parser.getTokenName(((PTLeaf) op).getToken())));
    }

    private Node visitNode(PTNode node) throws UnsupportedOperationException {
        Node result;
        String name = node.getNonterminal().name();
        Nonterminals nt = node.getNonterminal();

        if (operationExist(name)) {
            Operation op = Operation.valueOf(name);
            result = new Node(Operation.valueOf(name));

            switch (op) {
                case FUNCTION_DEFINITION:
                    // Setting function return type
                    PTLeaf type = (PTLeaf) node.getElements().get(0);
                    result.setType(edu.eltech.moevm.common.Type.valueOf(Parser.getTokenName(type.getToken())));
                    // Setting function name
                    PTLeaf fname = (PTLeaf) node.getElements().get(1);
                    result.setValue(fname.getValue());
                    break;
                case ITERATION_STATEMENT:
                    // Setting cycle type
                    PTLeaf iterationType = (PTLeaf) node.getElements().get(0);
                    result.setValue(Parser.getTokenName(iterationType.getToken()));
                    if (Parser.getTokenName(iterationType.getToken()).compareTo("WHILE") == 0) {
                        PTElement cond = node.getElements().get(2);
                        if (cond instanceof PTLeaf) {
                            Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) cond).getToken())), ((PTLeaf) cond).getValue(), ((PTLeaf) cond).getLine());
                            result.add(leaf2);
                        }
                    }
                    break;
                case SELECTION_STATEMENT:
                    // "if" statement handling
                    PTElement conditionNode = node.getElements().get(2);
                    if (conditionNode instanceof PTLeaf) {
                        Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) conditionNode).getToken())), ((PTLeaf) conditionNode).getValue(), ((PTLeaf) conditionNode).getLine());
                        result.add(leaf2);
                    }
                    break;
                case PARAMETER_DECLARATION:
                    // Creating leaf for parameter identifier with type
                    PTLeaf parameterType = (PTLeaf) node.getElements().get(0);
                    PTLeaf parameterName = (PTLeaf) node.getElements().get(1);
                    Leaf parameterLeaf = new Leaf(Operand.IDENTIFIER, parameterName.getValue(), parameterName.getLine());
                    parameterLeaf.setType(edu.eltech.moevm.common.Type.valueOf(Parser.getTokenName(parameterType.getToken())));
                    result.add(parameterLeaf);
                    break;
                case DECLARATION:
                    // Declaration (with multiple variables) handling
                    PTElement declarationType = node.getElements().get(0);
                    Iterator<PTElement> it = node.getElements().iterator();
                    it.next();
                    while (it.hasNext()) {
                        PTElement el = it.next();
                        if (el instanceof PTLeaf)
                            if (Parser.getTokenName(((PTLeaf) el).getToken()).compareTo("COMMA") != 0)
                                if (Parser.getTokenName(((PTLeaf) el).getToken()).compareTo("SEMICOLON") != 0) {
                                    Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) el).getToken())), ((PTLeaf) el).getValue(), ((PTLeaf) el).getLine());
                                    leaf.setType(edu.eltech.moevm.common.Type.valueOf(Parser.getTokenName(((PTLeaf) declarationType).getToken())));
                                    result.add(leaf);
                                }
                    }
                    break;
                case INIT_DECLARATOR:
                    // Declaration with initialization handling
                    PTElement typeNode = node.getParent().getElements().get(0);
                    PTElement initDeclaratorNode = node.getElements().get(0);
                    PTElement initValue = node.getElements().get(2);
                    if (initDeclaratorNode instanceof PTLeaf) {
                        Leaf leaf = new Leaf(Operand.IDENTIFIER, ((PTLeaf) initDeclaratorNode).getValue(), ((PTLeaf) initDeclaratorNode).getLine());
                        leaf.setType(edu.eltech.moevm.common.Type.valueOf(Parser.getTokenName(((PTLeaf) typeNode).getToken())));
                        result.add(leaf);
                    }
                    if (initValue instanceof PTLeaf) {
                        Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) initValue).getToken())), ((PTLeaf) initValue).getValue(), ((PTLeaf) initValue).getLine());
                        result.add(leaf2);
                    }
                    break;
                case POSTFIX_EXPRESSION:
                    // Function calls and array access handling
                    PTElement baseNode = node.getElements().get(0);
                    PTElement nextNode = node.getElements().get(1);
                    String operation = Parser.getTokenName(((PTLeaf) nextNode).getToken());
                    Leaf postfixLeaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) baseNode).getToken())), ((PTLeaf) baseNode).getValue(), ((PTLeaf) baseNode).getLine());
                    result.add(postfixLeaf);
                    if (operation.compareTo("RBLEFT") == 0) {
                        result.setOperation(Operation.FUNC_CALL);
                    } else if (operation.compareTo("BRACKETLEFT") == 0) {
                        result.setOperation(Operation.ARRAY_ACCESS);
                        PTElement arrayIteratonValue = node.getElements().get(2);
                        Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) arrayIteratonValue).getToken())), ((PTLeaf) arrayIteratonValue).getValue(), ((PTLeaf) arrayIteratonValue).getLine());
                        result.add(leaf2);
                    }
                    break;
                case CONDITIONAL_EXPRESSION:
                    // ? operator handling
                    PTElement condition = node.getElements().get(0);
                    if (condition instanceof PTLeaf) {
                        Leaf leaf2 = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) condition).getToken())), ((PTLeaf) condition).getValue(), ((PTLeaf) condition).getLine());
                        result.add(leaf2);
                    }
                    break;
            }
        } else {
            result = null;
            switch (nt) {
                case RELATIONAL_EXPRESSION:
                    // Comparision operation handling
                    PTElement leftExpression = node.getElements().get(0);
                    PTElement relationalOperation = node.getElements().get(1);
                    PTElement rightExpression = node.getElements().get(2);
                    result = new Node(Operation.valueOf(Parser.getTokenName((((PTLeaf) relationalOperation).getToken()))));
                    if (leftExpression instanceof PTLeaf) {
                        Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) leftExpression).getToken())), ((PTLeaf) leftExpression).getValue(), ((PTLeaf) leftExpression).getLine());
                        result.add(leaf);
                    }
                    if (rightExpression instanceof PTLeaf) {
                        Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) rightExpression).getToken())), ((PTLeaf) rightExpression).getValue(), ((PTLeaf) rightExpression).getLine());
                        result.add(leaf);
                    }
                    break;
                case ASSIGNMENT_EXPRESSION:
                    result = new Node(Operation.EQUAL);
                    setBinaryExpr(result, node);
                    break;
                case ADDITIVE_EXPRESSION:
                    result = new Node(Operation.PLUS);
                    setBinaryExpr(result, node);
                    break;
                case MULTIPLICATIVE_EXPRESSION:
                    result = new Node(Operation.STAR);
                    setBinaryExpr(result, node);
                    break;
                case ARGUMENT_EXPRESSION_LIST:
                    // Functions arguments handling
                    result = new Node(Operation.FUNC_ARGS);
                    for (PTElement el : node.getElements()) {
                        if (el instanceof PTLeaf)
                            if (((PTLeaf) el).getToken() != Parser.COMMA) {
                                Leaf leaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) el).getToken())), ((PTLeaf) el).getValue(), ((PTLeaf) el).getLine());
                                result.add(leaf);
                            }
                    }
                    break;
                case UNARY_EXPRESSION:
                    // Postfix operation handling
                    PTElement unaryArg = node.getElements().get(0);
                    PTElement unaryOp = node.getElements().get(1);
                    String str = Parser.getTokenName(((PTLeaf) unaryOp).getToken());
                    Operation op = Operation.POST_INC_OP;
                    if (str.compareTo("INC_OP") == 0)
                        op = Operation.POST_INC_OP;
                    else if (str.compareTo("DEC_OP") == 0)
                        op = Operation.POST_DEC_OP;
                    result = new Node(op);
                    Leaf unaryLeaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) unaryArg).getToken())), ((PTLeaf) unaryArg).getValue(), ((PTLeaf) unaryArg).getLine());
                    result.add(unaryLeaf);
                    break;
                case CAST_EXPRESSION:
                    // Unary operation handling
                    PTElement castArg = node.getElements().get(0);
                    PTElement castOp = node.getElements().get(1);
                    result = new Node(Operation.valueOf(Parser.getTokenName(((PTLeaf) castArg).getToken())));
                    Leaf castLeaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) castOp).getToken())), ((PTLeaf) castOp).getValue(), ((PTLeaf) castOp).getLine());
                    result.add(castLeaf);
                    break;
                case JUMP_STATEMENT:
                    // Jump statements and "return" handling
                    PTElement jumpType = node.getElements().get(0);
                    PTElement jumpArg = node.getElements().get(1);
                    result = new Node(Operation.valueOf(Parser.getTokenName(((PTLeaf) jumpType).getToken())));
                    if (result.getOperation() == Operation.RETURN) {
                        Leaf jumpLeaf = new Leaf(Operand.valueOf(Parser.getTokenName(((PTLeaf) jumpArg).getToken())), ((PTLeaf) jumpArg).getValue(), ((PTLeaf) jumpArg).getLine());
                        result.add(jumpLeaf);
                    }
                    break;
            }
        }

        for (PTElement child : node.getElements()) {
            if (child instanceof PTNode) {
                Node ret = visitNode((PTNode) child);
                if (ret != null && result != null)
                    result.add(ret);
            }
        }
        return result;
    }
}
