(ns beer-song
  (:require [clojure.string :as str]))

(defn verse [n]
  (condp = n
    2 (str "2 bottles of beer on the wall, 2 bottles of beer." "\n" "Take one down and pass it around, 1 bottle of beer on the wall.\n")
    1 (str "1 bottle of beer on the wall, 1 bottle of beer." "\n" "Take it down and pass it around, no more bottles of beer on the wall.\n")
    0 (str "No more bottles of beer on the wall, no more bottles of beer." "\n" "Go to the store and buy some more, 99 bottles of beer on the wall.\n")
    (str (format "%d bottles of beer on the wall, %d bottles of beer.\n" n n) (format "Take one down and pass it around, %d bottles of beer on the wall.\n" (- n 1))))) 

(defn sing
  ([m] (sing m 0))
  ([m n] (clojure.string/join "\n" (map verse(range m (- n 1) -1)))))
