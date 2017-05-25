CREATE TABLE stocks
(id SERIAL PRIMARY KEY,
 name TEXT UNIQUE NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
 updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp);