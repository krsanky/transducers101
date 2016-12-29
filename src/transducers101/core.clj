(ns transducers101.core
  (:gen-class))

(defn tmpl-xducer [xf]
	(fn
		;; set-up
		([] (xf))
		;; process
		([result input] (xf result input))
		;; tear-down
		([result] (xf result))))

;; old way:
;; (->> (range 10)
;;      (filter odd?)
;;      (map inc))
;; transducer way:
;; (into [] plain-filter-odd (range 10))
(defn plain-filter-odd [xf]
	(fn
		;; set-up
		([] (xf))
		;; process
		([result input] 
			(cond
				(odd? input) (xf result (inc input))
				:else result))
		;; tear-down
		([result] (xf result))))

(defn -main [& args]
  (println "transducers..."))

