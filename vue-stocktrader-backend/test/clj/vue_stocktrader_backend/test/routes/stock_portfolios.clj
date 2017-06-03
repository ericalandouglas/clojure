(ns vue-stocktrader-backend.test.routes.stock-portfolios
  (:require [cheshire.core :as json]
            [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [vue-stocktrader-backend.db.stock-portfolios :as stock-portfolios-db]
            [vue-stocktrader-backend.handler :refer :all]
            [vue-stocktrader-backend.schema.stock-portfolios :refer [example-stock-portfolio]]
            [vue-stocktrader-backend.test.helper :as test-helper]))

(deftest test-stock-portfolio-routes
  (testing "GET /stock-portfolios/:id route"
    (testing "returns a stock portfolio"
      (with-redefs [stock-portfolios-db/find-stock-portfolio-by-id (fn [_] example-stock-portfolio)]
        (let [response ((app) (request :get "/stock-portfolios/1"))
              body (-> response :body test-helper/parse-body)]
          (is (= 200 (:status response)))
          (is (= example-stock-portfolio body))))))

  (testing "GET /users/:id/stock-portfolio route"
    (testing "returns a stock portfolio"
      (with-redefs [stock-portfolios-db/find-stock-portfolio-by-user-id (fn [_] example-stock-portfolio)]
        (let [response ((app) (request :get "/users/1/stock-portfolio"))
              body (-> response :body test-helper/parse-body)]
          (is (= 200 (:status response)))
          (is (= example-stock-portfolio body)))))))
