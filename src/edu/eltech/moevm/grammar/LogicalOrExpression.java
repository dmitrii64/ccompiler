package edu.eltech.moevm.grammar;

public class LogicalOrExpression extends Nonterminal {

	public LogicalOrExpression(LogicalAndExpression logicalAndExpression) {
		// ...
	}

	public LogicalOrExpression(LogicalOrExpression logicalOrExpression, LogicalAndExpression logicalAndExpression) {
		// ...
	}

}
