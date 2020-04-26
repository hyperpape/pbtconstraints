package com.justinblank.pbtconstraints.checks;

import com.justinblank.pbtconstraints.execution.Check;
import org.junit.Test;

import java.util.function.Function;

import static com.justinblank.pbtconstraints.execution.Check.check;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckTest {

    @Test
    public void testCheckMonotonicInteger() {
        assertTrue(check((Integer x) -> x + 2, Integer.class).isMonotonic());
    }

    @Test
    public void testCheckNonMonotonicFunctionIsntMonotonic() {
        Function<Integer, Integer> fn = x -> x % 2 == 0 ? x / 2 : 3 * x + 1;
        assertFalse(check(fn, Integer.class).isMonotonic());
    }

    @Test
    public void testCheckMonotonicDouble() {
        assertTrue(check((Double x) -> x + 2, Double.class).isMonotonic());
    }

    @Test(expected = Exception.class)
    public void testCheckComplainsAboutMismatchedClass() {
        assertTrue(check((Double x) -> x + 2, Integer.class).isMonotonic());
    }
}
