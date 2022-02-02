(ns generativeartistry.circle-packing
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/circle-packing/

(def max-cycles 9999)
(def max-radius 100)
(def min-radius 2)


(defn maximize-radius
  "Calculates max radius of the circle with the center at (x, y)
  that do not intersects with the circle (tx, ty) and radius tr"
  [[tx ty tr] x y]
  (let [dx (- tx x)
        dy (- ty y)
        d  (sqrt (+ (* dx dx) (* dy dy)))]
    (- d tr)))


(defn next-circle
  "Generate the biggest possible circle at a random position
  that can be placed into the field of the existing circles"
  [circles]
  (let [w (width)
        h (height)
        x (random w)
        y (random h)
        dx (- w x)
        dy (- h y)
        ;; max radius given other circles
        r (reduce (fn [acc c]
                    (let [p (maximize-radius c x y)]
                      (if (< p 0)
                        (reduced p)  ; stop, if center is inside existing circle
                        (min acc p))))
                  max-radius
                  circles)
        r (min r x y dx dy max-radius)]
    (when (> r min-radius)
      [x y r])))


(defn gen-circles []
  (loop [circles [] cycles 0]
    (if (>= cycles max-cycles)
      circles
      (recur (if-let [next (next-circle circles)]
               (conj circles next)
               circles)
             (inc cycles)))))


(defn draw []
  (no-loop)
  (background 255)
  (stroke-weight 2)
  (ellipse-mode :radius)
  (doseq [[x y r] (gen-circles)]
    (ellipse x y r r)))


(defn setup []
  (no-loop))


(defsketch circle-packing
  :title "Circle Packing"
  :size [500 500]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
