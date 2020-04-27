package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.checks.Constraint;
import com.justinblank.pbtconstraints.checks.ConstraintSet;
import com.justinblank.pbtconstraints.variables.ConstantValue;
import com.justinblank.pbtconstraints.variables.ConstraintVar;
import com.justinblank.pbtconstraints.variables.DVar;
import com.justinblank.pbtconstraints.variables.IVar;
import org.apache.commons.lang3.tuple.Pair;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Execute {

    public static <T> Optional<T> falsify(ConstraintVar<?> var, Function<T, Boolean> test) {
        var constraints = new ConstraintSet(var);
        var modelAndVars = makeModelAndVars(constraints, var);
        var model = modelAndVars.model();
        var vars = modelAndVars.vars();
        var solver = model.getSolver();

        for (var i = 0; i < 100; i++) {
            solver.findSolution(); // TODO: don't assume solution exists
            var soln = vars.get(var.name());

            T o1 = (T) extractValue(soln);

            if (null == o1) {
                continue;
            }
            if (!test.apply(o1)) {
                return Optional.of(o1);
            }

        }

        return Optional.empty();
    }

    public static <T, S> Optional<Pair<T, S>> falsify(Pair<? extends ConstraintVar, ? extends ConstraintVar> cVars, BiFunction<T, S, Boolean> test) {
        var constraints = new ConstraintSet(cVars.getLeft(), cVars.getRight());
        var modelAndVars = makeModelAndVars(constraints);
        var model = modelAndVars.model();
        var vars = modelAndVars.vars();
        var solver = model.getSolver();

        if (cVars.getLeft() instanceof IVar && cVars.getRight() instanceof IVar) {
            IntVar[] toMonitor = new IntVar[2];
            toMonitor[0] = (IntVar) vars.get(cVars.getLeft().name());
            toMonitor[1] = (IntVar) vars.get(cVars.getRight().name());
            solver.plugMonitor(new NovelSolutionMonitor(toMonitor));
        }

        for (var i = 0; i < 100; i++) {
            solver.findSolution(); // TODO: don't assume solution exists
            var solnVar1 = vars.get(cVars.getLeft().name());
            var solnVar2 = vars.get(cVars.getRight().name());

            T o1 = (T) extractValue(solnVar1);
            S o2 = (S) extractValue(solnVar2);

            if (null == o1 || null == o2) {
                continue;
            }
            if (!test.apply(o1, o2)) {
                return Optional.of(Pair.of(o1, o2));
            }

        }

        return Optional.empty();
    }

    private static ModelAndVars makeModelAndVars(ConstraintSet constraints, ConstraintVar<?>... constraintVars) {
        var vars = new HashMap<String, Variable>();
        var model = new Model();
        for (Constraint<?> constraint : constraints.getConstraints()) {
            var variables = constraint.variables();
            for (ConstraintVar<?> variable : variables) {
                addConstraintVarToModel(vars, model, variable);
            }
            var condition = constraint.condition();
            var targetVar = vars.get(constraint.variable().name());
            if (condition.source() instanceof ConstraintVar<?> v) {
                if (targetVar instanceof IntVar i && v instanceof IVar i2) {
                    model.arithm(i, condition.operator().chocoArithmRep(), (IntVar) vars.get(i2.name())).reify();
                } else if (targetVar instanceof RealVar r && v instanceof DVar d2) {
                    // TODO: actually constrain RealVar values
                }
            } else if (condition.source() instanceof ConstantValue<?> c) {
                if (targetVar instanceof IntVar i && c.t() instanceof Integer i2) {
                    model.arithm(i, condition.operator().chocoArithmRep(), i2).reify();
                } else {
                    // TODO: actually constrain RealVar values
                }
            }
        }
        for (ConstraintVar<?> constraintVar : constraintVars) {
            addConstraintVarToModel(vars, model, constraintVar);
        }

        return new ModelAndVars(model, vars);
    }

    private static void addConstraintVarToModel(Map<String, Variable> vars, Model model, ConstraintVar<?> variable) {
        if (variable instanceof IVar i) {
            // TODO: not zero
            vars.computeIfAbsent(variable.name(), (name) -> model.intVar(name, 0, Integer.MAX_VALUE - 1, true));
        } else if (variable instanceof DVar d) {
            // TODO: not zero
            // TODO: not MAX_VALUE / 2--learn some floating point, JUSTIN
            // TODO: bad value for precision
            vars.computeIfAbsent(variable.name(), (name) -> model.realVar(name, 0, Double.MAX_VALUE / 2, .00000001d));
        }
    }

    private static Object extractValue(Variable solnVar1) {
        if (solnVar1 instanceof IntVar i1) {
            return i1.getValue();
        } else {
            return null;
        }
    }

    private record ModelAndVars(Model model, Map<String, Variable>vars) {
    }
}
