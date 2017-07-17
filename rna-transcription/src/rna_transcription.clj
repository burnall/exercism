(ns rna-transcription)

(def to-rna
  (comp 
    (partial apply str) 
    (partial map #(or ({\G \C, \C \G, \T \A, \A \U} %) (assert nil)))))

