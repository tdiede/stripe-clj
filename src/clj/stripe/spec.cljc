(ns stripe.spec
  (:require [clojure.spec.alpha :as s]
            [stripe.util :as util]
            [toolbelt.async :as ta]))


;; helper =======================================================================


(defn channel
  "Takes a spec and returns a spec for a channel. The inner spec is ignored, and
  used just for documentation purposes."
  ([] (channel any?))
  ([spec] ta/chan?))


(defn async
  "Takes a spec and returns an either spec for the passed-in inner spec OR a
  channel. If the Stripe method called is async, The inner spec is ignored, and
  used just for documentation purposes. If not, the inner spec is used."
  ([] (async any?))
  ([spec]
   (s/or :spec spec :channel (channel spec))))


;; stripe-specfic ===============================================================


(s/def ::currency-id
  (s/and string? #(= 3 (count %))))

(s/def ::country-id
  (s/and string? #(= 2 (count %))))

(s/def ::error
  map?)

(s/def ::stripe-error
  (s/keys :req-un [::error]))

(s/def ::deleted
  (partial = "true"))

(s/def ::id
  string?)

(s/def ::deleted-response
  (s/keys :req-un [::deleted ::id]))

(s/def ::metadata
  (s/and (s/map-of keyword? string?)
         ;; Only 20 KV pairs are currently supported.
         #(< (count %) 20)))

(s/def ::last4
  (s/and string? #(= 4 (count %))))

(s/def ::unix-timestamp
  integer?)

(s/def ::gt
  ::unix-timestamp)

(s/def ::gte
  ::unix-timestamp)

(s/def ::lt
  ::unix-timestamp)

(s/def ::lte
  ::unix-timestamp)

(s/def ::timestamp-query
  (s/keys :opt-un [::gt ::gte ::lt ::lte]))

(s/def ::limit
  (s/and integer? (util/between 1 101)))

(s/def ::statement-descriptor
  (s/and string? #(<= (count %) 22)))


(defn statement-descriptor?
  "Is the argument a valid statement descriptor"
  [x]
  (s/valid? ::statement-descriptor x))


(defn metadata [spec]
  (s/and spec (s/keys :opt-un [::metadata])))


(defn unix-timestamp? [ts]
  (s/valid? ::unix-timestamp ts))


(defn deleted? [x]
  (s/valid? ::deleted-response x))


(defn metadata? [m]
  (s/valid? ::metadata m))


(defn stripe-object
  "Adds an `:object` key to Stripe's return objects."
  [spec object-name]
  (s/and spec (comp (partial = object-name) :object)))


(defn currency? [x]
  (s/valid? ::currency-id x))


(defn country? [x]
  (s/valid? ::country-id x))


(defn timestamp-query? [x]
  (s/valid? ::timestamp-query x))


(defn limit? [x]
  (s/valid? ::limit x))


(defn last4? [x]
  (s/valid? ::last4 x))


;; sublist ==============================


(s/def ::livemode
  boolean?)

(s/def :sublist/object (partial = "list"))

(s/def ::has_more boolean?)

(s/def ::url string?)

(s/def ::total_count integer?)

(s/def ::count integer?)


(defn livemode? [x]
  (s/valid? ::livemode x))


(defn sublist
  "Returns a spec for a sublist object."
  [data-spec]
  (s/and (s/keys :req-un [:sublist/object ::has_more ::url ::data]
                 :opt-un [::total_count ::count])
         (comp (partial s/valid? (s/* data-spec)) :data)))
