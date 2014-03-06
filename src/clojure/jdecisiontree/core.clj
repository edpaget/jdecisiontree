(ns jdecisiontree.core
  (:require [clojure.walk :refer [stringify-keys]])
  (:import [jdecisiontree DecisionTree SingleChoiceTask MultiChoiceTask]))

(defn tree
  [name first-question & questions]
  (doto (DecisionTree.)
    (.withName name)
    (.withFirstTask first-question)
    (.withTasks questions)))

(defn- build-question
  [^ITask task label text opts & choices] 
  (doto (task)
    (.withKey label)
    (.withQuestion text)
    (.withNext (:next opts))
    (when-let [rlabel {:readable-label opts}]
      (.withReadableKey opts))
    (.withChoices choices)))

(defmulti question* [type & args] (fn [type & args] type))

(defmethod question :single
  [type & args]
  (apply build-question (SingleChoiceTask.) args))

(defmethod question :multi
  [type & args]
  (build-quesiton (MultiChoiceTask.) args))

(defn choice
  [value label & opts]
  (merge {"value" value "label" label} (stringify-keys opts)))
