package edu.eltech.moevm.grammar;

public class IterationStatement extends Nonterminal {

	public IterationStatement(Expression expression, Statement statement) {
		// ...
	}

	public IterationStatement(Statement statement, Expression expression) {
		// ...
	}

	public IterationStatement(ExpressionStatement expressionStatement1, ExpressionStatement expressionStatement2, Statement statement) {
		// ...
	}

	public IterationStatement(ExpressionStatement expressionStatement1, ExpressionStatement expressionStatement2, Expression expression, Statement statement) {
		// ...
	}

}
