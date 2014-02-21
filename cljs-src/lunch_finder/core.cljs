(ns lunch-finder.core)

(defn- start [] (.log js/console "Hi!"))

(set! (.-onload js/window) start)
