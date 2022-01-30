(ns generativeartistry.cubic-disarray
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/cubic-disarray/

(def square-size 40)
(def random-displacement 15)
(def rotate-multiplier 20)
;; Haven't figured out the necessity of the offset in original tutorial
;; (def offset 10)

(def size (* 9 square-size))


(defn randomize
  [amt val]
  (let [plus-or-minus (if (< (rand) 0.5) -1 1)]
    (* amt val plus-or-minus (rand))))


(defn draw []
  (background 255)
  (stroke-weight 2)
  (no-fill)
  (let [grid (range square-size size square-size)
        n-half-size (- (/ square-size 2))]
    (doseq [j grid
            i grid
            :let [amt (/ j size)
                  rotate-amt (radians (randomize amt rotate-multiplier))
                  translate-amt (randomize amt random-displacement)]]
      (translate (+ i translate-amt) j)
      (rotate rotate-amt)
      (rect n-half-size n-half-size
            square-size square-size)
      (reset-matrix))))


(defn setup []
  (no-loop))


(defsketch cubic-disarray
  :title "Cubic disarray"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
