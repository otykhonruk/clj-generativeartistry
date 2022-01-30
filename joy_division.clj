(ns generativeartistry.joy-division
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/joy-division/


(def size 600)
(def step 20)
(def offset 100)
(def draw-points false)


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


(let [grid (range step size step)]
  (for [x grid y grid] [x y]))

(defn draw []
  (background 255)
  (stroke-weight 2)
  (doseq [line (drop 3 (make-lines))]
    (stroke 0)
    (begin-shape)
    (apply vertex (first line))
    (doseq [[[ax ay][bx by]] (partition 2 1 line)
            :let [cx (/ (+ ax bx) 2)
                  cy (/ (+ ay by) 2)]]
      (quadratic-vertex ax ay cx cy))
    (end-shape)
    (when draw-points
      (stroke 255 0 0)
      (doseq [[px py] line] (point px py)))))


(defn setup []
  (no-loop))


(defsketch joy-division
  :title "Joy Division"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
