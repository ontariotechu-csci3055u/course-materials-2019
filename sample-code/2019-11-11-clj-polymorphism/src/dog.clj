(ns dog
  (:require [poly-lib :as poly]))

(defmethod poly/make-noise :dog
  (println "Woof woof"))
