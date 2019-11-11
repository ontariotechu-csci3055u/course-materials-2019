(ns duck
  (:require [poly-lib :as poly]))

(defmethod poly/make-noise :duck
  [animal]
  (println "Quack quack"))
