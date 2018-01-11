(ns podcast-man.core
  (:gen-class))
  
;(:require [clojure.pprint :as pprint]))

(defn make-patterns [pattern-list] 
  (map #(-> {:p %, :i %2}) pattern-list (range)))

(defn read-files [dir]
  (->> dir 
      (clojure.java.io/file)
      (file-seq)
      (drop 1)
      (sort)
      (map #(.getName %))))

(defn get-pattern-index [patterns] 
  (fn [file-name] 
    (or (some #(when (re-matches (:p %) file-name) (:i %)) patterns) :no-pattern)))  

(defn get-element [pattern-index files]
  (map #(-> {:name %, 
             :index pattern-index, 
             :value (/ %2 (count files))}) 
       files 
       (range))) 
   
(defn main [dir pattern-list]
  (let [files (read-files dir)
        patterns (make-patterns pattern-list)
        map-pattern-index-to-files (group-by (get-pattern-index patterns) files)
        map-clean (dissoc map-pattern-index-to-files :no-pattern)
        elements (reduce-kv (fn [acc k v] (concat acc (get-element k v))) [] map-clean)
        sorted-elements (sort-by (juxt :value :index ) elements)
        rename-list (map #(-> {:old (str dir "/" (:name %)), :new (format "%s/%03d_%s" dir %2 (:name %))}) sorted-elements (range))
    ]
    (do 
      (println "XXXX " map-pattern-index-to-files) 
      (println "Files beyond templates:" (:no-pattern map-pattern-index-to-files))
      (println (map #(.renameTo (clojure.java.io/file (:old %)) (clojure.java.io/file (:new %))) rename-list))
      )))
  
(defn get-pattern-list []
  (-> "./podcasts"
      (slurp)
      (clojure.string/split #"\n")
      (#(map re-pattern %)))) 

(defn -main
  [& args]
  (main (first args) (get-pattern-list)))
