(ns vue-stocktrader-backend.test.db.stock-portfolios
  (:require [buddy.hashers :as hashers]
            [clojure.test :refer :all]
            [vue-stocktrader-backend.db.stock-portfolios :as stock-portfolios-db]
            [vue-stocktrader-backend.db.users :as users-db]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(use-fixtures
  :once (join-fixtures [test-helper/start-migrated-db
                        test-helper/load-fixture-data]))

(use-fixtures
  :each
  (test-helper/with-db-rollback-txn))

(deftest test-stock-portfolios
  (let [user-test-data {:username "test-user"
                        :email "user@test.com"
                        :password (hashers/derive "test")
                        :first_name "Mr."
                        :last_name "Test"}
        {:keys [id] :as new-u} (users-db/save-user! test-helper/*t-conn* user-test-data)
        test-data {:user_id id}
        new-sp (stock-portfolios-db/save-stock-portfolio! test-helper/*t-conn* test-data)
        created-sp (stock-portfolios-db/create-stock-portfolio! test-helper/*t-conn* id)
        fund-data (assoc test-data :funds 999)
        updated-sp (stock-portfolios-db/update-users-stock-portfolio-funds! test-helper/*t-conn*
                                                                            fund-data)
        found-sp (stock-portfolios-db/find-stock-portfolio-by-id test-helper/*t-conn* new-sp)
        found-sp2 (stock-portfolios-db/find-stock-portfolio-by-user-id test-helper/*t-conn* {:user_id id})]

    (is (= (select-keys new-sp [:funds :user_id]) (assoc test-data :funds 10000)))

    (is (= new-sp created-sp))

    (is (= updated-sp (assoc created-sp :funds 999)))

    (is (= (select-keys found-sp [:user_id :funds]) fund-data))

    (is (= (select-keys found-sp2 [:user_id :funds]) fund-data))))
