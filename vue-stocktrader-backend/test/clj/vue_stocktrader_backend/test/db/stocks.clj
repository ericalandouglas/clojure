(ns vue-stocktrader-backend.test.db.stocks
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.db.stocks :as stocks-db]
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
        new-s (stocks-db/save-stock! test-helper/*t-conn* test-data)
        all-s (stocks-db/get-stocks test-helper/*t-conn* {})
        found-s (stocks-db/find-stock-by-id test-helper/*t-conn*
                                            (select-keys new-s [:id]))
        found-s2 (stocks-db/find-stock-by-name test-helper/*t-conn* fixture-data)
        created-s (stocks-db/create-stock! test-helper/*t-conn* (assoc fixture-data
                                                                       :price 5))]
    (is (= test-data (select-keys new-s [:name])))

    (is (= 2 (count all-s)))

    (is (= test-data (select-keys found-s [:name])))

    (is (= fixture-data (select-keys found-s2 [:name])))

    (is (and (= fixture-data (select-keys created-s [:name]))
             (= #{:id :name :price :created_at :updated_at}
                (-> created-s keys set))))))
