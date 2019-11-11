(ns lib
  (:require [duck]
            [dog]))

(defn make-noise [animal]
  (case (:species animal)
    :duck (duck/make-noise animal)
    :dog (dog/make-noise animal)))
