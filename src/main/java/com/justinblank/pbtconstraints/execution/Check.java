package com.justinblank.pbtconstraints.execution;

import com.justinblank.pbtconstraints.variables.IVar;
import com.justinblank.pbtconstraints.variables.Variable;

import java.util.function.Function;

public class Check {

    public static <T, S extends Comparable> FunctionCheck check(Function<T, S> func, Class<?> c1) {
        return new FunctionCheck<>(func, c1);
    }

    private Check() {}
}
