(ns generativeartistry.joy-division
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/joy-division/


(def size 600)
(def step 20)
(def offset 100)


(defn make-lines
  []
  (for [y (range step size step)]
    (for [x (range step size step)
          :let [center (/ size 2)
                dist-to-center (abs (- x center))
                variance (max 0 (- center offset dist-to-center))
                rnd (/ (* (rand) variance) 2)]]
      [x (- y rnd)])))


(defn draw []
  (background 255)
  (stroke-weight 2)
  (doseq [line (drop 3 (make-lines))]
    (begin-shape)
    (apply vertex (first line))
    (doseq [[[ax ay][bx by]] (partition 2 1 line)
            :let [cx (/ (+ ax bx) 2)
                  cy (/ (+ ay by) 2)]]
      (quadratic-vertex ax ay cx cy))
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
