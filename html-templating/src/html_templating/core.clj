(ns html-templating.core
  (:require [selmer.parser :as selmer]
            [selmer.filters :as filters]
            [selmer.middleware :refer [wrap-error-page]]))

(defn render-hello []
  (selmer/render-file "templates/hello.html"
                      {:user {:name "Walrus"}
                       :items ["apple" "orange"]}))

(defn add-image-tag! []
  (selmer/add-tag! :image
                   (fn [args cxt-map content]
                     (str "<img src=" (first args) "/>"))))

(defn renderer []
  (wrap-error-page
    (fn [template]
      {:status 200
       :body (selmer/render-file template {})})))

(defn main []
  (filters/add-filter! :empty? empty?)
  (add-image-tag!)
  (render-hello))

(defn error-main []
  ((renderer) "templates/error.html"))

