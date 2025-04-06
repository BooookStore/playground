(ns clojure-my-playground.person-list-io
  (:require [clojure.string :as str]))

(def input-file-path "resources/person-list/person-list-1.txt")

(def person-list (str/split (slurp input-file-path) #"\n"))

(println "person-list has" (count person-list) "people")