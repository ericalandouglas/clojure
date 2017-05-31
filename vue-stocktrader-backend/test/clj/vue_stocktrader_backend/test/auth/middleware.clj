(ns vue-stocktrader-backend.test.auth.middleware
  (:require
    [clojure.test :refer :all]
    [vue-stocktrader-backend.auth.middleware :as middleware]
    [vue-stocktrader-backend.auth.token :as token]))

(deftest test-auth-middleware-helpers
  (let [secure (middleware/is-secure-route? {:request-method :get
                                             :uri "/users/1"})
        secure2 (middleware/is-secure-route? {:request-method :get
                                              :uri "/stocks"})
        permitted (middleware/user-permitted? "/users/1/test" 1)
        permitted2 (middleware/user-permitted? "/users/2/test" 1)]

    (is (and secure (not secure2)))

    (is (and permitted (not permitted2)))))

(def mock-req
  {:uri "/users/0"
   :request-method :get
   :headers {"authorization" "token"}})

(deftest test-auth-req-auth
  (testing "req authenticated? function"
    (with-redefs [token/decrypt-user-token (fn [_] {:id 0})]
      (is (middleware/req-authenticated? mock-req false)
          "returns true given valid req with token"))
    (with-redefs [token/decrypt-user-token (fn [_] nil)]
      (is (-> mock-req
              (middleware/req-authenticated? false)
              not)
          "returns false given invalid token")))

  (testing "req authorized? function"
    (is (middleware/req-authorized? mock-req {:user-id 0} false)
        "returns true given valid user id for uri")
    (is (-> mock-req
            (middleware/req-authorized? {:user-id 1} false)
            not)
        "returns false given invalid user id for uri")))
