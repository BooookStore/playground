(ns clojure-my-playground.lib-clojure-core-complement-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]))

(with-test
  (def not-empty? (complement empty?))
  (is (= true
         (not-empty? (list 0))))
  (is (= true
         (not-empty? (list 0 1 2 3))))
  (is (= false
         (not-empty? (list))))
  (is (= true
         (not-empty? "a")))
  (is (= true
         (not-empty? "abc")))
  (is (= false
         (not-empty? ""))))