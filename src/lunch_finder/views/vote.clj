(ns lunch-finder.views.vote
  [:use
   hiccup.core
   hiccup.page
   hiccup.element])

(defn main []
  (html5
   [:head
    (include-js "js/goog/base.js" "/js/main.js")
    (javascript-tag "goog.require('lunch_finder.core');")]
   [:body [:div "test"]]))
