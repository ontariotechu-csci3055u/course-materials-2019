(require '[ca-cities :as ca])
(require '[query-1 :as q1])
(require '[query-2 :as q2])
(require '[clojure.pprint :refer [pprint]])

(printf "Total cities: %d\n" (count (ca/cities)))

(println "Distance from Toronto to Oshawa:"
         (format "%.2f km" (ca/distance (ca/city "Toronto") (ca/city "Oshawa"))))

(println "Nearest pairs of large cities (>500,000)")
(pprint (for [[c1, c2, d] (q1/closest-city-pairs)]
          [c1 c2 (format "%.2f" d)]))

(println "Provinces and their population")
(pprint (q2/provincial-population))
