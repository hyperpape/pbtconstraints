package com.justinblank.pbtconstraints.checks;

public final class LessThan<T> implements ConstraintOperator<T> {

    public static <T> Condition<T> ltCondition(ConstraintSource cs) {
        return new Condition<T>(cs, new LessThan<>());
    }

    @Override
    public String chocoArithmRep() {
        return "<";
    }
}
