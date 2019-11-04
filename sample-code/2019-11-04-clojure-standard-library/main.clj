(require '[clojure.pprint :refer [pprint]])
(require 'weather)

(doseq [record (take 10 (weather/get-records))]
  (println (get record "CET")
           (get record "Min Humidity")))
