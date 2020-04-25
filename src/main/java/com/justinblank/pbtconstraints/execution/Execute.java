package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.checks.*;
import com.justinblank.pbtconstraints.variables.ConstantValue;
import com.justinblank.pbtconstraints.variables.IVar;
import com.justinblank.pbtconstraints.variables.Variable;
import org.apache.commons.lang3.tuple.Pair;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.justinblank.pbtconstraints.variables.IVar.intVar;

public class Execute {

    public static Optional<Pair<Integer, Integer>> check(Pair<IVar, IVar> cVars, BiFunction<Integer, Integer, Boolean> test) {
        var constraints = new ConstraintSet(cVars.getLeft(), cVars.getRight());
        var vars = new HashMap<String, IntVar>();

        var model = new Model();
        for (Constraint<?> constraint : constraints.getConstraints()) {
            var variables = constraint.variables();
            for (Variable variable : variables) {
                vars.computeIfAbsent(variable.name(), (n) -> model.intVar(n, 0, Integer.MAX_VALUE - 1, true));
            }
            var condition = constraint.condition();
            var targetVar = vars.get(constraint.variable().name());
            if (condition.source() instanceof Variable v) {
                ConstraintOperator<?> operator = condition.operator();
                model.arithm(targetVar, condition.operator().chocoArithmRep(), vars.get(v.name())).reify();
            } else if (condition.source() instanceof ConstantValue<?> c) {
                model.arithm(targetVar, condition.operator().chocoArithmRep(), (Integer) c.t()).reify();
            }
        }

        var solver = model.getSolver();

        for (var i = 0; i < 100; i++) {
            solver.findSolution();
            var v1Soln = vars.get(cVars.getLeft().name()).getValue();
            var v2Soln = vars.get(cVars.getRight().name()).getValue();

            if (!test.apply(v1Soln, v2Soln)) {
                return Optional.of(Pair.of(v1Soln, v2Soln));
            }
        }

        return Optional.empty();
    }

    public static void main(String[] args) {
        var vars = intVar().lt(intVar());
        vars.getLeft().boundBy(0, 5);
        vars.getRight().boundBy(0, 20);

        System.out.println(check(vars, (a, b) -> (b - a) < 5));
    }
}
