(ns vue-stocktrader-backend.schema.stock-portfolios
  (:require [schema.core :as s]))

(s/defschema StockPortfolio
  {:id s/Int
   :user_id s/Int
   :funds s/Int
   :created_at org.joda.time.DateTime
   :updated_at org.joda.time.DateTime})

(s/defschema StockPortfolioCreate
  {:user_id s/Int})

(def example-stock-portfolio
  {:id 1
   :user_id 1
   :funds 1000
   :created_at "2016-07-25T00:00:00.000Z"
   :updated_at "2016-07-25T00:00:00.000Z"})
