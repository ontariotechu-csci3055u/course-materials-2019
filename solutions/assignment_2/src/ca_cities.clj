;; Author: Michael Valdron
;; Date: Dec 2019
(ns ca-cities
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defonce ^:private earth-r 6371.)

(def csv-file "data/ca.csv")
(defn- rad [degree] (* Math/PI (/ degree 180.)))
(defn- hav [theta]
  (/ (- 1 (Math/cos theta)) 2))

(defn reader []
  (io/reader csv-file))

(defn parse-float [x]
  (try
    (cond
      (string? x) (Double/parseDouble x)
      (float? x) (double x)
      :default x)
    (catch NumberFormatException e
      x)))

(defn parse-int [x]
  (try
    (cond
      (string? x) (Long/parseLong x)
      (integer? x) (long x)
      :default x)
    (catch NumberFormatException e
      x)))

(defn parse-record [line]
  (-> (zipmap [:name :lat :long :country]
              (subvec line 0 4))
      (assoc :province (get line 5))
      (assoc :pop (get line 7))))

(defn cities []
  (let [sep-fn #(string/split % #",")
        parse-ints #(mapv parse-int %)
        parse-floats #(mapv parse-float %)]
    (with-open [r (reader)]
      (->> (reduce conj [] (rest (line-seq r)))
           (mapv sep-fn)
           (mapv parse-ints)
           (mapv parse-floats)
           (mapv parse-record)))))

(defn city [name]
  (->> (cities)
       (filterv #(= name (:name %)))
       first))

(defn distance [record1 record2]
  (let [coord (fn [r] (-> (select-keys r [:lat :long])
                          (#(zipmap (keys %)
                                    (mapv rad (vals %))))))
        coord1 (coord record1)
        coord2 (coord record2)
        h (+ (hav (- (:lat coord2) (:lat coord1)))
             (* (Math/cos (:lat coord1))
                (Math/cos (:lat coord2))
                (hav (- (:long coord2) (:long coord1)))))]
    (->> (Math/sqrt h)
         Math/asin
         (* 2 earth-r))))
