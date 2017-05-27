(ns vue-stocktrader-backend.schema.stocks
  (:require [schema.core :as s]))

(def positive?-pred
  (s/pred (fn [p]
            (every? #(% p)
                    [integer? #(> % 0)]))))

(s/defschema Stock
  {:id s/Int
   :name s/Str
   :price positive?-pred
   :created_at org.joda.time.DateTime
   :updated_at org.joda.time.DateTime})

(s/defschema StockCreate
  {:name s/Str
   :price positive?-pred})

(s/defschema StockUpdate
  {:name s/Str})

(def example-stock
  {:id 1
   :name "Google"
   :price 99
   :created_at "2016-07-25T00:00:00.000Z"
   :updated_at "2016-07-25T00:00:00.000Z"})
