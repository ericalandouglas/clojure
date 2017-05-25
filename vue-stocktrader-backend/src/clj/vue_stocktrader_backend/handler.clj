(ns vue-stocktrader-backend.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [vue-stocktrader-backend.layout :refer [error-page]]
            [vue-stocktrader-backend.routes.home :refer [home-routes]]
            [vue-stocktrader-backend.routes.core :refer [service-routes]]
            [compojure.route :as route]
            [vue-stocktrader-backend.env :refer [defaults]]
            [mount.core :as mount]
            [vue-stocktrader-backend.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
