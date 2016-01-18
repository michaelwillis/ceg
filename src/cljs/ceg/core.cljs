(ns ceg.core
  (:require [cljsjs.three]))

(def THREE js/THREE)

(.log js/console (aget js/window "innerWidth"))
(.log js/console (aget js/window "innerHeight"))
(.log js/console (/ (aget js/window "innerWidth") (aget js/window "innerHeight")))

(defonce scene (new THREE.Scene))
(defonce cam (new THREE.PerspectiveCamera 75
                  (/ (aget js/window "innerWidth")
                     (aget js/window "innerHeight"))
                  1
                  10000))

(defonce geo (new THREE.BoxGeometry 200 200 200))
(defonce mat (new THREE.MeshBasicMaterial (js-obj "color" 0xff0000 "wireframe" true)))
(defonce mesh (new THREE.Mesh geo mat))
(defonce renderer (new THREE.WebGLRenderer))

(defn animate []
  (js/requestAnimationFrame animate)
  (let [rot (aget mesh "rotation")
        x (aget rot "x")
        y (aget rot "y")]
    (aset rot "x" (+ x 0.01))
    (aset rot "y" (+ y 0.02))
    (.render renderer scene cam)))

(defn init!
  []
  (js/alert "boo")
  (aset (aget cam "position") "z" 1000)
  (.add scene mesh)
  (.setSize renderer (aget js/window "innerWidth") (aget js/window "innerHeight"))
  (.appendChild (aget js/document "body") (aget renderer "domElement"))
  (animate))
