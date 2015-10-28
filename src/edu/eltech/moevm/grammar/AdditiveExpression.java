package edu.eltech.moevm.grammar;

public class AdditiveExpression extends Nonterminal {

	public AdditiveExpression(MultiplicativeExpression multiplicativeExpression) {
		// ...
	}

	public AdditiveExpression(AdditiveExpression additiveExpression, MultiplicativeExpression multiplicativeExpression) {
		// ...
	}

}
