-- :name save-stock-price! :<! :1
-- :doc creates a new price for a given stock using the stock id and price keys
INSERT INTO stock_prices
(stock_id, price)
VALUES (
  (:stock_id)::int,
  (:price)::int
)
RETURNING *

-- :name get-stock-prices :? :*
-- :doc selects all available stock prices
SELECT * FROM stock_prices

-- :name get-stock-prices-by-stock-id :? :*
-- :doc selects all available stock prices with given stock id
SELECT * FROM stock_prices
WHERE stock_id = (:stock_id)::int

-- :name get-stock-prices-by-stock-name :? :*
-- :doc selects all available stock prices with given stock name
SELECT stock_prices.* FROM stock_prices
INNER JOIN stocks
  ON stocks.id = stock_prices.stock_id
  AND stocks.name = (:name)::text

-- :name get-stock-price-by-stock-id :? :1
-- :doc selects the latest available stock price with given stock id
SELECT
  *
FROM stock_prices
WHERE stock_id = (:stock_id)::int
ORDER BY timestamp DESC

-- :name get-stock-price-by-stock-name :? :1
-- :doc selects the latest available stock price with given stock name
SELECT
  stock_prices.*
FROM stock_prices
INNER JOIN stocks
  ON stocks.id = stock_prices.stock_id
  AND stocks.name = (:name)::text
ORDER BY stock_prices.timestamp DESC
