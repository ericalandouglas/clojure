(ns vue-stocktrader-backend.db.core
  (:require
    [conman.core :as conman]
    [mount.core :refer [defstate]]
    [vue-stocktrader-backend.config :refer [env]]
    [vue-stocktrader-backend.db.serdeser]))

(defstate ^:dynamic *db*
          :start (conman/connect! {:jdbc-url (env :database-url)})
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/stocks.sql")
(conman/bind-connection *db* "sql/stock_prices.sql")

