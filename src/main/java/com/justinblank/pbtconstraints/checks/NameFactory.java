package com.justinblank.pbtconstraints.checks;

import java.util.concurrent.atomic.AtomicInteger;

public class NameFactory {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static String newName() {
        return "anon" + COUNTER.incrementAndGet();
    }
}
