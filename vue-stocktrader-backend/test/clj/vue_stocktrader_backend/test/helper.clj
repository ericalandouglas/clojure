(ns vue-stocktrader-backend.test.helper
  (:require [luminus-migrations.core :as migrations]
            [clojure.java.jdbc :as jdbc]
            [vue-stocktrader-backend.config :refer [env]]
            [vue-stocktrader-backend.db.core :refer [*db*]]
            [vue-stocktrader-backend.test.db.fixtures :as fixtures]
            [mount.core :as mount]))

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
