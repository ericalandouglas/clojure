CREATE TABLE stock_prices
(id SERIAL PRIMARY KEY,
 stock_id INTEGER REFERENCES stocks (id) NOT NULL,
 price INTEGER NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
 updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp);
