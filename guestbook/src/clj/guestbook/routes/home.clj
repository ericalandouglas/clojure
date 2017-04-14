(ns guestbook.routes.home
  (:require [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET POST]]
            [guestbook.db.core :as db]
            [guestbook.layout :as layout]
            [ring.util.http-response :as response]
            [struct.core :as st]))

(defn home-page [flash]
  (layout/render "home.html"
    (merge {:messages (db/get-messages)}
           (select-keys flash [:name :message :errors]))))

(defn about-page []
  (layout/render "about.html"))

(def message-schema
  [[:name st/required st/string]
   [:message st/required st/string]])

(defn validate-message [params]
  (-> params
      (st/validate message-schema)
      first))

(defn save-message! [params]
  (if-let [errors (validate-message params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-message!
        (assoc params :timestamp (java.util.Date.)))
      (response/found "/"))))

(defroutes home-routes
  (GET "/" {:keys [flash]} (home-page flash))
  (POST "/message" {:keys [params]} (save-message! params))
  (GET "/about" [] (about-page)))

