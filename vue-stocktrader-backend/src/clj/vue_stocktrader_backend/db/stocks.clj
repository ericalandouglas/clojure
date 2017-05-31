(ns vue-stocktrader-backend.db.stocks
  (:require
    [clojure.java.jdbc :as jdbc]
    [clojure.tools.logging :as log]
    [conman.core :as conman]
    [vue-stocktrader-backend.db.core :refer [*db*]]))

(conman/bind-connection *db* "sql/stocks.sql")
(conman/bind-connection *db* "sql/stock_prices.sql")

(defn create-stock!
  ([stock]
   (create-stock! *db* stock))
  ([db-conn {:keys [name price]}]
   (jdbc/with-db-transaction [t-conn db-conn]
     (let [name-payload {:name name}
           create-stock-price! (fn [{:keys [id]}]
                                 (save-stock-price! t-conn {:stock_id id
                                                            :price price}))
           add-stock-price! (fn [stock]
                              (let [{:keys [price]} (create-stock-price! stock)]
                                (assoc stock :price price)))]
       (if-let [{:keys [id] :as s} (find-stock-by-name t-conn name-payload)]
         (do
           (log/info (str "adding stock price of " price
                          " to stock " name " with id " id))
           (add-stock-price! s))
         (let [new-s (save-stock! t-conn name-payload)
               {:keys [id] :as priced-s} (add-stock-price! new-s)]
           (log/info (str "created new stock with price:" price
                          ", name:" name ", and id:" id))
           priced-s))))))
