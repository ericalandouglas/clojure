(ns vue-stocktrader-backend.auth-token
  (:require
    [buddy.sign.jwt :as jwt]
    [buddy.core.keys :as ks]
    [clj-time.core :as t]
    [clojure.java.io :as io]
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
     (let [exp (-> (env :token-expiration-mins)
                   t/minutes
                   t/from-now)]
       (jwt/sign {:user-id id :exp exp} priv-key encrypt-opts)))))

(defn decrypt-user-token [token]
  (jwt/unsign token pub-key encrypt-opts))
