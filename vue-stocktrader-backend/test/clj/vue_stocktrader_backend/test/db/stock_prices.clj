(ns vue-stocktrader-backend.test.db.stock_prices
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.db.core :as db]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(use-fixtures
  :once (join-fixtures [test-helper/start-migrated-db
                        test-helper/load-fixture-data]))

(use-fixtures
  :each
  (test-helper/with-db-rollback-txn))

(deftest test-stock-prices
  (let [fixture-data {:name "Stock Fixture"}
        s-id (->> fixture-data
                  (db/find-stock-by-name test-helper/*t-conn*)
                  :id)
        test-data {:price 10 :stock_id s-id}
        new-p (db/save-stock-price! test-helper/*t-conn* test-data)
        new-p2 (db/save-stock-price! test-helper/*t-conn* test-data)
        all-ps (db/get-stock-prices test-helper/*t-conn* {})
        one-sps (db/get-stock-prices-by-stock-id test-helper/*t-conn* {:stock_id s-id})
        one-sps2 (db/get-stock-prices-by-stock-name test-helper/*t-conn* fixture-data)]
    (is (every? #(= test-data %)
                (map #(select-keys % [:stock_id :price])
                     [new-p new-p2])))

    (is (and (= test-data
                (-> all-ps
                    first
                    (select-keys [:stock_id :price])))
             (= 2 (count all-ps))))

    (is (and (= test-data
                (-> one-sps
                    first
                    (select-keys [:stock_id :price])))
             (= 2 (count one-sps))))

    (is (and (= test-data
                (-> one-sps2
                    first
                    (select-keys [:stock_id :price])))
             (= 2 (count one-sps))))))
