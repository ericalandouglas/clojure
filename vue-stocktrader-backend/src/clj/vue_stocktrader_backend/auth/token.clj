(ns vue-stocktrader-backend.auth.token
  (:require
    [buddy.sign.jwt :as jwt]
    [buddy.core.keys :as ks]
    [clj-time.core :as t]
    [clj-time.coerce :as tc]
    [clojure.java.io :as io]
    [clojure.tools.logging :as log]
    [vue-stocktrader-backend.config :refer [env]]
    [vue-stocktrader-backend.db.core :refer [*db*]]
    [vue-stocktrader-backend.db.users :as users-db]))

(def priv-key (ks/private-key "resources/keys/ecprivkey.pem"))
(def pub-key (ks/public-key "resources/keys/ecpubkey.pem"))
(def encrypt-opts {:alg :es256})

(defn create-user-token
  ([user]
   (create-user-token *db* user))
  ([db-conn user]
   (when-let [{:keys [id]} (users-db/authenticate-user db-conn
                                                       user)]
     (let [exp (-> (:token-expiration-mins env)
                   t/minutes
                   t/from-now)
           token {:user-id id :exp exp}]
       (log/info (str "created user token with "
                      "id: " id ", exp: " exp))
       (jwt/sign token priv-key encrypt-opts)))))

(defn decrypt-user-token [token]
  (try
    (jwt/unsign token pub-key encrypt-opts)
    (catch Exception e
      nil)))
