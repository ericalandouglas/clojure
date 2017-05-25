(ns vue-stocktrader-backend.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[vue-stocktrader-backend started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[vue-stocktrader-backend has shut down successfully]=-"))
   :middleware identity})
