(ns clj-genpdf.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial landing [& content]
            (html5
              [:head
               [:title "Sample Report"]]
              [:body
               [:a {:href "/report"} [:button "Download Report"]]]))
