(ns word-count
  (:require [clojure.string :as str])) 

(defn word-count [phrase] 
  (-> phrase 
      str/lower-case 
      (str/split #"\W+")
      frequencies))

