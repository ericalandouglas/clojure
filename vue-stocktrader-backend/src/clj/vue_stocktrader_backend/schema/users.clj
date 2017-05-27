(ns vue-stocktrader-backend.schema.users
  (:require [schema.core :as s]))

(s/defschema User
  {:id s/Int
   :username s/Str
   :email s/Str
   :first_name s/Str
   :last_name s/Str
   :created_at org.joda.time.DateTime
   :updated_at org.joda.time.DateTime})

(s/defschema UserAuthenticate
  {:username s/Str
   :password s/Str})

(s/defschema UserToken
  {:token s/Str})

(def example-user
  {:id 1
   :username "example-user"
   :password "example-pw"
   :email "user@example.com"
   :first_name "Mr."
   :last_name "Example"
   :created_at "2016-07-25T00:00:00.000Z"
   :updated_at "2016-07-25T00:00:00.000Z"})
