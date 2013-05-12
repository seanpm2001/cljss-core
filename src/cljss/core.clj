;; ## Core
;; The core namespace is used to regroup the general api
;; proposed by the DSL.

(ns ^{:author "Jeremy Schoffen."}
  cljss.core
  (:refer-clojure :exclude (rem))
  (:require [cljss.AST]
            [cljss.selectors]
            [cljss.properties]
            [cljss.functions]

            [cljss.parse :as parse]
            [cljss.precompilation :as pre]
            [cljss.compilation :as compi]
            [clojure.string :as string]

            [potemkin :as p])
  (:use cljss.protocols))

;; Importation
;; We import here the different helper, contructor functions
;; that we can need when constructing css rules.

(p/import-vars
 [cljss.compilation styles]
 [cljss.AST media inline-css css-comment]
 [cljss.selectors & c-> c-+ c-g+

  att-sel

  link visited hover active focus

   target lang

   enabled disabled checked indeterminate

   root
   nth-child     nth-last-child
   nth-of-type   nth-last-of-type
   first-child   last-child
   first-of-type last-of-type
   only-child

   css-empty
   css-not

   first-line
   first-letter
   before after]
 [cljss.functions url])

;; ### Helpers

(def ^{:arglist '([& rules])
       :doc "Create a list of rules."}
  rules list)


(def ^{:arglist '([& rules])
       :doc "Groups a list of rules."}
  group-rules concat)

(defmacro defrules
  "Defines a named list of rules."
  [r-name & body]
  `(def ~r-name
     (rules ~@body)))

(defn css-str
  "Return the string wrapped inside a pair
  of character \".
  Useful to declare a css property as a css string."
  [s] (str \" s \"))

(defn css-with-style
  "Compile a list of rules with a given style."
  [style & rules]
  (->> rules
       parse/parse-rules
       pre/precompile-rules
       (compi/compile-rules style)))

(defn compressed-css
  "Compile rules with the compressed style."
  [& rules]
  (apply css-with-style (:compressed compi/styles) rules))

(defn compact-css
  "Compile rules with the compact style."
  [& rules]
  (apply css-with-style (:compact compi/styles) rules))

(defn css
  "Compile rules with the classic style."
  [& rules]
  (apply css-with-style (:classic compi/styles) rules))



