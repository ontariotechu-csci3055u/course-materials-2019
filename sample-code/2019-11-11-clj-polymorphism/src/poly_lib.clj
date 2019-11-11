(ns poly-lib)

(defn get-species [animal]
  (:species animal))

(defmulti make-noise get-species)

(defmethod make-noise :default
  [animal]
  (println "Unknown species:" (get-species animal)))

(defmethod make-noise :duck
  [animal]
  (println "Quack quack"))

(defmethod make-noise :dog
  [animal]
  (println "Woof woof"))

(defmethod make-noise :dinosaur
  [animal]
  (println "zzzqlkdwjf;lkjfd;lkadjf"))
