(ns ceg.core
  (:require [cljsjs.three]))

(def THREE js/THREE)

(defonce canvas (.getElementById js/document "canvas"))
(defonce scene (new THREE.Scene))
(defonce cam (new THREE.PerspectiveCamera 75
                  (/ (aget js/window "innerWidth")
                     (aget js/window "innerHeight"))
                  1
                  10000))

(defonce geo (new THREE.BoxGeometry 200 200 200))
(defonce mat (new THREE.MeshBasicMaterial (js-obj "color" 0xff0000 "wireframe" true)))
(defonce mesh (new THREE.Mesh geo mat))
(defonce renderer (new THREE.WebGLRenderer (js-obj {"canvas" canvas})))

(defn animate []
  (js/requestAnimationFrame animate)
  (let [rot (aget mesh "rotation")
        x (aget rot "x")
        y (aget rot "y")]
    (aset rot "x" (+ x 0.01))
    (aset rot "y" (+ y 0.02))
    (.render renderer scene cam)))

(defn resize []
  (let [screen-width  (aget js/window "innerWidth")
        screen-height (aget js/window "innerHeight")
        render-width  (/ screen-width  2)
        render-height (/ screen-height 2)]
    (.setSize renderer render-width render-height false)
    (aset cam "aspect" (/ render-width render-height))
    (.updateProjectionMatrix cam)))

(defn init!
  []
  (aset (aget cam "position") "z" 1000)
  (.add scene mesh)
  (resize)

  (.appendChild (aget js/document "body") (aget renderer "domElement"))
  (animate))

(aset js/window "onresize" resize)
