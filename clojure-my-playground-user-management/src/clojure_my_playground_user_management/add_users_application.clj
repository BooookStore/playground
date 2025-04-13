(ns clojure-my-playground-user-management.add-users-application
  (:require [clojure.java.io :refer [reader]]
            [clojure-my-playground-user-management.result :as result]))

(defn skip-head [coll] (rest coll))

(defn category-from-str [unvalidate-str]
  (let [category ({"Regular" :regular
                   "Partner" :partner}
                  unvalidate-str)]
    (if-not category
      (result/error (format "unknown category of %s" unvalidate-str))
      (result/ok category))))

(defn run [path]
  (with-open [rdr (reader path)]
    (->> (line-seq rdr)
         (skip-head)
         (println))))
