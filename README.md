# simple-symbolic-regression-clojure

A simple Clojure implementation of a genetic programming symbolic regression system.

## To-dos

 * Write the interpreter
   * Mostly done except for variables
 * Compute the error of a script on a set of rubrics
 * Store script and error in an `Individual` record
 * Have a population (vector? set?) of `Individuals`
 * Select "winning" `Individual`s from a population (stochastic) (binary tournaments?)
 * Implement simple 2-pt XO on scripts
 * Implement simple mutation on scripts
 * Use these to create new population
   * This assumes generational. Steady state instead? Or go with @Vaguary's model and just add individuals to an ever-increasing pot?
 * Run this thing!

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

The project uses [Midje](https://github.com/marick/Midje/).

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
