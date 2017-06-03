(ns
  vue-stocktrader-backend.test.db.fixtures
  "Provides functions for loading and unloading fixtures into the DB. Lifted from the
  fixtures-component project at: https://github.com/banzai-inc/fixtures-component
  Protocols from that project are removed. These functions are specific to
  PostgreSQL.
  Provided data should in be format:
      (def data [[:users [{:id 1 :username \"demo1@example.com\"}
                          {:id 2 :username \"demo2@example.com\"}]]
                 [:phones [{:id 1 :user_id 1 :text \"555-383-9999\"}
                           {:id 2 :user_id 2 :text \"555-898-2222\"}]]])
  "
  (:require
    [buddy.hashers :as hashers]
    [clj-time.core :as t]
    [clj-time.jdbc]
    [clojure.java.jdbc :refer [insert-multi! delete!]]))

(defn- load-table! [spec table records]
  (insert-multi! spec table records))

(defn- clear-table! [spec table]
  (delete! spec table []))

(defn- unload* [spec data]
  (try
    (doseq [[table _] (reverse data)]
      (clear-table! spec table))
    (catch Exception e
      (println (.getMessage e)))))

(defn load!
  [spec data]
  (try
    (unload* spec data) ;; in case the system failed to unload properly on shutdown
    (doseq [[table records] data]
      (when (not (empty? records))
        (load-table! spec table records)))
    (catch Exception e
      (println (.getMessage e)))))

(defn unload!
  [spec data]
  (unload* spec data))

(def data [[:stocks
            [{:id 0
              :name "Stock Fixture"
              :created_at (t/date-time 2015 12 25)
              :updated_at (t/date-time 2015 12 25)}]]
           [:stock_prices
            [{:id 0
              :stock_id 0
              :price 99
              :timestamp (t/date-time 2015 12 25)}]]
           [:users
            [{:id 0
              :username "fixture-user"
              :password (hashers/encrypt "fixture-pw")
              :email "user@fixture.com"
              :first_name "Mr."
              :last_name "Fixture"
              :created_at (t/date-time 2015 12 25)
              :updated_at (t/date-time 2015 12 25)}]]
           [:stock_portfolios
            [{:id 0
              :user_id 0
              :funds 9999
              :created_at (t/date-time 2015 12 25)
              :updated_at (t/date-time 2015 12 25)}]]])
