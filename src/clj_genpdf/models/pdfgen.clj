(ns clj-genpdf.genpdf
  (:use [clj-pdf :as pdf]
        [clojure.java.io :as io]))

; Basic report meta-data and such
(def report-skeleton
  [{:title  "Sample Report"
    :size          :a4
    :orientation   "portrait"
    :subject "Report"
    :author "John Doe"
    :creator "Jane Doe"
    :doc-header ["inspired by" "William Shakespeare"]
    :header "page header"
    :footer ""
    :footer-seperator "of"
    :pages true ; specifies that the total pages should be printed in the footer of each page
    }]

; Ugly. This will be removed (just loaded from a file) later. This is for quickly moving along.
(def sample-data
 [{:order 1, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 2, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 3, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 4, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 5, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 6, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 7, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 8, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 9, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 10, :description "This is the description 2", :quantity 1, :cost 45.2}

  {:order 11, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 12, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 13, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 14, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 15, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 16, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 17, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 18, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 19, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 20, :description "This is the description 2", :quantity 1, :cost 45.2}

  {:order 21, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 22, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 23, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 24, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 25, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 26, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 27, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 28, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 29, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 30, :description "This is the description 2", :quantity 1, :cost 45.2}

  {:order 31, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 32, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 33, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 34, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 35, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 36, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 37, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 38, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 39, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 40, :description "This is the description 2", :quantity 1, :cost 45.2}

  {:order 41, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 42, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 43, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 44, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 45, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 46, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 47, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 48, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 49, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 50, :description "This is the description 2", :quantity 1, :cost 45.2}

  {:order 51, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 52, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 53, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 54, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 55, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 56, :description "This is the description 2", :quantity 1, :cost 45.2}
  {:order 57, :description "This is the description 3", :quantity 3, :cost 18}
  {:order 58, :description "This is the description 4", :quantity 1, :cost 190.0}
  {:order 59, :description "This is the description", :quantity 2, :cost 12.2}
  {:order 60, :description "This is the description 2", :quantity 1, :cost 45.2}])

;=======================================  
; Helper functions for report generation
;=======================================
(defn start-report [doc]
  (conj doc report-skeleton))

(defn add-header [doc header-content]
  (conj doc [:heading header-content]))

(defn add-table-header [doc col-names]
  (conj doc [:table {:header col-names
                    :width 50
                    :border true
                    :cell-border true}]))

(defn add-table-row [doc row]
  (conj doc row))

; Generate the desired pdf report
(defn gen-report [data] 
  (let [doc []] 
    (do
      (start-report doc)
      (add-header doc "Sample Report")
      (add-table-header doc ["Order" "Description" "Quantity" "Cost"])
      (add-table-row [51 "A description" 2 12.3])
      (pdf/pdf doc "test.pdf"))))



