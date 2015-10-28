package edu.eltech.moevm.grammar;

public class UnaryExpression extends Nonterminal {

	public UnaryExpression(PostfixExpression postfixExpression) {
		// ...
	}

	public UnaryExpression(UnaryExpression unaryExpression) {
		// ...
	}

	public UnaryExpression(UnaryOperator unaryOperator, CastExpression castExpression) {
		// ...
	}

	public UnaryExpression(TypeName typeName) {
		// ...
	}

}
