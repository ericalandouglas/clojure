(ns vue-stocktrader-backend.test.routes.users
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vue-stocktrader-backend.auth-token :as auth-token]
            [vue-stocktrader-backend.db.users :as users-db]
            [vue-stocktrader-backend.handler :refer :all]
            [vue-stocktrader-backend.routes.users :refer [resource-view]]
            [vue-stocktrader-backend.schema.users :refer [example-user]]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(deftest test-users
  (testing "POST /authenticate route"
    (testing "returns a token when valid creds received"
      (with-redefs [auth-token/create-user-token (fn [_] "token")]
        (let [response ((app) (-> (request :post "/authenticate")
                                  (content-type "application/json")
                                  (body (json/generate-string
                                          {:username "test" :password "test"}))))
              token (-> response :body test-helper/parse-body :token)]
          (is (= 201 (:status response)))
          (is (= "token" token)))))
    (testing "returns a 401 when invalid creds received"
      (with-redefs [auth-token/create-user-token (fn [_] nil)]
        (let [response ((app) (-> (request :post "/authenticate")
                                  (content-type "application/json")
                                  (body (json/generate-string
                                          {:username "test" :password "test"}))))
              message (-> response :body test-helper/parse-body :message)]
          (is (= 401 (:status response)))
          (is (= "Invalid credentials." message))))))

  (testing "GET /users/:id route"
    (testing "returns a user"
      (with-redefs [users-db/find-user-by-id (fn [_] example-user)]
        (let [response ((app) (request :get "/users/1"))
              body (-> response :body test-helper/parse-body)]
          (is (= 200 (:status response)))
          (is (= (resource-view example-user) body)))))))
