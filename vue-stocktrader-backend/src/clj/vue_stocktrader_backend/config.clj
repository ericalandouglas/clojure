(ns vue-stocktrader-backend.config
  (:require [cprop.core :refer [load-config]]
            [cprop.source :as source]
            [mount.core :refer [args defstate]]))


(defn load-env [& {:keys [resource-path]}]
    (let [ps [:merge
              [(args)
               (source/from-system-props)
               (source/from-env)]]]
      (apply load-config (if resource-path
                           (conj ps :resource resource-path)
                           ps))))

(defstate env :start (load-env))
