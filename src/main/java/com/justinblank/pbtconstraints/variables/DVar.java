package com.justinblank.pbtconstraints.variables;

import com.justinblank.pbtconstraints.checks.Condition;
import com.justinblank.pbtconstraints.checks.GreaterThan;
import com.justinblank.pbtconstraints.checks.LessThan;
import com.justinblank.pbtconstraints.checks.NameFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record DVar(String name, Set<Condition<DVar>> conditions) implements ConstraintVar<DVar> {

    public static DVar doubleVar() {
        return new DVar(NameFactory.newName(), new HashSet<>());
    }

    public static DVar doubleVar(String name) {
        return new DVar(name, new HashSet<>());
    }

    public static DVar inRange(int low, int high) {
        return doubleVar().boundBy(low, high);
    }

    public DVar boundBy(int low, int high) {
        return lt(high).gt(low);
    }

    public DVar lt(int bound) {
        this.conditions.add(LessThan.ltCondition(ConstantValue.of(bound)));
        return this;
    }

    @Override
    public DVar copy() {
        return new DVar(NameFactory.newName(), new HashSet<>(conditions));
    }

    public Pair<DVar, DVar> lt(DVar var) {
        this.conditions.add(LessThan.ltCondition(var));
        return Pair.of(this, var);
    }

    public DVar gt(int bound) {
        this.conditions.add(GreaterThan.gtCondition(ConstantValue.of(bound)));
        return this;
    }

    public Pair<DVar, DVar> gt(DVar var) {
        this.conditions.add(GreaterThan.gtCondition(var));
        return Pair.of(this, var);
    }

    @Override
    public Set<Condition<DVar>> conditions() {
        return Collections.unmodifiableSet(conditions);
    }
}

