(ns clojure-my-playground-user-management.add-users-application
  (:require [clojure.string :as str]
            [clojure.java.io :refer [reader]]
            [clojure-my-playground-user-management.result :as result]))

(defn category-from-str [unvalidate-str]
  (if-let [category ({"Regular" :regular
                      "Partner" :partner}
                     unvalidate-str)]
    (result/ok category)
    (result/error (format "unknown category of %s" unvalidate-str))))

(defn skip-head [coll] (rest coll))

(defn split-comma [line] (str/split line #","))

(defn build-unvalidate-user [[category email-address username manager]]
  {:unvalidate-category category
   :unvalidate-email-address email-address
   :unvalidate-username username
   :unvalidate-manager manager})

(defn run [path]
  (let [rdr (reader path)]
    (->> (line-seq rdr)
         (skip-head)
         (map (comp build-unvalidate-user split-comma)))))
