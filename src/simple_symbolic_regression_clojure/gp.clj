(ns simple-symbolic-regression-clojure.gp
  )

(defn random-token
  "Argument is a list of tokens, including functions to generate values if you like;
  every token is evaluated as it is returned, and they are sampled with uniform probability."
  [tokens]
  (if (empty? tokens)
    nil
    (eval (nth tokens (rand-int (count tokens))))
  ))

(defn uniform-crossover
  "Takes two collections, and returns a new collection containing items taken with
  equal probability from the two 'parents' at each location. The length of the
  crossover-product is the shorter of the two lengths."
  [mom dad]
  (map (fn [a b] (if (< 0.5 (rand)) a b)) mom dad)
  )

(defn uniform-mutation
  "Takes a collections, a vector of tokens (including functions), and a probability;
  resamples eachposition with i.i.d. probability, using the token list to replace them.
  The result will not change length."
  [mom tokens prob]
  (map
    (fn [i]
      (if (or (> (rand) prob) (empty? tokens))
        i
        (random-token tokens)
        ))
    mom)
  )

(defn one-point-crossover
  "Takes two collections, and returns a new collection containing items taken from the
  front of the first and the tail of the second, switching at a randomly-chosen breakpoint
  in each."
  [mom dad]
  (let [mom-cut (rand-int (count mom))
        dad-cut (rand-int (count dad))]
    (concat (take mom-cut mom) (drop dad-cut dad))
  ))

(defrecord Individual [script score])

(defn make-individual
  ([script]
   (make-individual script nil))
  ([script score]
   (->Individual script score)))

(defn set-score [individual score]
  (assoc individual :score score))

(defn random-script
  "Takes a collection of token-generators and a size, and samples the generators using
  random-token to produce a vector of `size` samples."
  [token-list size]
  (repeatedly size #(random-token token-list))
  )

(defn random-individual
  "takes a token list and size, and returns an un-scored Individual"
  [tokens size]
  (make-individual (random-script tokens size)))
