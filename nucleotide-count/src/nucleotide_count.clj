(ns nucleotide-count)

(refer 'clojure.core :rename '{count _count})

(defn count [nucleotide dna-string] 
  {:pre [(#{\A \C \G \T} nucleotide)]}
  (->> dna-string
       (filter #(= % nucleotide))
       (_count)  
  ))

(defn nucleotide-counts [dna-string] 
  (->> dna-string
       (frequencies)
       (merge {\A 0, \C 0, \G 0, \T 0})))
