(ns vue-stocktrader-backend.routes.core
  (:require [compojure.api.sweet :refer :all]
            [vue-stocktrader-backend.routes.stocks :refer [stocks-routes]]
            [vue-stocktrader-backend.routes.services :refer [sample-routes]]
            [vue-stocktrader-backend.routes.users :refer [users-routes]]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Stocktrader API"
                           :description "Backend for a vuejs stocktrader UI"}}}}

  stocks-routes
  sample-routes
  users-routes)
