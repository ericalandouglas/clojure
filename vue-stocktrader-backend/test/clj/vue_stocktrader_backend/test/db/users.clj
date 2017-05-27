(ns vue-stocktrader-backend.test.db.users
  (:require [buddy.hashers :as hashers]
            [clojure.test :refer :all]
            [vue-stocktrader-backend.db.users :as users-db]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(use-fixtures
  :once (join-fixtures [test-helper/start-migrated-db
                        test-helper/load-fixture-data]))

(use-fixtures
  :each
  (test-helper/with-db-rollback-txn))

(deftest test-users
  (let [pw "test-pw"
        test-data {:username "test-user"
                   :email "user@test.com"
                   :password (hashers/derive pw)
                   :first_name "Mr."
                   :last_name "Test"}
        test-data-2 (assoc test-data :username "test-user-2"
                                     :email "user2@test.com"
                                     :password pw)
        new-u (users-db/save-user! test-helper/*t-conn* test-data)
        created-u (users-db/create-user! test-helper/*t-conn* test-data-2)
        authed-u (users-db/authenticate-user test-helper/*t-conn*
                                             (assoc test-data :password pw))
        found-u (users-db/find-user-by-id test-helper/*t-conn* {:id (:id new-u)})
        found-u2 (users-db/find-user-by-username test-helper/*t-conn*
                                                 {:username (:username created-u)})]

    (is (= test-data (select-keys new-u [:username :email :password :first_name :last_name])))

    (is (and (-> authed-u nil? not)
             (= (select-keys authed-u [:username :email :first_name :last_name])
                (select-keys test-data [:username :email :first_name :last_name]))))

    (is (= test-data (select-keys found-u [:username :email :password :first_name :last_name])))

    (is (= (dissoc test-data-2 :password)
           (select-keys found-u2 [:username :email :first_name :last_name])))))
