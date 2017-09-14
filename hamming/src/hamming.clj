(ns hamming)

(defn distance [a b] 
  (when (= (count a) (count b))
    (reduce + (map #(if (= % %2) 0 2) a b))))
