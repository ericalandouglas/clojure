(ns ring-app.core
  (:require [compojure.core :refer [GET POST
                                    context defroutes routes]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.http-response :as response]))

(defn response-handler [req]
  (response/ok
    (str "<html><body>"
         "Your ip: " (:remote-addr req)
         "</body></html>")))

(defn destructure-response-handler [p ps hs r]
  (let [h (get hs "host")
        ks (keys r)]
    (response/ok {:param-1 p :other-params ps
                  :host h :request-keys ks})))

(defn wrap-nocache [handler]
  (fn [req]
    (-> req
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

(defn wrap-formats [handler]
  (wrap-restful-format
    handler
    :formats [:json-kw :transit-json :tansit-msgpack]))

(def user-routes
  (context "/users/:id" [id] ;; context closure
    (GET "/apples" [] (str "<p>apples: " id "</p>"))
    (GET "/oranges" [] (str "<p>oranges: " id "</p>"))))

(defroutes handler
  user-routes
  (GET "/" request response-handler)
  (GET "/:id" [id] (str "<p>id: " id "</p>"))
  (POST "/json" [id] (response/ok {:result id}))
  (POST "/destruct" [x & y :as {hs :headers :as r}]
    (destructure-response-handler x y hs r)))

(defn -main []
  (jetty/run-jetty
    (-> #'handler ;; #' => creates var
        wrap-nocache
        wrap-reload
        wrap-formats)
    {:port 3000
     :join? false})) ;; false => non-blocking server thread

