(ns guestbook.routes.home
  (:require [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET POST]]
            [guestbook.db.core :as db]
            [guestbook.layout :as layout]
            [ring.util.http-response :as response]
            [struct.core :as st]))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(def message-schema
  [[:name st/required st/string]
   [:message st/required st/string]])

(defn validate-message [params]
  (-> params
      (st/validate message-schema)
      first))

(defn save-message! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (response/bad-request {:errors errors})
    (try
      (db/save-message!
        (assoc params :timestamp (java.util.Date.)))
      (response/ok {:status :ok})
      (catch Exception e
        (response/internal-server-error
          {:errors {:server-error ["Failed to save message!"]}})))))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/messages" [] (response/ok (db/get-messages)))
  (POST "/message" req (save-message! req))
  (GET "/about" [] (about-page)))

