(defproject clj-genpdf "0.0.1"
            :description "clj-pdfgen: A web application that generates
                         PDF reports for the user to download"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [clj-pdf "0.9.9"]
                           [noir "1.2.1"]]
            :main clj-genpdf.server)

