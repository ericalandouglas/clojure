-- :name save-stock-price! :<! :1
-- :doc creates a new price for a given stock using the stock id and price keys
INSERT INTO stock_prices
(stock_id, price)
VALUES (
  :stock_id,
  :price
)
RETURNING *

-- :name get-stock-prices :? :*
-- :doc selects all available stock pricess
SELECT * FROM stock_prices

-- :name get-stock-prices-by-stock-id :? :*
-- :doc selects all available stock prices with given stock id
SELECT * FROM stock_prices
WHERE stock_id = :stock_id

-- :name get-stock-prices-by-stock-name :? :*
-- :doc selects all available stock prices with given stock name
SELECT stock_prices.* FROM stock_prices
INNER JOIN stocks
  ON stocks.id = stock_prices.stock_id
  AND stocks.name = :name
