(ns ^{:author "Jeremy Schoffen."}
  cljss.protocols)

(defprotocol Tree
  (children [this]
    "Each node of the AST must return the keyword to access its children.")
  (assoc-children [this new-children]
    "Returns a new version of the tree with the new children"))

; Protocols specific to selectors
(defprotocol Neutral
  (neutral? [this]
    "True if the selector is a neutral element in a composition of selectors."))

(defprotocol SimplifyAble
  (simplify [this]
    "Return a simplyfied, equivalent version of a selector."))

(defprotocol Parent
  (parent? [this]
    "Detects if the parent selector is used.")
  (replace-parent [this replacement]
    "Replace any apparition of the parent selecor it contains"))

; protocols about compilation
(defprotocol CSS
  (css-compile [this style]
    "Compile as a css element."))

(defprotocol CssSelector
  (compile-as-selector [this]
    "Compile a value considered a selector to a string."))

(defprotocol CssPropertyName
  (compile-as-property-name [this]
    "Compile a value considered a property name to a string."))

(defprotocol CssPropertyValue
  (compile-as-property-value [this]
    "Compile a value considered a property value to a string."))