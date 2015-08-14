# simple-symbolic-regression-clojure

A simple Clojure implementation of a genetic programming symbolic regression system.

The project uses [Midje](https://github.com/marick/Midje/).

## Interpreter

A single numeric stack.

Support basic arithmetic (+, -, *, /) and possibly transcendental functions.

Start with single variable, but wouldn't hurt to support multi-variable.

The interpreter will simply take a program or script along with an input and return
an output. It doesn't apply any value judgement (or fitness) to that output.

We could throw in some simple stack operations like dup and swap since they're easy,
but we want to avoid anything that leads to termination concerns.

We need constants.

A script will be a vector of operations and literals.

When we process an item in the script we'll:
 * See if it's a number
 * See if it's got a binding (i.e., it's an input variable)
 * Otherwise apply it as a function.

## How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
