(ns vue-stocktrader-backend.middleware.route-validation
  (:require
    [ring.util.response :as response]
    [vue-stocktrader-backend.auth-token :as auth-token]
    [vue-stocktrader-backend.config :refer [env]]))

(def secure-routes
  [[:post #"/stocks"]
   [:get #"/users/[0-9]+"]])

(defn is-secure-route? [{:keys [request-method uri]}]
  (let [is-match? (fn [[method path-regex]]
                    (and (= method request-method)
                         (re-matches path-regex uri)))]
    (some is-match? secure-routes)))

(defn user-permitted? [uri user-id]
  (let [uri-user-id (->> uri
                         (re-matches #"/users/([0-9]+)(/.+)?")
                         second
                         (#(when % (Integer/parseInt %))))]
    (or (nil? uri-user-id) (= uri-user-id user-id))))

(defn req-authenticated? [{:keys [headers uri] :as req}]
  (let [token (get headers "authorization")
        {:keys [id]} (auth-token/valid-user-token? token)]
    (or (:dangerous-disable-auth env)
        (-> req is-secure-route? not)
        (and token id))))

(defn req-authorized? [{:keys [uri]} user-id]
  (or (:dangerous-disable-auth env)
      (user-permitted? uri user-id)))

(defn wrap-token-validation [handler]
  (fn [req]
    (if-let [user-id (req-authenticated? req)]
      (if (req-authorized? req user-id)
        (handler req)
        (-> (response/response "Forbidden.")
            (response/status 403)))
      (-> (response/response "Unauthorized.")
          (response/status 401)))))
