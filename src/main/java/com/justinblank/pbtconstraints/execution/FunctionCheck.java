package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.variables.IVar;
import com.justinblank.pbtconstraints.variables.Variable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

record FunctionCheck<T, S extends Comparable<S>>(Function<T, S> func, Class<?> c1) {

    <P extends Variable<P>> boolean isMonotonic() {
        Variable<P> var1 = Variable.fromClass(c1);
        P var2 = var1.copy();
        var1.lt(var2);
        Pair<IVar, IVar> vars = Pair.of((IVar) var1, (IVar) var2);
        return Execute.check(vars, (x, y) -> func.apply((T) x).compareTo(func.apply((T) y)) < 1).isEmpty();
    }
}
