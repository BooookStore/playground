(ns clojure-my-playground-user-management.add-users-application
  (:require [clojure.string :as str]
            [clojure.java.io :refer [reader]]
            [clojure-my-playground-user-management.result :as result]
            [clojure-my-playground-user-management.add-users-usecase :as usecase]))

(defn category-from-str [unvalidate-str]
  (if-let [category ({"Regular" :regular
                      "Partner" :partner}
                     unvalidate-str)]
    (result/ok category)
    (result/error (format "unknown category of %s" unvalidate-str))))

(defn skip-head [coll] (rest coll))

(defn split-comma [line] (str/split line #","))

(defn unvalidate-user-from-row [[category email-address username manager]]
  (if (some nil? [category email-address username])
    (result/error "not enough input")
    (result/ok (usecase/build-unvalidate-user category email-address username (or manager :no-input)))))

(defn run [path]
  (let [rdr (reader path)]
    (->> (line-seq rdr)
         (skip-head)
         (map (comp unvalidate-user-from-row split-comma)))))
