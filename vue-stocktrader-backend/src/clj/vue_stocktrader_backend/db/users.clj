(ns vue-stocktrader-backend.db.users
  (:require
    [buddy.hashers :as hashers]
    [clojure.java.jdbc :as jdbc]
    [conman.core :as conman]
    [vue-stocktrader-backend.db.core :refer [*db*]]))

(conman/bind-connection *db* "sql/users.sql")

(defn create-user!
  ([user]
   (create-user! *db* user))

  ([db-conn user]
   (save-user! db-conn (update user :password hashers/derive))))

(defn authenticate-user
  ([user]
   (authenticate-user *db* user))
  ([db-conn {:keys [password] :as user}]
   (let [u (find-user-by-username db-conn user)]
     (when (hashers/check password (:password u))
       (dissoc u :password)))))
