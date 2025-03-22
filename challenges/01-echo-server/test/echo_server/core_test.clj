(ns echo-server.core-test
  (:require [clojure.test :refer :all]
            [echo-server.core :refer :all]
            [cheshire.core :as json]))

(deftest parse-message-test
  (testing "Parse valid JSON message"
    (let [json-str "{\"src\":\"c1\",\"dest\":\"n1\",\"body\":{\"type\":\"echo\",\"echo\":\"hello\",\"msg_id\":1}}"
          result (parse-message json-str)]
      (is (= "c1" (:src result)))
      (is (= "n1" (:dest result)))
      (is (= "echo" (get-in result [:body :type])))
      (is (= "hello" (get-in result [:body :echo])))
      (is (= 1 (get-in result [:body :msg_id]))))))

(deftest handle-init-test
  (testing "Handle init message"
    (let [init-msg {:src "c1", 
                    :dest "n1", 
                    :body {:type "init", 
                           :node_id "n1", 
                           :msg_id 1}}
          response (atom nil)]
      ; Override send-message to capture response
      (with-redefs [send-message (fn [msg] (reset! response msg))]
        (handle-init init-msg)
        (is (= "n1" (:src @response)))
        (is (= "c1" (:dest @response)))
        (is (= "init_ok" (get-in @response [:body :type])))
        (is (= 1 (get-in @response [:body :in_reply_to])))))))

(deftest handle-echo-test
  (testing "Handle echo message"
    (let [echo-msg {:src "c1", 
                   :dest "n1", 
                   :body {:type "echo", 
                          :echo "Hello, World!", 
                          :msg_id 1}}
          response (atom nil)]
      ; Override send-message to capture response
      (with-redefs [send-message (fn [msg] (reset! response msg))]
        (handle-echo echo-msg)
        (is (= "n1" (:src @response)))
        (is (= "c1" (:dest @response)))
        (is (= "echo_ok" (get-in @response [:body :type])))
        (is (= 1 (get-in @response [:body :in_reply_to])))
        (is (= "Hello, World!" (get-in @response [:body :echo])))))))

(deftest handle-message-test
  (testing "Message routing for different message types"
    (let [responses (atom [])]
      ; Override handlers to track called functions
      (with-redefs [handle-init (fn [msg] (swap! responses conj [:init msg]))
                    handle-echo (fn [msg] (swap! responses conj [:echo msg]))]
        
        ; Test init message routing
        (let [init-msg {:body {:type "init"}}]
          (handle-message init-msg)
          (is (= [:init init-msg] (first @responses))))
        
        ; Reset responses
        (reset! responses [])
        
        ; Test echo message routing
        (let [echo-msg {:body {:type "echo"}}]
          (handle-message echo-msg)
          (is (= [:echo echo-msg] (first @responses))))))))
