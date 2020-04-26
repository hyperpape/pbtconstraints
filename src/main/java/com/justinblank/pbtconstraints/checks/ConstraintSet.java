package com.justinblank.pbtconstraints.checks;

import com.justinblank.pbtconstraints.variables.IVar;
import com.justinblank.pbtconstraints.variables.ConstraintVar;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConstraintSet {

    public ConstraintSet(IVar v1) {
    }

    public ConstraintSet(ConstraintVar<?> v1, ConstraintVar<?> v2) {
        for (Condition<?> condition : v1.conditions()) {
            addConstraint(new Constraint(v1, condition));
        }
        for (Condition<?> condition : v2.conditions()) {
            addConstraint(new Constraint(v2, condition));
        }
    }

    Set<Constraint<?>> constraints = new HashSet<>();

    public void addConstraint(Constraint<?> constraint) {
        this.constraints.add(constraint);
    }

    public Set<Constraint<?>> getConstraints() {
        return Collections.unmodifiableSet(constraints);
    }

    public Set<ConstraintVar> variables() {
        return new HashSet<>();
    }
}
