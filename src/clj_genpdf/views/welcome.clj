(ns clj-genpdf.views.welcome
  (:require [clj-genpdf.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))


; Application root. Redirects to /report
(defpage "/" []
         (common/landing))


; Report page. The user will be prompted to download the report.
(defpage "/report" []
         (common/report))
