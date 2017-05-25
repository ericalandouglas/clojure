-- :name save-stock! :<! :1
-- :doc creates a new stock using the name keys
INSERT INTO stocks
(name)
VALUES (
  (:name)::text
)
RETURNING *

-- :name get-stocks :? :*
-- :doc selects all available stocks
SELECT * FROM stocks

-- :name find-stock-by-id :? :1
-- :doc selects stock with id if exists
SELECT * FROM stocks
WHERE id = (:id)::int

-- :name find-stock-by-name :? :1
-- :doc selects stock with name if exists
SELECT * FROM stocks
WHERE name = (:name)::text

-- :name get-stocks-with-price :? :*
-- :doc selects all available stocks with latest price
SELECT DISTINCT ON (stocks.name)
  stocks.*,
  stock_prices.price
FROM stocks
INNER JOIN stock_prices
  ON stock_prices.stock_id = stocks.id
ORDER BY stocks.name, stock_prices.created_at DESC
