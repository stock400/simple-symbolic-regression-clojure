(defproject simple-symbolic-regression-clojure "0.0.1-SNAPSHOT"
  :description "Simple stack-based GP system"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [com.climate/claypoole "1.1.0"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]
                                  [lein-midje "3.1.3"]
                                  [criterium "0.4.3"]]}}
  :main simple-symbolic-regression-clojure.core
  :jvm-opts ["-Xms1g" "-Xmx4g"
             "-XX:+UseG1GC"
             "-Dcom.sun.management.jmxremote"
             "-Dcom.sun.management.jmxremote.ssl=false"
             "-Dcom.sun.management.jmxremote.authenticate=false"
             "-Dcom.sun.management.jmxremote.port=43210"])

