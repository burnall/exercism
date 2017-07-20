(ns podcast-man.core
  (:gen-class))

(def patterns [
  #"^6[23].*"
  #"^3[.][0-9].*" 
  #"^atp.*"
  #"^CTOcast.*"
  #"^hanselminutes.*"
  #"^devzen.*"
  #"^functionalgeekery.*"
  #"^SE-Radio.*"
  #"^rt_podcast.*"
  #"^haskellcast.*"
  #"^partially_derivative.*"
  #"^razbor.*"
  #"^sri_en_swetoday.*"
  #"^Turing.*"
])
(def ps (map #(-> {:p %, :i %2}) patterns (range)))

(defn read-files [dir]
  (->> dir 
      (clojure.java.io/file)
      (file-seq)
      (drop 1)
      (map #(.getName %))))

(defn get-pattern-index [fileName] 
  (or (some #(when (re-matches (:p %) fileName) (:i %)) ps) :no-pattern))  

(defn get-element [pattern-index files]
  (map #(-> {:name %, 
             :index pattern-index, 
             :value (/ %2 (count files))}) 
       files 
       (range))) 
   
(defn f [dir]
  (let [files (read-files dir)
        map-pattern-index-to-files (group-by get-pattern-index files)
        map-clean (dissoc map-pattern-index-to-files :no-pattern)
        elements (reduce-kv (fn [acc k v] (concat acc (get-element k v))) [] map-clean)
        sorted-elements (sort-by (juxt :value :index ) elements)
        rename-list (map #(-> {:old (str dir "/" (:name %)), :new (format "%s/%03d_%s" dir %2 (:name %))}) sorted-elements (range))
         ]
    (do 
      (println "Files beyond templates:" (:no-pattern map-pattern-index-to-files))
      (println (map #(.renameTo (clojure.java.io/file (:old %)) (clojure.java.io/file (:new %))) rename-list)))))
  

(defn -main
  [& args]
  (f (first args)))
