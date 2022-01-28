(ns generativeartistry.tiled-lines
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/tiled-lines/

(defn setup []
  (no-loop))


(defn grid
  [w h]
  (for [x (range w)
        y (range h)]
    [x y]))


(defn draw-cell
  [x y cellsize]
  (let [ox (* x cellsize) 
        oy (* y cellsize)
        x (+ ox cellsize)
        y (+ oy cellsize)]
    (if (zero? (rand-int 2))
      (line ox oy x y)
      (line ox y x oy))))


(defn draw-grid
  [grid cellsize]
  (doseq [[x y] grid]
    (draw-cell x y cellsize)))


(defn draw []
  (background 248)
  (stroke-weight 2)
  (doseq [[x y] (grid 50 50)]
    (draw-cell x y 15)))


(defsketch tiled-lines
  :title "Tiled lines"
  :size [750 750]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
