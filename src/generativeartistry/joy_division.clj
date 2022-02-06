(ns generativeartistry.joy-division
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/joy-division/


(def size 600)
(def step 20)
(def offset 100)


(defn make-lines
  []
  (let [grid (range step size step)
        center (/ size 2)]
    (for [y grid]
      (for [x grid
            :let [dist-to-center (abs (- x center))
                  variance (max 0 (- center offset dist-to-center))
                  rnd (/ (* (rand) variance) 2)]]
        [x (- y rnd)]))))


(defn draw []
  (background 255)
  (stroke-weight 2)
  (doseq [line (drop 3 (make-lines))]
    (stroke 0)
    (begin-shape)
    (apply vertex (first line))
    (doseq [[[ax ay][bx by]] (partition 2 1 line)
            :let [cx (lerp ax bx 0.5)
                  cy (lerp ay by 0.5)]]
      (quadratic-vertex ax ay cx cy))
    (apply vertex (last line))
    (end-shape)))


(defn setup []
  (no-loop))


(defsketch joy-division
  :title "Joy Division"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
