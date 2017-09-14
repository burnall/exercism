(ns bracket-push
  (:require [clojure.string :as str]))

(def opening-brackets #{\( \{ \[})

(def closing-brackets {\) \(,  \} \{, \] \[})

(defn valid? 
  ([expr] 
   (valid? expr (list))) 

  ([expr stack] 
   (let [[top & tail] expr]
    (cond 
      (empty? expr) (empty? stack)
      (opening-brackets top) (recur tail (cons top stack)) 
      (closing-brackets top) (if (= (first stack) (closing-brackets top)) (recur tail (rest stack)) false) 
      :else (recur tail stack)))))
