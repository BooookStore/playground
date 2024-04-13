(ns clojure-my-playground.lib-clojure-core-complement-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]))

(deftest complement-test
  (let [not-empty? (complement empty?)]
    (testing "example1"
      (is (= true
             (not-empty? (list 0 1 2 3)))))))