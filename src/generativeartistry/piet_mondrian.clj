(ns generativeartistry.piet-mondrian
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/piet-mondrian/


(def ncells 7)
(def step 80)
(def size (* step ncells))

; colors are in ARGB format, see https://github.com/quil/quil/issues/71
(def white 0xFFF2F5F1)
(def colors [0xFFD40920 0xFF1356A2 0xFFF7D842])


(defn -split-at
  [[x y w h] ax v]
  (case ax
    :x [[x y (- v x) h]
        [v y (- w (- v x)) h]]
    :y [[x y w (- v y)]
        [x v w (- h (- v y))]]))


(defn intersects?
  [ax v [x y w h]]
  (case ax
    :x (> (+ x w) v x) 
    :y (> (+ y h) v y)))


(defn -split-with
  [squares ax v]
  (loop [to-split (filter (partial intersects? ax v) squares)
         squares squares]
    (if-let [square (first to-split)]
      (recur
       (rest to-split)
       (if (> (rand) 0.5)  ; to split or not to split
         (into (disj squares square) (-split-at square ax v))
         squares))
      squares)))


(defn make-squares []
  (loop [squares (hash-set [0 0 size size])
         lines (for [grid (range step size step)
                     axis [:x :y]]
                 [axis grid])]
    (if-let [[ax v] (first lines)]
      (recur (-split-with squares ax v)
             (rest lines))
      squares)))


(defn draw []
  (background 255)
  (stroke-weight 12)
  (let [squares (make-squares)
        colors (zipmap (repeatedly (count colors) #(rand-int (count squares)))
                       colors)]
    (doseq [[c s] (map-indexed (fn [i s] [(colors i white) s]) squares)]
      (fill c)
      (apply rect s))))


(defn setup []
  (no-loop))


(defsketch piet-mondrian
  :title "Piet Mondrian"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
