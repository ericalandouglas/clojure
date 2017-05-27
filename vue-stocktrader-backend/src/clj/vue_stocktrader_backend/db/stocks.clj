(ns vue-stocktrader-backend.db.stocks
  (:require
    [clojure.java.jdbc :as jdbc]
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
       (if-let [s (find-stock-by-name t-conn name-payload)]
         (add-stock-price! s)
         (let [new-s (save-stock! t-conn name-payload)]
           (add-stock-price! new-s)))))))
