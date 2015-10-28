package edu.eltech.moevm.grammar;

public class ExclusiveOrExpression extends Nonterminal {

	public ExclusiveOrExpression(AndExpression andExpression) {
		// ...
	}

	public ExclusiveOrExpression(ExclusiveOrExpression exclusiveOrExpression, AndExpression andExpression) {
		// ...
	}

}
