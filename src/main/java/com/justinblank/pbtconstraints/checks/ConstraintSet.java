package com.justinblank.pbtconstraints.checks;

import com.justinblank.pbtconstraints.variables.IVar;
import com.justinblank.pbtconstraints.variables.Variable;
import org.chocosolver.solver.variables.IntVar;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConstraintSet {

    public ConstraintSet(IVar v1) {
    }

    public ConstraintSet(Variable<?> v1, Variable<?> v2) {
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

    public Set<Variable> variables() {
        return new HashSet<>();
    }
}
