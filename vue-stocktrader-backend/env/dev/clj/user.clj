(ns user
  (:refer-clojure)
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [eftest.runner :as eftest]
            [vue-stocktrader-backend.config :refer [env load-env]]
            vue-stocktrader-backend.core
            [vue-stocktrader-backend.db.core :refer [*db*]]
            [vue-stocktrader-backend.test.db.fixtures :as fixtures]
            [luminus-migrations.core :as migrations]
            [mount.core :as mount])
  (:import [org.slf4j LoggerFactory]
           [ch.qos.logback.classic Logger Level])
  (:use clojure.repl))

(defn run-migrations-ops [& ops]
  (mount/start #'vue-stocktrader-backend.db.core/*db*)
  (doseq [o ops]
    (migrations/migrate [o] (select-keys env [:database-url]))))

(defn migrate []
  (run-migrations-ops "migrate"))

(defn rollback! []
  (run-migrations-ops "rollback"))

(defn reset-db! []
  (run-migrations-ops "rollback" "migrate"))

(defn load-data! []
  (mount/start #'vue-stocktrader-backend.db.core/*db*)
  (fixtures/load! *db* fixtures/data))

(defn start [& {:keys [migrate?] :or {migrate? false}}]
  (mount/start-without #'vue-stocktrader-backend.core/repl-server)
  (when migrate?
    (migrate)))

(defn stop []
  (mount/stop-except #'vue-stocktrader-backend.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn reset [& {:keys [reset-db?] :or {reset-db? false}}]
  (when reset-db?
    (reset-db!))
  (refresh)
  (restart))

(defn set-log-context [level f & args]
  (let [root-logger (LoggerFactory/getLogger Logger/ROOT_LOGGER_NAME)
        orig-level (.getLevel root-logger)]
    (.setLevel root-logger level)
    (apply f args)
    (.setLevel root-logger orig-level)))

(defn start-test-env []
  (stop)
  (mount/start-with-states {#'vue-stocktrader-backend.config/env
                             {:start #(load-env :resource-path "test.edn")
                              :stop hash-map}}))

(defn run-tests
  ([] (run-tests "test"))
  ([ns-or-dir]
    (let [test-f (fn []
                   (start-test-env)
                   (eftest/run-tests (eftest/find-tests ns-or-dir)
                                     {:multithread? false
                                      :report eftest.report.pretty/report})
                   (restart))]
      (set-log-context Level/OFF test-f))))

