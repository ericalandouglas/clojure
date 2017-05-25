(ns vue-stocktrader-backend.db.serdeser
  (:require [clojure.java.jdbc :as jdbc]
            [clj-time.coerce :as tc])
  (:import [java.sql Date Timestamp]))

(extend-protocol jdbc/IResultSetReadColumn
  Date
  (result-set-read-column [v _ _] (tc/from-sql-date v))

  Timestamp
  (result-set-read-column [v _ _] (tc/from-sql-time v)))
