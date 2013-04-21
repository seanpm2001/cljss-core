(ns cljss.selectors-test
  (:require [midje.repl :as m])
  (:use cljss.selectors
        [cljss.parse :only (parse-rule)]
        [cljss.precompilation.decorator :only (decorate-rule)]
        [cljss.selectors.combination :only (combine)]))

(def r1 [:div :bgcolor :blue])
(def r2 [:a :color :white])
(def r3 [:p :color :green])
(def r4 [:strong :color :black])


(def r (conj r1 r2 (conj r3 r4)))
(def p-r (parse-rule r))


(def s1-expected :div)
(def s2-expected (combine :div :a))
(def s3-expected (combine :div :p))
(def s4-expected (combine s3-expected :strong))


(m/fact "The combine selectors decorator recursively combines
        the selector of a rule to its sub rules."
        (let [decorated (decorate-rule p-r combine-selector-decorator)
              s1 (:selector decorated)
              s2 (-> decorated :sub-rules first :selector)
              s3 (-> decorated :sub-rules second :selector)
              s4 (-> decorated :sub-rules second :sub-rules first :selector)]
          s1 => s1-expected
          s2 => s2-expected
          s3 => s3-expected
          s4 => s4-expected))