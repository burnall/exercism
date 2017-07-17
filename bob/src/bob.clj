(ns bob
  (:require [clojure.string :refer [upper-case blank? ends-with?]]))

(defn response-for [phrase]
  (cond 
    (blank? phrase) "Fine. Be that way!"
    (and (= (upper-case phrase) phrase) 
             (re-find #"[A-Z]" phrase)) "Whoa, chill out!"
    (ends-with? phrase "?") "Sure."
    :else "Whatever."))

