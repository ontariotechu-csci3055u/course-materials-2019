(ns user
  (:require [clojure.pprint :refer [pprint]]))

(defmacro print-eval [& expr-list]
  ; at compile time,
  ; which is data** and which is code__??
  `(do ~@(for [[i expr] (map-indexed vector expr-list)]
           `(do (println ~i ":")
                (println ~(str expr))
                (println ~expr))))
  )

(defmacro infix [a op b]
  `(~op ~a ~b))

; Test the macro
(println "==== macroexpand ====")
(pprint
  (macroexpand-1
    '(print-eval (+ 1 2)
                (inc (+ 1 2))
                (apply + (for [i (range 100) :when (even? i)] i)))))

(println "==== runtime evaluation ====")
(print-eval (+ 1 2)
            (inc (+ 1 2))
            (apply + (for [i (range 100) :when (even? i)] i)))

(println (infix 100 + 200))
