(ns main
  (:require [poly-lib :as lib]))

(def duck {:species :duck})
(def dog {:species :dog})
(def t-rex {:species :dinosaur})
(def what??? {:species :weird})

(defn louder [animal]
  (lib/make-noise animal)
  (lib/make-noise animal))

(louder duck)
(println "--")
(louder dog)
(println "--")
(louder t-rex)
(println "--")
(louder what???)
