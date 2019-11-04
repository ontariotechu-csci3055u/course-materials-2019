(defn first-column [lines]
  (for [line lines]
    (first (clojure.string/split line #","))))

(let [file (clojure.java.io/reader "weather_madrid_LEMD_1997_2015.csv")
      lines (line-seq file)
      date-pattern #"(\d+)-(\d+)-(\d+)"]
  (let [distinct-year (into #{}
                            (for [date-string (first-column (rest lines))] 
                              (let [match (re-matches date-pattern date-string)] 
                                (nth match 1))))]
    (println (sort distinct-year))))
