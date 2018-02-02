(defproject starcity/stripe-clj "0.1.0-SNAPSHOT"
  :description "Stripe bindings for Clojure."
  :url "https://github.com/starcity-properties/stripe-clj"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.async "0.4.474"]
                 [http-kit "2.2.0"]
                 [cheshire "5.8.0"]
                 [starcity/toolbelt-async "0.4.0"]
                 [starcity/toolbelt-core "0.3.0"]]
<<<<<<< HEAD

  :source-paths ["src/clj"]

  :repl-options {:init-ns user}

  :profiles {:dev {:source-paths ["src/clj" "test/clj" "env/dev"]}})
=======
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]

  :profiles {:dev {:dependencies [[org.clojure/test.check "0.10.0-alpha2"]
                                  [se.haleby/stub-http "0.2.4"]
                                  [ring/ring-codec "1.1.0"]]}})
>>>>>>> initialize testing
