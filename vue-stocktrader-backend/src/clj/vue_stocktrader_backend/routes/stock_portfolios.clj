(ns vue-stocktrader-backend.routes.stock-portfolios
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [vue-stocktrader-backend.db.stock-portfolios :as stock-portfolios-db]
            [vue-stocktrader-backend.schema.stock-portfolios :as stock-portfolios-schema]))

(def stock-portfolio-description
  (describe stock-portfolios-schema/StockPortfolio
            "StockPortfolio is an entity representing a user's stocks and funds."
            :example stock-portfolios-schema/example-stock-portfolio))

(def create-params-description
  (describe stock-portfolios-schema/StockPortfolioCreate
            "StockPortfolio create parameters."
            :example (select-keys stock-portfolios-schema/example-stock-portfolio
                                  [:user_id])))

(defroutes stock-portfolios-routes
  (context "/stock-portfolios" []
    :tags ["stock-portfolios"]

    (GET "/:id" []
      :return  stock-portfolio-description
      :path-params [id :- s/Int]
      :summary "Returns the stock portfolio with the given id."
      (if-let [res (stock-portfolios-db/find-stock-portfolio-by-id {:id id})]
        (ok res)
        (not-found))))

  (context "/users" []
    :tags ["stock-portfolios"]

    (GET "/:id/stock-portfolio" []
      :return  stock-portfolio-description
      :path-params [id :- s/Int]
      :summary "Returns the stock portfolio with the given user id."
      (if-let [res (stock-portfolios-db/find-stock-portfolio-by-user-id
                     {:user_id id})]
        (ok res)
        (not-found)))

    (POST "/:id/stock-portfolio" []
      :return  stock-portfolio-description
      :path-params [id :- s/Int]
      :body    [create-params create-params-description]
      :summary "Creates a new stock portfolio with the given user id if one does not exist."
      (let [sp (stock-portfolios-db/create-stock-portfolio! create-params)]
        (created (str "/stock-portfolios/" (:id sp)) sp)))))
