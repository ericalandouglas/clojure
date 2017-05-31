CREATE TABLE stock_portfolios
(id SERIAL PRIMARY KEY,
 user_id INTEGER REFERENCES users (id) NOT NULL,
 funds INTEGER NOT NULL DEFAULT 10000,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
 updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp);