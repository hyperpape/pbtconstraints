package com.justinblank.pbtconstraints.checks;

import com.justinblank.pbtconstraints.variables.Variable;

import java.util.HashSet;
import java.util.Set;

public record Constraint<T extends Variable>(T variable, Condition<T> condition) {

    public Set<Variable> variables() {
        var variables = new HashSet<Variable>();
        variables.add(variable);
        if (condition.source() instanceof Variable v) {
            variables.add(v);
        }
        return variables;
    }
}
