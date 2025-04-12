(ns clojure-my-playground.person-list-io
  (:require [clojure.string :as str]
            [clojure.java.io :refer [reader]]))

(def input-file-path "resources/person-list/person-list-1.txt")

(defn user-builder [line]
  (let [[mail-address username] (str/split line #",")
        mail-address (str/trim mail-address)
        username (str/trim username)]
    {:mail-address mail-address :username username}))

(defn load-users [path]
  (let [rdr (reader path)]
    (map user-builder (line-seq rdr))))

(comment
   (load-users input-file-path))

(defn result-ok? [r] (= :ok (:result r)))

(defn validate-contains-atmark [mail-address]
  (if (str/includes? mail-address "@")
    {:result :ok}
    {:result :error :message (vector (str "'" mail-address "' not include '@'"))}))

(defn validate-domain [mail-address]
  (if (str/ends-with? mail-address "example")
    {:result :ok}
    {:result :error :message (vector (str "'" mail-address "'not domain example"))}))

(defn join-message
  ([result1 result2]
   (update result1 :message #(flatten (conj % (:message result2)))))
  ([] {:result :error :message []}))

(defn comp-validator [& validators]
  (fn [mail-address]
    (let [results (map #(% mail-address) validators)
          errors (:error (group-by :result results))]
      (if (empty? errors)
        {:result :ok}
        (reduce join-message errors)))))

(defn valid-users-mail-address? [users]
  (let [mail-addresses (map :mail-address users)
        validate (comp-validator validate-contains-atmark validate-domain)]
    (every? #(result-ok? (validate %)) mail-addresses)))

(defn validate-users-mail-address [users]
  (let [mail-addresses (map :mail-address users)
        f (fn [v ma] (if (result-ok? v) (validate-contains-atmark ma) v))]
    (reduce f {:result :ok} mail-addresses)))

(defn validate-users-mail-address-results [users]
  (let [mail-addresses (map :mail-address users)
        validation-results (map validate-contains-atmark mail-addresses)
        errors (:error (group-by #(:result %) validation-results))]
    (if (empty? errors)
      {:result :ok}
      (reduce join-message {:result :error :message []} errors))))

(comment
  (valid-users-mail-address? (load-users input-file-path))
  (validate-users-mail-address (load-users input-file-path))
  (validate-users-mail-address-results (load-users input-file-path))
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