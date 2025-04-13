(ns clojure-my-playground-user-management.result)

(defn ok [v] {:result :ok :value v})

(defn error [msg] {:result :error :message [msg]})