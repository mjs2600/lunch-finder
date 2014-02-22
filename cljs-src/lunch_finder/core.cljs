(ns lunch-finder.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]))

(enable-console-print!)

(def app-state (atom {:restaurants [{:name "Q-Shack" :votes 0}
                                    {:name "Thai Cafe" :votes 0}]}))

(defn restaurant-view [restaurant owner]
  (reify
    om/IRenderState
    (render-state [this {:keys [delete]}]
      (dom/span nil
               (dom/dt nil (:name restaurant))
               (dom/dd nil (str "Votes: " (:votes restaurant)))
               (dom/button #js {:onClick (fn [e] (put! delete @restaurant))} "Delete")))))

(defn restaurants-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
      {:delete (chan)})
    om/IWillMount
    (will-mount [_]
      (let [delete (om/get-state owner :delete)]
        (go (loop []
              (let [restaurant (<! delete)]
                (om/transact! app :restaurants
                              (fn [xs] (vec (remove #(= restaurant %) xs))))
                (recur))))))
    om/IRenderState
    (render-state [this {:keys [delete]}]
      (dom/div nil
               (dom/h2 nil "Restaurants")
               (apply dom/dl #js{:className "restaurants"}
                      (om/build-all restaurant-view (:restaurants app)
                                    {:init-state {:delete delete}}))))))

(om/root
 restaurants-view
 app-state
 {:target (. js/document (getElementById "app"))})
