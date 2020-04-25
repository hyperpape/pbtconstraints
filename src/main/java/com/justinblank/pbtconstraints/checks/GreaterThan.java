package com.justinblank.pbtconstraints.checks;

public final class GreaterThan<T> implements ConstraintOperator<T> {

    public static <T> Condition<T> gtCondition(ConstraintSource cs) {
        return new Condition<T>(cs, new GreaterThan<>());
    }

    @Override
    public String chocoArithmRep() {
        return ">";
    }
}
