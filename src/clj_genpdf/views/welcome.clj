(ns clj-genpdf.views.welcome
  (:require [clj-genpdf.views.common :as common]
            [clj-pdfgen.models.pdfgen :as pdfgen])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))


; Application root. Redirects to /report
(defpage "/" []
         (common/landing))


; Report page. The user will be prompted to download the report.
(defpage "/report" []
         (pdfgen/gen-report))
         ;(common/report))
