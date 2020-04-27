package com.justinblank.pbtconstraints.checks;

import org.junit.Test;

import java.util.function.Function;

import static com.justinblank.pbtconstraints.execution.Check.fn;
import static com.justinblank.pbtconstraints.execution.Check.operator;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckTest {

    @Test
    public void testCheckMonotonicInteger() {
        assertTrue(fn((Integer x) -> x + 2, Integer.class).isMonotonic());
    }

    @Test
    public void testCheckNonMonotonicFunctionIsntMonotonic() {
        Function<Integer, Integer> func = x -> x % 2 == 0 ? x / 2 : 3 * x + 1;
        assertFalse(fn(func, Integer.class).isMonotonic());
    }

    @Test
    public void testCheckMonotonicDouble() {
        assertTrue(fn((Double x) -> x + 2, Double.class).isMonotonic());
    }

    @Test(expected = Exception.class)
    public void testCheckComplainsAboutMismatchedClass() {
        assertTrue(fn((Double x) -> x + 2, Integer.class).isMonotonic());
    }

    @Test
    public void testCheckIdempotency() {
        assertTrue(operator((Integer x) -> x % 2, Integer.class).isIdempotent());
        assertFalse(operator((Integer x) -> x + 1, Integer.class).isIdempotent());
    }

    @Test
    public void testCheckInvolution() {
        assertTrue(operator((Integer x) -> x * -1, Integer.class).isInvolution());
        assertFalse(operator((Integer x) -> x * 2, Integer.class).isInvolution());
    }
}
