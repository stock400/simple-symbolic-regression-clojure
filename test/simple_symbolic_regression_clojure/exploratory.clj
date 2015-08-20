(ns simple-symbolic-regression-clojure.exploratory
  (:use midje.sweet)
  (:use [simple-symbolic-regression-clojure.gp])
  (:use [simple-symbolic-regression-clojure.core])
  )


;; exploratory work to understand a whole GP algorithm

(def random-code-atoms
  ['(rand-int 100) '(rand) '(rand-int 1000) '(rand-int 10) :+ :- :* :÷ :x])

(def x-plus-6-rubrics 
  (map #(->Rubric {:x %} (+ 6 %)) (range 20)))

(def population 
  (atom (into [] 
    (repeatedly 50 
      #(make-individual (random-script random-code-atoms
        5) nil))
    )))

(swap! population #(into [] (map (partial score-individual x-plus-6-rubrics) %)))

;; the preceding 2 lines should be folded into a single function make-population

(defn score-individual
  [rubrics individual]
  (set-score individual (total-score-on (:script individual) rubrics))
  individual)


; (println population)

;; pick 1
;; change him
;; loop

(defn tournament-of-n
  [n population]
  (winners (repeatedly n #(nth population (rand-int (count population)))))
  )

(def w (first (tournament-of-n 2 @population)))

(swap! population conj w)

(defn mutant
  [individual rate]
  (let [script (:script individual)
        new-script (uniform-mutation script random-code-atoms rate)
        child (make-individual new-script)]
        (score-individual x-plus-6-rubrics child)))

(defn crossover-baby
  [mom dad]
  (let [mom-script (:script mom)
        dad-script (:script dad)
        new-script (one-point-crossover mom-script dad-script)
        child (make-individual new-script)]
        (score-individual x-plus-6-rubrics child)))


(defn step
  [population]
  (swap! population conj (mutant
    (crossover-baby
      (first (tournament-of-n 2 @population))
      (first (tournament-of-n 7 @population)))
    0.3)))

;; put it through its paces:
(time (doall (repeatedly 1000 #(step population))))

;; print the scores
(println (reverse (sort (map get-score @population))))

;; peek at the best 10 individuals
(println (reverse (map :script (take 10 (sort-by get-score @population)))))

;; (... :x 6 :+)