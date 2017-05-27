(ns vue-stocktrader-backend.test.helper
  (:require [cheshire.core :as json]
            [clojure.java.jdbc :as jdbc]
            [luminus-migrations.core :as migrations]
            [mount.core :as mount]
            [vue-stocktrader-backend.config :refer [env]]
            [vue-stocktrader-backend.db.core :refer [*db*]]
            [vue-stocktrader-backend.test.db.fixtures :as fixtures]))

(defn start-migrated-db [f]
  (mount/start #'vue-stocktrader-backend.config/env #'vue-stocktrader-backend.db.core/*db*)
  (migrations/migrate ["migrate"] (select-keys env [:database-url]))
  (f))

(def ^:dynamic *t-conn*)

(defn load-fixture-data [f]
  (fixtures/load! *db* fixtures/data)
  (f))


(defn with-db-rollback-txn []
  (fn [f]
    (jdbc/with-db-transaction [t-conn *db*]
      (jdbc/db-set-rollback-only! t-conn)
      (binding [*t-conn* t-conn]
        (f)))))

(defn parse-body [body]
  (when body
    (json/parse-string (slurp body) true)))
