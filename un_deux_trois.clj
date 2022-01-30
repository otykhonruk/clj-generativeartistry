(ns generativeartistry.un-deux-trois
  (:require [quil.core :refer :all]))


(def size 540)
(def step 40)
(def h-step (/ step 2))
(def positions
  [[0.5] 
   [0.2 0.8]
   [0.1 0.5 0.9]])


(defn draw-cell
  [x y positions]
  (translate (+ x h-step) (+ y h-step))
  (rotate (* 5 (rand)))
  (translate (- h-step) (- h-step))
  (doseq [p positions
          :let [x (* step p)]]
    (line x 0 x step))
  (reset-matrix))


(defn draw []
  (background 255)
  (stroke-weight 7)
  (let [grid (range step (- size step) step)]
    (doseq [x grid
            y grid
            :let [npos (quot (/ y size) 1/3)
                  pos (positions npos)]]
      (draw-cell x y pos))))


(defn setup []
  (no-loop))


(defsketch un-deux-trois
  :title "Un Deux Trois"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
