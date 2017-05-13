(ns guestbook.env
  (:require [taoensso.timbre :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[guestbook started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[guestbook has shut down successfully]=-"))
   :middleware identity})
