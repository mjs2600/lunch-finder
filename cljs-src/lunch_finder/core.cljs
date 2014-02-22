(ns lunch-finder.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]))

(enable-console-print!)

(def app-state (atom {:restaurants [{:name "Q-Shack" :votes 0}
                                    {:name "Thai Cafe" :votes 0}]}))

(defn vote-for-restaurant [restaurants vote]
  (vec (map #(if (= % vote)
             (update-in % [:votes] inc)
             %) restaurants)))

(defn restaurant-view [restaurant owner]
  (reify
    om/IRenderState
    (render-state [this {:keys [vote]}]
      (dom/span nil
               (dom/dt nil (:name restaurant))
               (dom/dd nil (str "Votes: " (:votes restaurant)))
               (dom/button #js {:onClick (fn [e] (put! vote @restaurant))} "Vote")))))

(defn restaurants-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
      {:vote (chan)})
    om/IWillMount
    (will-mount [_]
      (let [vote (om/get-state owner :vote)]
        (go (loop []
              (let [restaurant (<! vote)]
                (om/transact! app :restaurants
                              (fn [xs] (vote-for-restaurant xs restaurant)))
                (recur))))))
    om/IRenderState
    (render-state [this {:keys [vote]}]
      (dom/div nil
               (dom/h2 nil "Restaurants")
               (apply dom/dl #js{:className "restaurants"}
                      (om/build-all restaurant-view (:restaurants app)
                                    {:init-state {:vote vote}}))))))

(om/root
 restaurants-view
 app-state
 {:target (. js/document (getElementById "app"))})
