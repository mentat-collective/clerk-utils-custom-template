(ns mentat-collective.sci-extensions
    (:require [mentat-collective.custom]
              [sci.ctx-store]
              [sci.core :as sci]))

;; ## SCI Environment Extension
;;
;; This namespace extends the SCI environment used by Clerk, making available
;; any custom ClojureScript you'd like to use while [writing
;; viewers](https://book.clerk.vision/#writing-viewers) for your notebooks.

;; This form creates a "lives-within-SCI" version of the `clerk-utils.custom`
;; namespace by copying all public vars.
(def custom-namespace
  (sci/copy-ns mentat-collective.custom
               (sci/create-ns 'mentat-collective.custom)))


;; Adding an entry to this map is equivalent to adding an entry like
;; `(:require [clerk-utils.custom])` to a Clojure namespace.
(def custom-namespaces
  {'mentat-collective.custom custom-namespace
   ;; Add any more namespaces here! Make sure to `:require` anything you add at
   ;; the top of this file.
   })

;; Add aliases here for namespaces that you've included above. This adds an
;; `:as` form to a namespace: `(:require [mentat-collective.custom :as custom])`
(def custom-aliases
  {'custom 'mentat-collective.custom})


;; This next form mutates SCI's default environment, merging in your custom code
;; on top of what Clerk has already configured.
(sci.ctx-store/swap-ctx!
 sci/merge-opts
 {;; Use `:classes` to expose JavaScript classes that you'd like to use in your
  ;; viewer code. `Math/sin` etc will work with this entry:
  :classes    {'Math  js/Math}
  :namespaces custom-namespaces
  :aliases    custom-aliases})

;; ## JavaScript Libraries
;;
;; See the [JavaScript
;; Libraries](https://clerk-utils.mentat.org/#javascript-libraries) section of
;; the [Clerk-utils docs](https://clerk-utils.mentat.org) for instructions on
;; how to make NPM dependencies like "react" available inside of Clerk's
;; viewers.
