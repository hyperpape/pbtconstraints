package com.justinblank.pbtconstraints.variables;

import com.justinblank.pbtconstraints.checks.Condition;
import com.justinblank.pbtconstraints.checks.ConstraintSource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public interface Variable<T extends Variable<T>> extends ConstraintSource {
    String name();

    Set<Condition<T>> conditions();

    Pair<T, T> lt(T t);

    Pair<T, T> gt(T t);

    T copy();

        static Variable fromClass(Class<?> c) {
        if (c == Integer.class) {
            return IVar.intVar();
        }
        throw new IllegalArgumentException("Class not supported yet");
    }
}
