package edu.eltech.moevm.grammar;

public class AssignmentExpression extends Nonterminal {

	public AssignmentExpression(ConditionalExpression conditionalExpression) {
		// ...
	}

	public AssignmentExpression(UnaryExpression unaryExpression, AssignmentExpression assignmentExpression) {
		// ...
	}

}
