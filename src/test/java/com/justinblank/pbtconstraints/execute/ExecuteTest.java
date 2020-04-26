package com.justinblank.pbtconstraints.execute;

import org.testng.annotations.Test;

import static com.justinblank.pbtconstraints.execution.Execute.falsify;
import static com.justinblank.pbtconstraints.variables.IVar.intVar;
import static junit.framework.Assert.assertTrue;

public class ExecuteTest {

    @Test
    public void testCheck() {
        var vars = intVar().lt(intVar());
        vars.getLeft().boundBy(0, 5);
        vars.getRight().boundBy(0, 20);

        assertTrue(falsify(vars, (Integer a, Integer b) -> (b - a) < 5).isPresent());
    }
}
