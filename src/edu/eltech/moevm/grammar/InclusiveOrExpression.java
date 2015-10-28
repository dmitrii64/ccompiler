package edu.eltech.moevm.grammar;

public class InclusiveOrExpression extends Nonterminal {

	public InclusiveOrExpression(ExclusiveOrExpression exclusiveOrExpression) {
		// ...
	}

	public InclusiveOrExpression(InclusiveOrExpression inclusiveOrExpression, ExclusiveOrExpression exclusiveOrExpression) {
		// ...
	}

}
