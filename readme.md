POC for using constraint solving to provide expressive
generators/invariants in property based tests.

WIP

Use:

Checking properties:
```
assertTrue(operator((Integer x) -> x % 2, Integer.class).isIdempotent());

Function<Integer, Integer> func = x -> x % 2 == 0 ? x / 2 : 3 * x + 1;
assertFalse(fn(func, Integer.class).isMonotonic());

// definition of log here
assertTrue(operator(log, Integer.class).hasFixedPoint());

assertTrue(fn(String::valueOf, Integer.class).decodes(Integer::parseInt));
```

Constructing generators:

```
var vars = intVar().lt(intVar());
vars.getLeft().boundBy(0, 5);
vars.getRight().boundBy(0, 20);

assertTrue(falsify(vars, (Integer a, Integer b) -> (b - a) < 5).isPresent());
```

