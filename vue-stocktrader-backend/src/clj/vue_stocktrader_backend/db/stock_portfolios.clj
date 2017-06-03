(ns vue-stocktrader-backend.db.stock-portfolios
  (:require
    [clojure.java.jdbc :as jdbc]
    [clojure.tools.logging :as log]
    [conman.core :as conman]
    [vue-stocktrader-backend.db.core :refer [*db*]]
    [vue-stocktrader-backend.db.users :as users-db]))

(conman/bind-connection *db* "sql/stock_portfolios.sql")

(defn create-stock-portfolio!
  ([user-id]
   (create-stock-portfolio! *db* user-id))
  ([db-conn user-id]
   (let [user {:user_id user-id}]
     (if-let [p (find-stock-portfolio-by-user-id db-conn user)]
       p
       (let [new-p (save-stock-portfolio! db-conn user)]
         (log/info (str "new stock portfolio created with "
                        "user id: " (:user_id new-p) ", "
                        "funds: " (:funds new-p)))
         new-p)))))
