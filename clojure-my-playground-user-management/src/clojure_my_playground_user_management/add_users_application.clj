(ns clojure-my-playground-user-management.add-users-application
  (:require [clojure.java.io :refer [reader]]
            [clojure-my-playground-user-management.result :as result]))

(defn skip-head [coll] (rest coll))

(defn category-from-str [unvalidate-str]
  (if (= "Regular" unvalidate-str)
    (result/ok {:category :regular})
    (if (= "Partner" unvalidate-str)
      (result/ok {:category :partner})
      (result/error (str "unknown category of " unvalidate-str)))))

(defn run [path]
  (with-open [rdr (reader path)]
    (->> (line-seq rdr)
         (skip-head)
         (println))))
