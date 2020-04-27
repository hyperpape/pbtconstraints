package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.variables.ConstraintVar;

import java.util.Optional;
import java.util.function.Function;

public class OperatorCheck<T extends Comparable<T>> extends FunctionCheck<T, T> {

    OperatorCheck(Function<T, T> operator, Class<?> c1) {
        super(operator, c1);
    }

    public boolean isIdempotent() {
        ConstraintVar<?> var1 = ConstraintVar.fromClass(getC1());
        Optional<T> result = Execute.falsify(var1, (T x) -> {
            T once = getFunc().apply(x);
            T twice = getFunc().apply(once);
            return once.equals(twice);
        });
        return result.isEmpty();
    }

    public boolean isInvolution() {
        ConstraintVar<?> var1 = ConstraintVar.fromClass(getC1());
        Optional<T> result = Execute.falsify(var1, (T x) -> {
            T twice = getFunc().apply(getFunc().apply(x));
            return x.equals(twice);
        });
        return result.isEmpty();
    }
}
