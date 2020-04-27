package com.justinblank.pbtconstraints.execution;

import java.util.function.Function;

public class Check {

    public static <T, S extends Comparable<S>> FunctionCheck<T, S> fn(Function<T, S> func, Class<?> c1) {
        return new FunctionCheck<>(func, c1);
    }

    public static <T extends Comparable<T>> OperatorCheck<T> operator(Function<T, T> operator, Class<?> c1) {
        return new OperatorCheck<T>(operator, c1);
    }

    private Check() {}
}
