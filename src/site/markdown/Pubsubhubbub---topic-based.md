Discovery is based on topic based scheme so it contains two modules:
* Publisher
* Subscriber

Role of Hub can play any Pubsubhubbub Hub implementation, eg. 
[Hub by Google](http://pubsubhubbub.appspot.com/ )

**Both modules can be installed on one gateway**

## Publisher module
Consist of 2 components:
* PublisherManager 
* Publishing

###PublisherManager

Only one iPOJO instance of PublisherManager component is necessary and can be created by using RoSe configuration file by adding in component section:

`
{ "factory" : "PublisherManager", 
			  "properties" : { "hub.url" : "http://129.88.51.72:8080"}}
`

Where *hub.url* property specifies HUB full URL address

###Publishing

Several iPOJO instances of Publishingcomponent  can be created. Main purpose of this component is to create RSS topic and notify PublisherManager to send Hub updates notifications. Easily can created by using  RoSe configuration file by adding in component section:

`
{ "factory" : "Publishing", 
			  "properties" : { "topic.url" : "/rss1"}}
`

Where *topic.url* property specifies topic servlet alias on gateway


##Subscriber module
Consist of 2 components:
* SubscriberManager 
* Subscribing

###SubscriberManager

Only one iPOJO instance of SubscriberManager component is necessary and can be created by using RoSe configuration file by adding in component section:

`
{ "factory" : "SubscriberManager", 
			  "properties" : { "hub.url" : "http://129.88.51.72:8080","call.back":"/sub"}
			}
`

Where *hub.url* property specifies HUB full URL address  and *call.back* property specifies servlet alias on gateway in order to retrieve feeds (POST request) and verifications (GET request)


##Subscribing

Several iPOJO instances of Subscribing component can be created. Main purpose of this component is to register for different topics. Easily can created by using  RoSe configuration file by adding in component section:

`
			{ 
			  "factory" : "Subscription", 
			  "properties" : { "topic.url" : "http://129.88.51.50:8082/rss1"}
			} 
`

Where *topic.url* property specifies topic full URL address.



