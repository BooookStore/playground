(ns clojure-my-playground-user-management.add-users-usecase)

(defn build-unvalidate-user [category email-address username manager]
  {:unvalidate-category category
   :unvalidate-email-address email-address
   :unvalidate-username username
   :unvalidate-manager manager})
