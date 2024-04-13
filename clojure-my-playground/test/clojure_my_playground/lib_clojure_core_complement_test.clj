(ns clojure-my-playground.lib-clojure-core-complement-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]))

(deftest complement-test
  (testing "example1"
    (is (= true
           (let [not-empty? (complement empty?)]
             (not-empty? (list 0 1 2 3)))))))