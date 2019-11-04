(defn stutter-1 [n message]
  (doall (for [i (range n)] 
           (println message))))

(defn stutter-2 [n message]
  (dotimes [i n]
    (println message)))

(stutter-1 3 "Hello")
(stutter-2 3 "World")
