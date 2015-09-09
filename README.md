# simple-symbolic-regression-clojure

A simple Clojure implementation of a genetic programming symbolic regression system. This is essentially a serial (non-parallel, non-distributed) implementation of a very simple GP system that students can use as a starting point for creating parallel or distributed implementations. To keep things simple, we're (at least initially) _only_ focusing on parallelizing the evaluation of individual scripts, and not worrying about adding concurrency to things like selection, crossover, and building new populations.

## How to use it

Build a collection of `Rubric` items for your training dataset. For example, here's a set of rubrics `sine-rubrics` that contains 10 randomly-sampled `x`  values and maps them to the sine of `x`:

~~~ clojure
(def sine-rubrics
  (repeatedly 10 
    #(let [x (rand (* 2 Math/PI))]
      (->Rubric {:x x} (Math/sin x)))))
~~~

The first argument of the `->Rubric` constructor should be a map containing as its key the variable name `:x` _as such_... at least if your symbolic regression setup also is to include a variable `:x`. 

## To-dos

 * Write the interpreter
   * Mostly done except for variables
 * Compute the error of a script on a set of rubrics
   * This is ultimately what the students will parallelize/distribute
 * Store script and error in an `Individual` record
 * Have a population of `Individuals`
   * Probably a vector, but could be a set?
 * Select "winning" `Individual`s from a population (**stochastic**) (binary tournaments?)
 * Implement simple 2-pt XO on scripts (**stochastic**)
 * Implement simple mutation on scripts (**stochastic**)
 * Use these to create new population (**stochastic**)
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
