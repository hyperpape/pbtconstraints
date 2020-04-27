package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.variables.ConstraintVar;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;
import java.util.function.Function;

public class FunctionCheck<T, S extends Comparable<S>> {

    private final Function<T, S> func;
    private final Class<?> c1;

    FunctionCheck(Function<T, S> func, Class<?> c1) {
        this.func = func;
        this.c1 = c1;
    }

    protected Function<T, S> getFunc() {
        return func;
    }

    protected Class<?> getC1() {
        return c1;
    }

    public <P extends ConstraintVar<P>> boolean isMonotonic() {
        ConstraintVar<P> var1 = ConstraintVar.fromClass(c1);
        P var2 = var1.copy();
        var1.lt(var2);
        Pair<P, P> vars = Pair.of((P) var1, (P) var2);
        Optional<Pair<T, T>> result = Execute.falsify(vars, (T x, T y) -> {
            S xMapped = func.apply(x);
            S yMapped = func.apply(y);
            return xMapped.compareTo(yMapped) < 1;
        });
        return result.isEmpty();
    }

    public <P extends ConstraintVar<P>> boolean decodes(Function<S, T> encoder) {
        ConstraintVar<P> var1 = ConstraintVar.fromClass(getC1());
        Optional<T> result = Execute.falsify(var1, (T t) -> {
            S decoded = getFunc().apply(t);
            T encoded = encoder.apply(decoded);
            return encoded.equals(t);
        });
        return result.isEmpty();
    }
}
