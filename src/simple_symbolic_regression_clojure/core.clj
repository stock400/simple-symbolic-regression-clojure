(ns simple-symbolic-regression-clojure.core)

(defn process-token [stack token]
  "Process given token returning the updated stack"
  (cond
   (number? token) (conj stack token)
   (and (= token +) (>= (count stack) 2))
     (conj (drop 2 stack)
           (+ (first stack)
              (second stack)))
   :else stack)
  )

(defn run-script [script bindings]
  "loop over every item in the script and modify the stack accordingly"
  (reduce process-token [] script)
  )

(defn interpret [script bindings]
  (let [stack (run-script script bindings)
        answer (peek stack)]
    {:result answer, :stack stack}))
