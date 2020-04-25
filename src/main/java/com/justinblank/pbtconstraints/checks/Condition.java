package com.justinblank.pbtconstraints.checks;

public record Condition<T>(
    ConstraintSource source,
    ConstraintOperator<T> operator) {}
