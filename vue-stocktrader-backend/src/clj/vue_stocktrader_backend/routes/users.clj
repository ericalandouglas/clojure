(ns vue-stocktrader-backend.routes.users
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [vue-stocktrader-backend.auth-token :as auth-token]
            [vue-stocktrader-backend.db.users :as users-db]
            [vue-stocktrader-backend.schema.users :as users-schema]))

(defn resource-view [u]
  (dissoc u :password))

(def user-description
  (describe users-schema/User
            "User is an entity representing a user account."
            :example (resource-view users-schema/example-user)))

(def user-auth-description
  (describe users-schema/UserAuthenticate
            "UserAuthenticate is an entity representing user creds."
            :example (select-keys users-schema/example-user
                                  [:username :password])))

(def user-token-description
  (describe users-schema/UserToken
            "UserToken is an entity representing a user's JWT access token."
            :example  {:token "user-jwt-access-token"}))

(defroutes users-routes
  (context "/" []
    :tags ["users"]

    (POST "/login" []
      :return  user-token-description
      :body    [user-params user-auth-description]
      :summary "Generates a new JWT access token for valid user credentials."
      (if-let [t (auth-token/create-user-token user-params)]
        (created "" {:token t})
        (unauthorized {:message "Invalid credentials."})))))