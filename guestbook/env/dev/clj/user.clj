(ns user
  (:refer-clojure)
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [eftest.runner :as eftest]
            [guestbook.config :refer [env load-env]]
            [guestbook.db.core]
            [luminus-migrations.core :as migrations]
            [mount.core :as mount]
            guestbook.core)
  (:use clojure.repl))

(defn run-migrations-ops [& ops]
  (mount/start #'guestbook.db.core/*db*)
  (doseq [o ops]
    (migrations/migrate [o] (select-keys env [:database-url]))))

(defn migrate []
  (run-migrations-ops "migrate"))

(defn rollback! []
  (run-migrations-ops "rollback"))

(defn reset-db! []
  (run-migrations-ops "rollback" "migrate"))

(defn start [& {:keys [migrate?] :or {migrate? false}}]
  (mount/start-without #'guestbook.core/repl-server)
  (when migrate?
    (migrate)))

(defn stop []
  (mount/stop-except #'guestbook.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn reset [& {:keys [reset-db?] :or {reset-db? false}}]
  (when reset-db?
    (reset-db!))
  (refresh)
  (restart))

(defn run-tests
  ([] (run-tests "test"))
  ([ns-or-dir]
    (stop)
    (mount/start-with-states {#'guestbook.config/env
                               {:start #(load-env :resource-path "test-config.edn")
                                :stop hash-map}})
    (eftest/run-tests (eftest/find-tests ns-or-dir)
                      {:multithread? false
                       :report eftest.report.pretty/report})
    (restart)))

