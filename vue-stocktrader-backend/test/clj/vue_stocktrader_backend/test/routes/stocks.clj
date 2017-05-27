(ns vue-stocktrader-backend.test.routes.stocks
  (:require [cheshire.core :as json]
            [clojure.string :refer [ends-with?]]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vue-stocktrader-backend.db.stocks :as stocks-db]
            [vue-stocktrader-backend.schema.stocks :refer [example-stock]]
            [vue-stocktrader-backend.handler :refer :all]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(deftest test-stocks
  (testing "GET /stocks route"
    (testing "returns a list of stocks"
      (with-redefs [stocks-db/get-stocks-with-price #(list example-stock)]
        (let [response ((app) (request :get "/stocks"))
              body (-> response :body test-helper/parse-body)]
          (is (= 200 (:status response)))
          (is (= (list example-stock) body))))))

  (testing "GET /stocks/:id route"
    (testing "returns a stock"
      (with-redefs [stocks-db/find-stock-by-id (fn [_] (dissoc example-stock :price))
                    stocks-db/get-stock-price-by-stock-id (fn [_] (select-keys example-stock
                                                                        [:price]))]
        (let [response ((app) (request :get "/stocks/1"))
              body (-> response :body test-helper/parse-body)]
          (is (= 200 (:status response)))
          (is (= example-stock body))))))

  (testing "POST /stocks route"
    (testing "returns the created entity"
      (with-redefs [stocks-db/create-stock! (fn [_] example-stock)]
        (let [response ((app) (-> (request :post "/stocks")
                                  (content-type "application/json")
                                  (body (json/generate-string
                                          {:name "Test" :price 5}))))
              body (-> response :body test-helper/parse-body)]
          (is (= 201 (:status response)))
          (is (= body example-stock))
          (is (ends-with? (get-in response [:headers "Location"])
                          (str "/stocks/" (:id example-stock))))))))

  (testing "not-found route"
    (let [response ((app) (request :get "/invalid"))]
      (is (= 404 (:status response))))))
