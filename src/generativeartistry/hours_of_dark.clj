(ns generativeartistry.hours-of-dark
  (:require [quil.core :refer :all]))

;; https://generativeartistry.com/tutorials/hours-of-dark/

(def size 500)

(def cols 23)
(def rows 16)
(def days 365)

(def gridw (* size 0.9))
(def gridh (* size 0.7))
(def cellw (/ gridw cols))
(def cellh (/ gridh rows))
(def margx  (* 0.5 (- size gridw)))
(def margy  (* 0.5 (- size gridh)))

(def w 2)
(def h 30)

(defn draw []
  (background 255)
  (fill 0)
  (dotimes [i days]
    (let [col (quot i rows)
          row (rem i rows)
          x (+ margx (* col cellw))
          y (+ margy (* row cellh))
          phi (* PI (/ i days))
          theta (+ (* PI 0.45 (sin phi)) 0.85)
          scale- (+ 1 (* 2 (abs (cos phi))))]
      (translate x y)
      (clip 0 0 cellw cellh)
      (translate (* 0.5 cellw) (* 0.5 cellh))
      (rotate theta)
      (scale scale- 1)
      (rect (* -0.5 w) (* -0.5 h) w h)
      (reset-matrix))))


(defn setup []
  (no-loop))


(defsketch hours-of-dark
  :title "Hours of Dark"
  :size [size size]
  :setup setup
  :draw draw
  :settings #(pixel-density (display-density))
  :features [:keep-on-top])
