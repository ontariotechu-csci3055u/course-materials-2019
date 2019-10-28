(def students [{:name "Jack"
                :grade 89
                :skill #{:c :web :html :java}}
               {:name "Mary"
                :grade 90
                :skill #{:java :python :R}}
               {:name "John"
                :grade 78
                :skill #{:database :web :mobile :java :c :soccer}}])

(println "Student names:" (map :name students))
(println "Student records:" 
         (map (fn [s] (str (:name s) ":" (:grade s))) students))


(println "Web programmers:"
         (map :name (filter #(contains? (:skill %) :web) students)))

(println "Java programmers:"
         (map :name (filter #(contains? (:skill %) :java) students)))

(println "Average grade:"
         (let [result (reduce (fn [old-state s]
                                 {:count (inc (:count old-state))
                                  :total (+ (:total old-state) (:grade s))})
                               {:count 0.
                                :total 0.} students)]
           (/ (:total result) (:count result))))

(println "Average grade:"
         (let [n (count students)
               total (apply + (map :grade students))]
           (/ (double total) n)))
