(ns anagram 
  (:require [clojure.string :as str]))

(defn anagrams-for [word candidates]
  (let [normalize (comp sort str/lower-case) 
        sample (normalize word)]
    (filter #(and (= sample (normalize %)) (not= (str/lower-case word) (str/lower-case %))) candidates)))
