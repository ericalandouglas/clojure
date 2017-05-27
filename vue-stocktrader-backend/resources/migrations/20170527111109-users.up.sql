CREATE TABLE users
(id SERIAL PRIMARY KEY,
 username TEXT UNIQUE NOT NULL,
 password TEXT NOT NULL,
 email TEXT NOT NULL UNIQUE,
 first_name TEXT NOT NULL,
 last_name TEXT NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
 updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp);
