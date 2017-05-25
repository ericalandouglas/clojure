(ns vue-stocktrader-backend.routes.stocks
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [vue-stocktrader-backend.db.core :as db]
            [vue-stocktrader-backend.schema.stocks :as stocks-schema]))

(def stock-description
  (describe stocks-schema/Stock
            "Stock is an entity representing an available stock for purchase."
            :example stocks-schema/example-stock))


(def create-params-description
  (describe stocks-schema/StockCreate
            "Stock create parameters."
            :example (select-keys stocks-schema/example-stock
                                  [:name])))

(defroutes stocks-routes
  (context "/stocks" []
    :tags ["stocks"]

    (GET "/" []
      :return  [stock-description]
      :summary "Returns the list of stocks."
      (ok (db/get-stocks)))

    (GET "/:id" []
      :return  stock-description
      :path-params [id :- s/Int]
      :summary "Returns the stock with the given id."
      (if-let [res (db/find-stock-by-id {:id id})]
        (ok res)
        (not-found)))

    (POST "/" []
      :return  stock-description
      :body    [create-params create-params-description]
      :summary "Creates a new stock."
      (let [s (db/save-stock! create-params)]
        (created (str "/stocks/" (:id s)) s)))))
