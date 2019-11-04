(println
  (let [filename "weather_madrid_LEMD_1997_2015.csv"
      date-pattern #"(\d+)-(\d+)-(\d+)"]
    (with-open [f (clojure.java.io/reader filename)]
      (let [content (slurp f)]
        (sort (into #{} (for [match (re-seq date-pattern content)] 
                          (nth match 1))))))))
