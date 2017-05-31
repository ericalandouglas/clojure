(ns vue-stocktrader-backend.db.users
  (:require
    [buddy.hashers :as hashers]
    [clojure.java.jdbc :as jdbc]
    [clojure.tools.logging :as log]
    [conman.core :as conman]
    [vue-stocktrader-backend.db.core :refer [*db*]]))

(conman/bind-connection *db* "sql/users.sql")

(defn create-user!
  ([user]
   (create-user! *db* user))
  ([db-conn user]
   (let [u-data (update user :password hashers/derive)
         u (save-user! db-conn u-data)]
     (log/info (str "new user created with "
                    "id: " (:id u) ", "
                    "username: " (:username u) ", "
                    "email: " (:email u)))
     u)))

(defn authenticate-user
  ([user]
   (authenticate-user *db* user))
  ([db-conn {:keys [password] :as user}]
   (let [u (find-user-by-username db-conn user)]
     (when (hashers/check password (:password u))
       (dissoc u :password)))))
