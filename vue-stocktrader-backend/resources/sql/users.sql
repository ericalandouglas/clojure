-- :name save-user! :<! :1
-- :doc creates a new user using the required keys
INSERT INTO users
(username, password, email, first_name, last_name)
VALUES (
  (:username)::text,
  (:password)::text,
  (:email)::text,
  (:first_name)::text,
  (:last_name)::text
)
RETURNING *

-- :name update-user-pw! :<! :1
-- :doc updates an existing user's password has by id
UPDATE users
SET password = (:password)::text,
    updated_at = current_timestamp
WHERE id = (:id)::int
RETURNING *

-- :name find-user-by-id :? :1
-- :doc selects user with id if exists
SELECT * FROM users
WHERE id = (:id)::int

-- :name find-user-by-username :? :1
-- :doc selects user with username if exists
SELECT * FROM users
WHERE username = (:username)::text
