(ns simple-symbolic-regression-clojure.gp-test
  (:use midje.sweet)
  (:use [simple-symbolic-regression-clojure.gp])
  (:require (bigml.sampling [simple :as simple]))
  )

(fact "we can select a subsample of a collection using bigml.sampling"
  (count (take 5 (simple/sample (range 1000)))) => 5
  ; (take 5 (simple/sample (range 1000))) => [some collection of 5 numbers]
  )

(fact "but we can stub that by name"
  (with-redefs [simple/sample (fn [s] (cycle [1 2]))]
    (take 5 (simple/sample (range 1000))) => (just [1 2 1 2 1]))
  )
 
(fact "we can stub core random number generation if we want to"
  (with-redefs [rand-int (fn [arg] 88)]
    [(rand-int 11) (rand-int 22) (rand-int 33)] => (just [88 88 88]))
  )

(defn cycler
  [numbers]
  (atom {:step 0, :values (cycle numbers)})
  )

(defn step-cycler
  [c]
  (let [result (nth (:values @c) (:step @c))]
    (do
      (swap! c assoc :step (inc (:step @c)))
      result
  )))

(fact "we can call step-cycler to produce a number from the cycler"
  (let [cc (cycler [-1 2 -3 4])]
    (step-cycler cc) => -1
    (step-cycler cc) => 2
    (step-cycler cc) => -3
    (step-cycler cc) => 4
    ))

(fact "we can NOW stub random number generation with a cycler"
  (let [stubby (cycler [8 7 2 5])]
    (with-redefs [rand-int (fn [arg] (step-cycler stubby))]
      (take 7 (repeatedly #(rand-int 1000))) => (just [8 7 2 5 8 7 2])))
  )

(fact "it works for other random things too"
  (let [stubby (cycler [0.8 0.7 0.2 0.5])]
    (with-redefs [rand (fn [arg] (step-cycler stubby))]
      (take 7 (repeatedly #(rand 9.0))) => (just [0.8 0.7 0.2 0.5 0.8 0.7 0.2])))
  )
