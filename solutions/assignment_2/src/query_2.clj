;; Author: Michael Valdron
;; Date: Dec 2019
(ns query-2
  (:require [ca-cities :as ca]))

; returns the total population of the cities
(defn total-population [cities]
  (apply + (mapv :pop cities)))

; list of sorted [province-name population] pairs
(defn provincial-population
  []
  (let [prov-pop (->> (ca/cities)
                      (map #(select-keys
                             % [:province :pop]))
                      (group-by :province))
        provs (keys prov-pop)
        pops (map #(apply + (map :pop %))
                  (vals prov-pop))]
    (->> (zipmap provs pops)
         (into [])
         (sort-by last)
         vec)))
