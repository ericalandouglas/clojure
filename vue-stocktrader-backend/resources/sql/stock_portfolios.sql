-- :name save-stock-portfolio! :<! :1
-- :doc creates a new stock portfolio using the required keys
INSERT INTO stock_portfolios
(user_id)
VALUES (
  (:user_id)::int
)
RETURNING *

-- :name update-users-stock-portfolio-funds! :<! :1
-- :doc updates an existing stock portfolio's funds by user id
UPDATE stock_portfolios
SET funds = (:funds)::int,
    updated_at = current_timestamp
WHERE user_id = (:user_id)::int
RETURNING *

-- :name find-stock-portfolio-by-id :? :1
-- :doc selects stock portfolio with id if exists
SELECT * FROM stock_portfolios
WHERE id = (:id)::int

-- :name find-stock-portfolio-by-user-id :? :1
-- :doc selects stock portfolio with user id if exists
SELECT * FROM stock_portfolios
WHERE user_id = (:user_id)::int
