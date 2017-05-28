(ns vue-stocktrader-backend.test.middleware.route-validation
  (:require
    [clojure.test :refer :all]
    [vue-stocktrader-backend.middleware.route-validation :as rv-middleware]))

(deftest test-route-validation-middleware-helpers
  (let [secure (rv-middleware/is-secure-route? {:request-method :get
                                                :uri "/users/1"})
        secure2 (rv-middleware/is-secure-route? {:request-method :get
                                                 :uri "/stocks"})
        permitted (rv-middleware/user-permitted? "/users/1/test" 1)
        permitted2 (rv-middleware/user-permitted? "/users/2/test" 1)]

    (is (and secure (not secure2)))

    (is (and permitted (not permitted2)))))

