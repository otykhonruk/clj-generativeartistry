(ns generativeartistry.triangular-mesh
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/triangular-mesh/


(def size 520)
(def gap (/ size 7))
(def gap2 (/ gap 2))

(def enumerate (partial map-indexed vector))


;; (Math.random()*.8 - .4) * gap
(defn random-gap []
  (* gap (- (* (rand) 0.8) 0.4)))


(defn random-color []
  (* 16 (floor (* 16 (rand)))))


(defn grid []
  "Generate dots"
  (let [grid (range gap2 size gap)]
    (for [[i y] (enumerate grid)]
      (for [x grid
            :let [x (+ x (random-gap) (if (odd? i) gap2 0))
                  y (+ y (random-gap))]]
        [x y]))))


(defn mesh [grid]
  "Create triangulated mesh from grid"
  (->> (partition 2 1 grid)
       (enumerate)
       (map (fn [[i [a b]]]
              (apply interleave (if (odd? i) [b a] [a b]))))
       (mapcat #(partition 3 1 %))))


(defn draw []
  (background 255)
  ; (stroke-weight 2)
  (doseq [t (mesh (grid))]
    (fill (random-color))
    (apply triangle (flatten t))))


(defn setup []
  (no-loop))


(defsketch triangular-mesh
  :title "Triangular mesh"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
