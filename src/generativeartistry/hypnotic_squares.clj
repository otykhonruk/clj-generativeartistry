(ns generativeartistry.hypnotic-squares
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/hypnotic-squares/

;; NB: I ignored offset from original tutorial for simplicity.

(def tile-step 50)
(def start-size tile-step)
(def final-size 6)
(def directions [-1 0 1])

(def size (* 7 tile-step))


(defn draw-cell
  [x y w h xm ym steps start-steps]
  (rect x y w h)
  (when (>= steps 0)
    (let [new-size (+ final-size (* start-size (/ steps start-steps)))
          new-x (+ x (/ (- w new-size) 2))
          new-y (+ y (/ (- h new-size) 2))
          new-x (- new-x (* xm (/ (- x new-x) (+ 2 steps))))
          new-y (- new-y (* ym (/ (- y new-y) (+ 2 steps))))]
      (println steps new-size new-x new-y)
      (recur new-x new-y new-size new-size xm ym (dec steps) start-steps))))


(defn draw []
  (background 255)
  (stroke-weight 2)
  (no-fill)
  (let [grid (range 0 size tile-step)]
    (doseq [y grid
            x grid
            :let [start-steps (ceil (random 2 6))
                  xd (rand-nth directions)
                  yd (rand-nth directions)]]
      (draw-cell x y start-size start-size xd yd (dec start-steps) start-steps))))


(defn setup []
  (no-loop))


(defsketch hypnotic-squares
  :title "Hypnotic Squares"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
