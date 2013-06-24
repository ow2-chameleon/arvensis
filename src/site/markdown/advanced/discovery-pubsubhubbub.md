## Global overview
Registry of exported and imported endpoints description are done by using [Pubsubhubbub technology](http://code.google.com/p/pubsubhubbub/) with some minor changes, with result of:

* increase information exchange speed via network, comparing to discovery based on [Zookeeper](Zookeeper-registry) 
* limit useless data exchange  

By limiting useless data exchange is meant that Hub notifies subscribers only with proper endpoint description, that satisfies their endpoint filter. That is a major change comparing to original Pubsubhubbub where subscribers specifies only publisher RSS topic, in RoSe this solution would perform useless data exchange because of not all publisher notifications (Endpoint created, Endpoint deleted) could satisfy particular subscriber endpoint filter. Generally, subscriber does not have any information about publishers and vice versa. For both of them joint point is a Hub. 

Pubsubhubbub discovery system is based on 3 components:

 * Publisher - creates a RSS topic where notifies Hub of created or deleted particular endpoints on his machine
 * Subscriber - sends subscription for particular endpoints (endpoint filter)
 * Hub - manages all notifications and subscriptions

##Setting up in JSON configuration file (subscriber/publisher)

Additional sections are need to be added in "COMPONENT" section

* RoSe pubsubhubbub – subscriber
 * callback.url – relative http address path, used in notifying by Hub
 * hub.url – full Hub http address
 * endpoint.filter – specify an endpoint filter

`{"factory" : "Rose_Pubsubhubbub.subscriber",
"properties" : { "callback.url" : "/subscription", "hub.url":"http://localhost:8080/hub", "endpoint.filter":"(endpoint.id=*)" } 
}`   

* Rose pubsubhubbub – publisher
 * hub.url – full Hub http address
 * rss.url – relative http address path where RSS feeds are published

`{   "factory" : "Rose_Pubsubhubbub.publisher",
     "properties" : { "hub.url" : "http://...", "rss.url":"/rss" }
}`

Both of those components works independently of each other on single machine, which means that only one can be installed. 

##Setting up HUB in JSON configuration file

Pubsubhubbub Hub is independent from RoSe Core (machine does not provide RoSe service).
Only RoSe Json Configurator in needed.

Additional section is need to be added in "COMPONENT" section in order to lunch Pubsubhubbub hub

 * hub.url - relative path where hub will get POST request from subscribers or publishers.

`{ 
			  "factory" : "Rose_Pubsubhubbub.hub",
			  "properties" : { "hub.url" : "/hub" }
			}`

Feel free to install Hub on same machine where are publishers or subscribers components are running.

_RoSe works only with one instance of Hub, future work will give opportunity to work with bunch of Hubs_