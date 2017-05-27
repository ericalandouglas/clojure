(ns vue-stocktrader-backend.test.auth-token
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.auth-token :as auth-token]
            [vue-stocktrader-backend.test.helper :as test-helper]))


(use-fixtures
  :once (join-fixtures [test-helper/start-migrated-db
                        test-helper/load-fixture-data]))

(use-fixtures
  :each
  (test-helper/with-db-rollback-txn))

(deftest test-auth-tokens
  (let [test-data {:username "fixture-user"
                   :password "fixture-pw"}
        tok (auth-token/create-user-token test-helper/*t-conn* test-data)
        decrypt-u (auth-token/decrypt-user-token tok)]

    (is (-> tok nil? not))

    (is (= (:user-id decrypt-u) 0))))
