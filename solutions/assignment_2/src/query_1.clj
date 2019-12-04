;; Author: Michael Valdron
;; Date: Dec 2019
(ns query-1
  (:require [ca-cities :as ca]))

; check if the city has more than 0.5E6 people
(defn large? [city]
  (let [large-pop 0.5E6
        pop (:pop city)]
    (> pop large-pop)))

; check if the two cities are no more than 600 km apart
(defn close? [c1 c2]
  (let [close-dist 600.
        dist (ca/distance c1 c2)]
    (<= dist close-dist)))

; find distinct pairs of city names of *large* cities
; that are close.
(defn closest-city-pairs []
  (let [large-cities (filter large? (ca/cities))
        all-pairs (fn [coll]
                    (loop [[x & xs] coll
                           result []]
                      (if (nil? xs)
                        result
                        (recur xs
                               (concat result
                                       (map #(vector x %)
                                            xs))))))
        ]
    (->> (all-pairs large-cities)
         (filter #(not= (:name (first %))
                        (:name (second %))))
         (filter #(apply close? %))
         (map #(sort-by :name %))
         (mapv #(vec
                 (concat (mapv :name %)
                         [(apply ca/distance %)]))))))
