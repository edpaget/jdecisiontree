(ns jdecisiontree.core
  (:require [clojure.walk :refer [stringify-keys]])
  (:import [jdecisiontree DecisionTree ITask SingleChoiceTask MultiChoiceTask]))

(defn tree
  [name first-question & questions]
  (doto (DecisionTree.)
    (.withName name)
    (.withFirstTask first-question)
    (.withTasks questions)))

(defn- build-question
  [^ITask task label text opts & choices] 
  (doto task
    (.withKey label)
    (.withQuestion text)
    (.withNext (:next opts))
    (.withChoices choices))
  (when (:readable-label opts)
    (.withReadableKey task (:readable-label opts)))
  task)

(defmulti question (fn [type & args] type))

(defmethod question :single
  [type & args]
  (println "HERE")
  (apply build-question (SingleChoiceTask.) args))

(defmethod question :multi
  [type & args]
  (apply build-question (MultiChoiceTask.) args))

(defn choice
  [value label & opts]
  (merge {"value" value "label" label} (stringify-keys opts)))
