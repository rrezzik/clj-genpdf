(ns clj-genpdf.views.welcome
  (:require [clj-genpdf.views.common :as common]
            [clj-genpdf.models.pdfgen :as pdfgen])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))


; Application root. Has a download link to /report
(defpage "/" []
         (common/landing))


; Report page. The user will be prompted to download the report.
(defpage "/report" []
         (pdfgen/gen-report []))
