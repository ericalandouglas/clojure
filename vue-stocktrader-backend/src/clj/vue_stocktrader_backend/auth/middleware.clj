(ns vue-stocktrader-backend.auth.middleware
  (:require
    [ring.util.response :as response]
    [vue-stocktrader-backend.auth.token :as token]))

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

(defn req-authenticated? [{:keys [headers uri] :as req} auth-disabled]
  (let [token (get headers "authorization")
        decrypted-tok (token/decrypt-user-token token)]
    (or auth-disabled
        (-> req is-secure-route? not)
        (and token decrypted-tok))))

(defn req-authorized? [{:keys [uri]} token auth-disabled]
  (or auth-disabled
      (user-permitted? uri (:user-id token))))

(defn wrap-request-auth [handler auth-disabled]
  (fn [req]
    (if-let [t (req-authenticated? req auth-disabled)]
      (if (req-authorized? req t auth-disabled)
        (handler req)
        (-> (response/response "Forbidden.")
            (response/status 403)))
      (-> (response/response "Unauthorized.")
          (response/status 401)))))
