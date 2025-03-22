(ns echo-server.core
  (:require [cheshire.core :as json])
  (:gen-class))

(defn parse-message [line]
  (json/parse-string line true))

(defn send-message [message]
  (println (json/generate-string message))
  (flush))

(defn handle-init [message]
  (let [src (:src message)
        dest (:dest message)
        body (:body message)
        msg-id (:msg_id body)
        node-id (:node_id body)]
    (send-message {:src dest
                  :dest src
                  :body {:type "init_ok"
                         :in_reply_to msg-id}})))

(defn handle-echo [message]
  (let [src (:src message)
        dest (:dest message)
        body (:body message)
        msg-id (:msg_id body)
        echo-value (:echo body)]
    (send-message {:src dest
                  :dest src
                  :body {:type "echo_ok"
                         :in_reply_to msg-id
                         :echo echo-value}})))

(defn handle-message [message]
  (let [body (:body message)
        type (:type body)]
    (case type
      "init" (handle-init message)
      "echo" (handle-echo message)
      (println (str "Unknown message type: " type)))))

(defn -main
  [& args]
  (loop []
    (when-let [line (read-line)]
      (-> line
          parse-message
          handle-message)
      (recur))))
