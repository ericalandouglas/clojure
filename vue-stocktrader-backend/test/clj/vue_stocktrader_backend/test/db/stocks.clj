(ns vue-stocktrader-backend.test.db.stocks
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.db.core :as db]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(use-fixtures
  :once (join-fixtures [test-helper/start-migrated-db
                        test-helper/load-fixture-data]))

(use-fixtures
  :each
  (test-helper/with-db-rollback-txn))

(deftest test-stocks
  (let [test-data {:name "Test"}
        fixture-data {:name "Stock Fixture"}
        new-s (db/save-stock! test-helper/*t-conn* test-data)
        all-s (db/get-stocks test-helper/*t-conn* {})
        found-s (db/find-stock-by-id test-helper/*t-conn*
                                     (select-keys new-s [:id]))
        found-s2 (db/find-stock-by-name test-helper/*t-conn* fixture-data)]
    (is (= test-data (select-keys new-s [:name])))

    (is (= 2 (count all-s)))

    (is (= test-data (select-keys found-s [:name])))

    (is (= fixture-data (select-keys found-s2 [:name])))))
