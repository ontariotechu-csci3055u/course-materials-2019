(ns weather
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(def filename "weather_madrid_LEMD_1997_2015.csv")

(defn greetings []
  (println "Hello world"))

(defn comma-split [line]
  (map s/trim (s/split line #",")))

(defn get-records []
  (let [f (io/reader filename)
        lines (line-seq f)
        headings (comma-split (first lines))
        rows (map comma-split (rest lines))]
    (for [row rows]
      (zipmap headings row))))
