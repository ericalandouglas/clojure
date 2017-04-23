(defproject ring-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.0-RC3"]
                 [compojure "1.5.2"]
                 [ring-middleware-format "0.7.2"]
                 [metosin/ring-http-response "0.8.2"]]
  :main ring-app.core)
