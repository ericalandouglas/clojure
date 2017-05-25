(ns vue-stocktrader-backend.test.handler
  (:require [cheshire.core :as json]
            [clojure.string :refer [ends-with?]]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vue-stocktrader-backend.db.core :as db]
            [vue-stocktrader-backend.handler :refer :all]))

(deftest test-stocks
  (testing "GET stocks route"
    (testing "returns an empty array"
      (with-redefs [db/get-stocks list]
        (let [response ((app) (request :get "/stocks"))]
          (is (= 200 (:status response)))))))

  (testing "POST stocks route"
    (with-redefs [db/save-stock! (fn [_] {:id 0})]
      (let [response ((app) (-> (request :post "/stocks")
                                (content-type "application/json")
                                (body (json/generate-string
                                        {:name "Test"}))))]
        (is (= 201 (:status response)))
        (is (ends-with? (get-in response [:headers "Location"])
                        "/stocks/0")))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
