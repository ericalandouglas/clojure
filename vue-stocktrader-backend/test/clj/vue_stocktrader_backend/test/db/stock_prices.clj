(ns vue-stocktrader-backend.test.db.stock_prices
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.db.stocks :as stocks-db]
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
                  (stocks-db/find-stock-by-name test-helper/*t-conn*)
                  :id)
        test-data {:price 10 :stock_id s-id}
        new-p (stocks-db/save-stock-price! test-helper/*t-conn* test-data)
        new-p2 (stocks-db/save-stock-price! test-helper/*t-conn* test-data)
        all-ps (stocks-db/get-stock-prices test-helper/*t-conn* {})
        one-sps (stocks-db/get-stock-prices-by-stock-id test-helper/*t-conn* {:stock_id s-id})
        one-sps2 (stocks-db/get-stock-prices-by-stock-name test-helper/*t-conn* fixture-data)
        sp (stocks-db/get-stock-price-by-stock-id test-helper/*t-conn* {:stock_id s-id})
        sp2 (stocks-db/get-stock-price-by-stock-name test-helper/*t-conn* fixture-data)]
    (is (every? #(= test-data %)
                (map #(select-keys % [:stock_id :price])
                     [new-p new-p2])))

    (is (and (every? #(= #{:id :price :stock_id :timestamp} (set %))
                     (map keys all-ps))
             (= 3 (count all-ps))))

    (is (and (every? #(= s-id %) (map :stock_id one-sps))
             (= 3 (count one-sps))))

    (is (and (every? #(= s-id %) (map :stock_id one-sps2))
             (= 3 (count one-sps))))

    (is (= test-data (select-keys sp [:stock_id :price])))

    (is (= test-data (select-keys sp2 [:stock_id :price])))))
