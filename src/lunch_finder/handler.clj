(ns lunch-finder.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [lunch-finder.views.vote :as vote-view]))

(defroutes app-routes
  (GET "/" [] (vote-view/main))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
