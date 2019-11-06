(ns main
  (:require [a.hello]
            [b.math :as m :refer [add sub]]))

(println "Main program: " a.hello/long-string)
(println "PI is" m/PI)
(println "1 + 2" (add 1 2))
(println "1 - 2" (sub 1 2))
