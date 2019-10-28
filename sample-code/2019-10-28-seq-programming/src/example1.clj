(def students [{:name "Jack"
                :grade 89
                :skill #{:c :web :html :java}}
               {:name "Mary"
                :grade 90
                :skill #{:java :python :R}}
               {:name "John"
                :grade 78
                :skill #{:database :web :mobile :java :c :soccer}}])

(println "empty?")
(println (empty? students))

(println "every knows java?")
(println (every? (fn [student] (contains? (:skill student) :java)) students))

(println "every knows c")
(println (every? (fn [student] (contains? (:skill student) :c)) students))

(println "No one knows cobol")
(println (not-any? (fn [student] (contains? (:skill student) :cobol)) students))
