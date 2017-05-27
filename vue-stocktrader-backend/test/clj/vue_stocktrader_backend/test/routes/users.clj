(ns vue-stocktrader-backend.test.routes.users
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vue-stocktrader-backend.db.users :as users-db]
            [vue-stocktrader-backend.auth-token :as auth-token]
            [vue-stocktrader-backend.schema.users :refer [example-user]]
            [vue-stocktrader-backend.handler :refer :all]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(deftest test-users
  (testing "POST /login route"
    (testing "returns a token when valid creds received"
      (with-redefs [auth-token/create-user-token (fn [_] "token")]
        (let [response ((app) (-> (request :post "/login")
                                  (content-type "application/json")
                                  (body (json/generate-string
                                          {:username "test" :password "test"}))))
              body (-> response :body test-helper/parse-body)]
          (is (= 201 (:status response)))
          (is (= "token" (:token body))))))))
