(ns address-book.routes.address-book-routes
  (:require [compojure.core :refer :all]
            [address_book.views.address_book_layout :refer [common-layout
                                                            read-contact
                                                            add-contact-form]]
            [ring.util.response :as response]))

(def contacts (atom [{:id 1 :name "Guilherme" :phone "(13) 98657634" :email "guilherme@guilherme.com.br"}]))

(defn nex-id []
  (->> @contacts
       (map :id)
       (apply max)
       (+ 1)))

(defn post-route [request]
  (let [name (get-in request [:params :name])
        phone (get-in request [:params :phone])
        email (get-in request [:params :email])]
    (swap! contacts conj {:id (nex-id) :name name :phone phone})
    (response/redirect "/")))

(defn get-route [request]
  (common-layout
    (for [contact @contacts]
      (read-contact contact))
    (add-contact-form)))

(defroutes address-book-routes
  (GET "/" [] get-route)
  (POST "/post" [] post-route))


