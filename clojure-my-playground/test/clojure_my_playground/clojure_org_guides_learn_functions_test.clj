(ns clojure-my-playground.clojure-org-guides-learn-functions-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]))

(defn greet [] "Hello")

(def greet-2 (fn [] "Hello"))

(def greet-2-2 #(str "Hello"))

(defn greeting
  ([] "Hello, World!")
  ([x] (str "Hello, " x))
  ([x y] (str x ", " y "!")))

(defn do-nothing [x] x)

(defn always-things [n & m] 100)

(deftest functions-knowledge
  (testing "1"
    (is (= "Hello"
           (greet))))
  (testing "2"
    (is (= "Hello"
           (greet-2))))
  (testing "2-2"
    (is (= "Hello"
           (greet-2-2))))
  (testing "3"
    (is (= "Hello, World!"
           (greeting))))
  (testing "3-1"
    (is (= "Hello, Bookstore"
           (greeting "Bookstore"))))
  (testing "3-2"
    (is (= "Clojure, Bookstore!"
           (greeting "Clojure" "Bookstore"))))
  (testing "4 do-nothing"
    (is (= "NOTHING"
           (do-nothing "NOTHING"))))
  (testing "5 always-things"
    (is (= 100
           (always-things 1 2 3 4 5)))))