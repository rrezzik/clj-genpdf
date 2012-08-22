(ns clj-genpdf.views.common
  (:use [noir.core :only [defpartial]]
        [noir.response :only [content-type]]
        [clojure.java.io :as io]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial landing [& content]
            (html5
              [:head
               [:title "Sample Report"]]
              [:body
               [:a {:href "/report"} [:button "Download Report"]]]))


(defn download-report []
  (let [file (io/input-stream "test.pdf")]
    (content-type "application/pdf" file)))
