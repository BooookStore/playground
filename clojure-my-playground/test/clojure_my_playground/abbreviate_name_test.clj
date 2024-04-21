(ns clojure-my-playground.abbreviate-name-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))

(defn formatter
  ([head last] (str head ". " last))
  ([head] (str head "."))
  ([] ""))

(defn destruct [name]
  (let [[first last] (str/split name #" ")
        [head] first]
    [head last]))

(defn abbreviate-name [name]
  (apply formatter (remove nil? (destruct name))))

(deftest abbreviate-name-test
  (testing "abbreviate-name"
    (is (= "B. Store"
           (abbreviate-name "Book Store")))
    (is (= "S."
           (abbreviate-name "Store")))
    (is (= ""
           (abbreviate-name "")))
    (is (= "B. Store"
           (abbreviate-name "Book Store !!!")))))