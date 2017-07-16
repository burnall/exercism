(ns bob)

(defn response-for [phrase]
  (cond 
    (= (clojure.string/trim phrase) "") "Fine. Be that way!"
    (and (= (clojure.string/upper-case phrase) phrase) 
             (re-find #"[A-Z]" phrase)) "Whoa, chill out!"
    (= (last phrase) \?) "Sure."
    :else "Whatever."))

