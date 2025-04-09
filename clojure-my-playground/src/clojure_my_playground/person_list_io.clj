(ns clojure-my-playground.person-list-io
  (:require [clojure.string :as str]))

(def input-file-path "resources/person-list/person-list-1.txt")

(defn user-builder [line]
  (let [[mail-address username] (str/split line #",")
        mail-address (str/trim mail-address)
        username (str/trim username)]
    {:mail-address mail-address :username username}))

(defn load-users [path] 
  (map user-builder (str/split (slurp path) #"\n")))

(comment
   (load-users input-file-path))

(defn result-ok? [r] (= :ok (:result r)))

(defn validate-mail-address [mail-address]
  (if (str/includes? mail-address "@")
    {:result :ok}
    {:result :error :message (str "'" mail-address "' not include '@'")}))

(defn valid-users-mail-address? [users]
  (let [mail-addresses (map :mail-address users)]
    (every? #(result-ok? (validate-mail-address %)) mail-addresses)))

(defn validate-users-mail-address [users]
  (let [mail-addresses (map :mail-address users)
        f (fn [v ma] (if (result-ok? v) (validate-mail-address ma) v))]
    (reduce f {:result :ok} mail-addresses)))

(comment
  (valid-users-mail-address? (load-users input-file-path))
  (validate-users-mail-address (load-users input-file-path))
  )

(defn orderd-users [users]
  (let [idx-seq (iterate inc 1)
        idx-with-person (map vector idx-seq users)]
    (map (fn [[n p]] (conj p {:index n})) idx-with-person)))

(comment
  (orderd-users (load-users input-file-path))
  (->> input-file-path
       load-users
       orderd-users)
  )