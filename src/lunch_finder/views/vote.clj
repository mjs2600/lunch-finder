(ns lunch-finder.views.vote
  [:use
   hiccup.core
   hiccup.page
   hiccup.element])

(defn main []
  (html5 [:body
          [:div {:id "app"}]
          (include-js "js/goog/base.js"
                      "/js/main.js"
                      "http://fb.me/react-0.9.0.js")
          (javascript-tag "goog.require('lunch_finder.core');")]))
