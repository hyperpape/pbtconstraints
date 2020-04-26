package com.justinblank.pbtconstraints.checks;

import com.justinblank.pbtconstraints.variables.ConstraintVar;

import java.util.HashSet;
import java.util.Set;

public record Constraint<T extends ConstraintVar>(T variable, Condition<T> condition) {

    public Set<ConstraintVar> variables() {
        var variables = new HashSet<ConstraintVar>();
        variables.add(variable);
        if (condition.source() instanceof ConstraintVar v) {
            variables.add(v);
        }
        return variables;
    }
}
