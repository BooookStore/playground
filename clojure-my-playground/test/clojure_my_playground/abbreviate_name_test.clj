(ns clojure-my-playground.abbreviate-name-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))

(defn head-upper-formatter
  ([head last] (str head ". " last))
  ([head] (str head "."))
  ([] ""))

(defn destruct [name]
  (let [[first last] (str/split name #" ")
        [head] first]
    (remove nil? [head last])))

(defn abbreviator [destructor formatter]
  (fn [name] (apply formatter (destructor name))))

(defn abbreviate-head-upper [name]
  (let [f (abbreviator destruct head-upper-formatter)]
    (f name)))

(deftest abbreviate-name-test
  (testing "abbreviate-name"
    (is (= "B. Store"
           (abbreviate-head-upper "Book Store")))
    (is (= "S."
           (abbreviate-head-upper "Store")))
    (is (= ""
           (abbreviate-head-upper "")))
    (is (= "B. Store"
           (abbreviate-head-upper "Book Store !!!")))))