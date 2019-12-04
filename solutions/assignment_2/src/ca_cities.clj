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
      (float? x) x
      :default nil)
    (catch NumberFormatException e
      (println (.getMessage e)))))

(defn parse-int [x]
  (try
    (cond
      (string? x) (Long/parseLong x)
      (integer? x) x
      :default nil)
    (catch NumberFormatException e
      (println (.getMessage e)))))

(defn parse-record [line]
  (let [line-vec (string/split line #",")]
    (-> (subvec line-vec 0 4)
        (#(zipmap [:name :lat :long :country] %))
        (assoc :province (get line-vec 5))
        (assoc :pop (get line-vec 7)))))

(defn cities []
  (let [parse-int-by-key #(update %2 %1 parse-int)
        parse-float-by-key #(update %2 %1 parse-float)]
    (with-open [r (reader)]
      (->> (reduce conj [] (rest (line-seq r)))
           (mapv parse-record)
           (mapv (partial parse-int-by-key :pop))
           (mapv (partial parse-float-by-key :lat))
           (mapv (partial parse-float-by-key :long))))))

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
