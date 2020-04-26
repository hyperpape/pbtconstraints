package com.justinblank.pbtconstraints.variables;

import com.justinblank.pbtconstraints.checks.Condition;
import com.justinblank.pbtconstraints.checks.GreaterThan;
import com.justinblank.pbtconstraints.checks.LessThan;
import com.justinblank.pbtconstraints.checks.NameFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record IVar(String name, Set<Condition<IVar>> conditions) implements Variable<IVar> {

    public static IVar intVar() {
        return new IVar(NameFactory.newName(), new HashSet<>());
    }

    public static IVar inRange(int low, int high) {
        return intVar().boundBy(low, high);
    }

    public IVar boundBy(int low, int high) {
        return lt(high).gt(low);
    }

    public IVar lt(int bound) {
        this.conditions.add(LessThan.ltCondition(ConstantValue.of(bound)));
        return this;
    }

    @Override
    public IVar copy() {
        return new IVar(NameFactory.newName(), new HashSet<>(conditions));
    }

    public Pair<IVar, IVar> lt(IVar var) {
        this.conditions.add(LessThan.ltCondition(var));
        return Pair.of(this, var);
    }

    public IVar gt(int bound) {
        this.conditions.add(GreaterThan.gtCondition(ConstantValue.of(bound)));
        return this;
    }

    public Pair<IVar, IVar> gt(IVar var) {
        this.conditions.add(GreaterThan.gtCondition(var));
        return Pair.of(this, var);
    }

    @Override
    public Set<Condition<IVar>> conditions() {
        return Collections.unmodifiableSet(conditions);
    }
}
