(ns clojure-my-playground-user-management.add-users-application-test
    #_{:clj-kondo/ignore [:refer-all]}
   (:require [clojure.test :refer :all]
             [clojure-my-playground-user-management.add-users-application :refer :all]
             [clojure-my-playground-user-management.result :as result]))

(deftest category-from-str-test
  (testing "ok regular"
    (is (= (result/ok :regular)
           (category-from-str "Regular"))))
  
  (testing "ok partner"
    (is (= (result/ok :partner)
           (category-from-str "Partner"))))
  
  (testing "error unknown str"
    (is (= (result/error "unknown category of miss")
           (category-from-str "miss")))))

(deftest build-unvalidate-user-from-row-test
  (are [input expected]
       (= expected
          (unvalidate-user input))

    ["Regular" "aaa@example" "User A" "bbb@example"]
    (result/ok {:unvalidate-category "Regular"
                :unvalidate-email-address "aaa@example"
                :unvalidate-username "User A"
                :unvalidate-manager "bbb@example"})

    ["Regular" "aaa@example" "User A"]
    (result/ok {:unvalidate-category "Regular"
                :unvalidate-email-address "aaa@example"
                :unvalidate-username "User A"
                :unvalidate-manager :no-input})

    ["Regular" "aaa@example"]
    (result/error "not enough input")
    
    ["Regular"]
    (result/error "not enough input")))