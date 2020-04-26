POC for using constraint solving to provide expressive
generators/invariants in property based tests.

WIP

Use:

Checking properties:
```
Function<Integer, Integer> fn = x -> x % 2 == 0 ? x / 2 : 3 * x + 1;
assertFalse(check(fn, Integer.class).isMonotonic());
```

Constructing generators:

```
var vars = intVar().lt(intVar());
vars.getLeft().boundBy(0, 5);
vars.getRight().boundBy(0, 20);

assertTrue(falsify(vars, (Integer a, Integer b) -> (b - a) < 5).isPresent());
```

