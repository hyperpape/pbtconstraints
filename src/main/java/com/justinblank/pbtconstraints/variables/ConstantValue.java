package com.justinblank.pbtconstraints.variables;

import com.justinblank.pbtconstraints.checks.ConstraintSource;

public record ConstantValue<T>(T t) implements ConstraintSource {

    static <T> ConstantValue<T> of(T t) {
        return new ConstantValue<>(t);
    }
}
