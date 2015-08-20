(ns simple-symbolic-regression-clojure.exploratory
  (:use midje.sweet)
  (:use [simple-symbolic-regression-clojure.gp])
  (:use [simple-symbolic-regression-clojure.core])
  )


;; exploratory

(def population 
  (atom (into #{} 
    (repeatedly 10 
      #(make-individual (random-script 
        ['(rand-int 100) '(rand) '(rand-int 1000) '(rand-int 10) :+ :- :* :÷ :x]
        10) nil))
    )))

; (println population)

(def x-plus-6-rubrics 
  (repeatedly 20 #(let [x (rand-int 100)] (->Rubric {:x x} (+ 6 x)))))

; (println x-plus-6-rubrics)

(defn score-individual
  [rubrics individual]
  (assoc individual :score (total-score-on (:script individual) rubrics)))

(swap! population #(map (partial score-individual x-plus-6-rubrics) %))

(println population)