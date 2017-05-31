(ns vue-stocktrader-backend.test.auth.token
  (:require [clojure.test :refer :all]
            [vue-stocktrader-backend.auth.token :as token]
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
        tok (token/create-user-token test-helper/*t-conn* test-data)
        decrypt-t (token/decrypt-user-token tok)
        decrypt-t2 (token/decrypt-user-token "junk")]

    (is (-> tok nil? not))

    (is (= (:user-id decrypt-t) 0))

    (is (not decrypt-t2))))
