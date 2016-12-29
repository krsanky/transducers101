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

;; STATEFUL
;; (transduce stateful-transducer conj (range 10))
(defn stateful-transducer [xf]
	(let [state (atom 100)]
		(fn
			([] (xf))
			([result] (xf result))
			([result input] 
				(swap! state inc)
				(cond
					(odd? input)
						(-> result
							(xf input)
							(xf (str "S:" @state)))
						:else result)))))

;; REDUCING
;; (transduce reducer conj [4 2 9])
(defn reducer [xf]
	(let [state (atom 1)]
		(fn
			([] (xf))
			([result] (xf @state))
			([result input] 
				(swap! state #(* input %))
				result))))

(defn -main [& args]
	(println "transducers..."))

