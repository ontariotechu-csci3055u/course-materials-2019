(ns main)

(defprotocol Animal
  (make-noise [x])
  (has-fur? [x])
  (can-find? [x continent]))

(defrecord Bird [name wing-span species egg-color]
  Animal
  (make-noise [bird]
    (cond (< wing-span 10)
          (println "chirp chirp")
          :else (println "Quack quack")))
  (has-fur? [bird] false)
  (can-find? [bird continent]
    (case [(:species bird) continent]
      [:penguine :aarctic] true
      [:duck :na] true
      false)))

(let [pet (->Bird "DonaldDuck" 300 :duck :white)]
  (make-noise pet)
  (println "Aarctic" (can-find? pet :aarctic))
  (println "NA" (can-find? pet :na)))
