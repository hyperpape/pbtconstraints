package com.justinblank.pbtconstraints.variables;

import com.justinblank.pbtconstraints.checks.Condition;
import com.justinblank.pbtconstraints.checks.ConstraintSource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public interface ConstraintVar<T extends ConstraintVar<T>> extends ConstraintSource {
    String name();

    Set<Condition<T>> conditions();

    Pair<T, T> lt(T t);

    Pair<T, T> gt(T t);

    T copy();

    static ConstraintVar fromClass(Class<?> c) {
        if (c == Integer.class) {
            return IVar.intVar();
        }
        else if (c == Double.class) {
            return DVar.doubleVar();
        }
        throw new IllegalArgumentException("Class not supported yet");
    }
}
