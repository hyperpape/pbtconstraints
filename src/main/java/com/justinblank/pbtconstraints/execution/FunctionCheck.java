package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.variables.ConstraintVar;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;
import java.util.function.Function;

// TODO: record can't have package private constructor?
public record FunctionCheck<T, S extends Comparable<S>>(Function<T, S> func, Class<?> c1) {

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
}
