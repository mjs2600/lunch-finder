(defproject lunch-finder "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [sablono "0.2.6"]
                 [clojurewerkz/neocons "2.0.1"]
                 [om "0.4.2"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-cljsbuild "1.0.2"]]
  :ring {:handler lunch-finder.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}
  :cljsbuild {
  :builds [{:id "dev"
            :source-paths ["cljs-src"]
            :compiler {
              :output-to "resources/public/js/main.js"
              :output-dir "resources/public/js/"
              :optimizations :none
              :source-map true}}]})
