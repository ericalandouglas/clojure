(ns vue-stocktrader-backend.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [vue-stocktrader-backend.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[vue-stocktrader-backend started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[vue-stocktrader-backend has shut down successfully]=-"))
   :middleware wrap-dev})
