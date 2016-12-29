(ns transducers101.core
	(:gen-class))

;; BASICS
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

;; DUPLICATION
;; (transduce duplicate-odd conj (range 11))
(defn duplicate-odd [xf]
	(fn
		([] (xf))
		([result input] 
			(cond
				(odd? input)
					(-> result
						(xf input)
						(xf input))
				:else result))
		([result] (xf result))))

(defn -main [& args]
	(println "transducers..."))

