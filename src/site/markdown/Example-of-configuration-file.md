## Configuration file
Specs can be found below a code

    {
	"machine" : {
		"id" : "server1",
		"host" : "192.168.1.1",
		
		"connection" : [
				{
				"out" : {
					"service_filter" : "(objectClass=org.osgi.service.log.LogService)",
				        "properties" : { "tag" : ["log","service","id"] }
					    }
				},
                 "in" : {
					"endpoint_filter" : "(endpoint.id=*)"
					    }
				}

		],

		"component" : [
			{ 
			  "factory" : "RoSe_importer.jabsorb" 
            },
            { 
			  "factory" : "RoSe_exporter.jabsorb" 
            },
			{ 
			  "factory" : "Rose_Pubsubhubbub.hub",
			  "properties" : { "hub.url" : "/hub" }
			},
			{ 
			  "factory" : "Rose_Pubsubhubbub.publisher",
			  "properties" : { "hub.url" : "192.168.1.1:8080/hub/hub:8080/hub", "rss.url":"/roserss" }
			},
			{ 
			  "factory" : "Rose_Pubsubhubbub.subscriber",
			  "properties" : { "callback.url" : "/sub1", "hub.url":"192.168.1.1:8080/hub", "endpoint.filter":"(endpoint.id=*)" }
			}
		],
	}
    }

### Specs

Configuration above shows that machine name is "server1" **(id)** and can be reached at "192.168.1.1" ip address **(host)**. In section **"connection"** is depicted two possible configurations **"in"** and **"out"**, which in this particular example shows that machine can provide **(out)** every service which satisfies filter specified in LDAP convention, here it is *objectClass=org.osgi.service.log.LogService*. Some additional informations are added in "properties". 
Besides, machine is accepting **(in)** all services, by setting filter a *"endpoint.id=* *".

**"Component"** section shows that:

   * Communication for imported services is via JSON-RPC by setting up a *"RoSe_importer.jabsorb"*
   * Communication for exported services is via JSON-RPC by setting up a *"RoSe_exporter.jabsorb"*
   * On machine is working a Pubsubhubbub Hub installed on "192.168.1.1:8080/hub", please note that only relative path *"/hub"* has been set up
   * Pubsubhubbub publisher for notification an exported services is connected to Pubsubhubbub Hub at "192.168.1.1:8080/hub" and post all new RSS feeds at "192.168.1.1:8080/roserss", please note that only relative path "*/reserss"* has been set up
   * Pubsubhubbub subscriber for notification an imported services is connected to Pubsubhubbub Hub at "192.168.1.1:8080/hub" and receives all informations from hub "192.168.1.1:8080/sub1", please note that only relative path *"/sub1"* has been set up. Furthermore specifies that from particular hub is expecting all endpoints by setting *"(endpoint.id=* *)" up

Please note that in above example Pubsubhubbub hub, Pubsubhubbub publisher and Pubsubhubbub subscriber are installed on same machine, but these components are independent from each other and can be installed selectively. 

Additionally feel free to use different invocation protocols instead of JSON-RPC in this case.

 