(ns a.hello
  (:require [clojure.string :refer [join]]))

(def long-string (join "|" (range 100)))

