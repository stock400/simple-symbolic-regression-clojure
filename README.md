# simple-symbolic-regression-clojure

A Clojure implementation of a simple genetic programming symbolic regression system. This is essentially a serial (non-parallel, non-distributed) implementation of a very simple GP system that students can use as a starting point for creating parallel or distributed implementations. To keep things simple, we're (at least initially) _only_ focusing on parallelizing the evaluation of individual scripts, and not worrying about adding concurrency to things like selection, crossover, and building new populations.

The current system frankly isn't a terribly effective GP system and doesn't use any of the "tricks of the trade" that serious practitioners would use if they were trying to solve "real world" problems. Since the current goal is to provide a framework for exploring parallel evaluation, however, that's fine for now.

## How to use it

If you have [Leiningen installed](http://leiningen.org/) and clone this repo you should be able to run the system using `lein run` or `time lein run` if you want to collect timing data from your run.


## How to implement your own test problem

"Out of the box" it tries to find a function, using just +, -, *, and ÷, that approximates _sin(x)_. If you'd like to try to solve a different problem, build a collection of `Rubric` items for your training dataset. For example, here's a set of rubrics `sine-rubrics` that contains 10 randomly-sampled `x`  values and maps them to the sine of `x`:

```clojure
(def sine-rubrics
  (repeatedly 10 
    #(let [x (rand (* 2 Math/PI))]
      (->Rubric {:x x} (Math/sin x)))))
```

The first argument of the `->Rubric` constructor should be a map containing as its key the variable name `:x` _as such_... at least if your symbolic regression setup also is to include a variable `:x`. 

## Interpreter

Properties of the current implementation of the interpreter:

 - A single numeric stack.
 - Support basic arithmetic (+, -, *, /). (It would be nice to add transcendental functions.)
 - Support for ephemeral random constants.
 - An arbitrary number of input variables.
 - The interpreter simply takes a script, which is just a vector of operations and literals, along with an input and returns an output. It doesn't apply any value judgement (or fitness) to that output.

## How to run the tests

The project has a fairly comprehensive test suite, all based on the [Midje](https://github.com/marick/Midje/) test framework.

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
