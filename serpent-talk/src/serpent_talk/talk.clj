(ns serpent-talk.talk
  (:require [camel-snake-kebab.core :as csk]))

(defn serpent-talk
  "I convert an input string to snake_case." ;; docstring
  [x]
  (str "Serpent!  You said: "
       (csk/->snake_case x)))

(defn -main [& args] ;; access variable number of args as vector
  (println (serpent-talk (first args))))
