# Machine Registration 

Considering that RoSe already implements the protocol you will to use, the only thing you have to do is to perform a **machine registration** and then you are ready to go.

This process is required in order to import or export a service. Although this process remains the same is logical that the parameters for each of them change accordingly. 

# Story telling

Consider you have a webservice, you know everything that is necessary to make use of it. Now, you wish to integrate this service into other applications that have been deployed in a **OSGi Service Platform**. Well there are two flavours of doing that:

1. Implement all the service lookup yourself: connecting, manage the possible service faults, then registering that service in the *OSGi Service Registry*
2. Use RoSe, you declare a **Machine registration** (which can be done in two ways: either you deploy a **Rose DSL** file or you do the registration programatically), then you are ready to go.

## Machine registering with Rose DSL

In order to use **Rose DSL** file, its required to deploy the [fileinstall](http://felix.apache.org/site/apache-felix-file-install.html) bundle.

After that, its enough to create a directory named *load* in the root of your **OSGi Service Platform** (we use [felix](http://felix.apache.org/downloads.cgi))

### File name convention to be respected

So the **Rose DSL** file is correctly detected by Rose platform is has to be named according to some rules, which are:

* file name should start by "rose-conf". _e.g._ rose-confXXXXXXX
* file name can be followed by nothing at all or any literal character (from a to z, upper or lower case, or an underscore "_")
* file should be placed in the directory required by fileinstall bundle, _load_ by default. _e.g._ felix/load/rose-conf-jax_rs XXXXXXX
* file extension must be _.json_ or _.rose_.

For the more experts, the regular expression to be respect is '^rose-conf(-[a-zA-Z_0-9]+|).(json|rose)$'

### Example 1 - declaring an importer 

```json
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
```

### Example 2 - declaring an exporter

```json
{
    "machine": {
        "id": "server1",
        "host": "localhost",
        "connection": [
            {
                "out": {
                    "service_filter": "(jaxrs=*)"
                }
            }
        ],
        "component": [
            {
                "factory": "RoSe_exporter.jersey",
                "properties": {
                    "jersey.servlet.name": "/rest"
                }
            }
        ]
    }
}
```

### Example 3

```json
{
    "machine": {
        "id": "server1",
        "host": "localhost",
        "connection": [
            {
                "out": {
                    "service_filter": "(jaxrs=*)"
                }
            }
        ],
        "component": [
            {
                "factory": "RoSe_exporter.jabsorb",
                "properties": {
                    "jersey.servlet.name": "/rest"
                }
            }
        ]
    }
}
```
### Example 4
```json
machine: { 
  id: "",
  host:  "",
  bind : [
    in : {
      endpoint-filter : "",	
      customizer : { factory : "", properties : {} },
      importer : [ { factory = "" } ]
    },
    out : {
      service-filter : "", 
      exporter : [{ factory : "", properties : {} }]
    }
  ],
  registry : [{ }]
}
```

### Example 5
```json
machine {  
  id : "",
  host : "",
  
  exporters : [
   {jsonrpc : {type : "rose_jsonrpc_exporter", properties : {}},
   {rest : {type : "rose_jersey_exporter"}, properties : {}}
  ],

  connection : [
    out : {
      service-filter : "", 
      exporters : ["json","rest"]
    }
  ],
  registry : [{ }]
}
```

## Machine Registering programatically

In order to register a Rose Machine programatically, it is necessary to create an OSGi component instance that will be responsible to access Rose and deploy the desired configuration.

Below is an example of how to register an Rose machine programatically.

```java
@Component
@Instantiate
public class RoseMachineRegister {

	BundleContext bc;
	Machine m;

	public RoseStarter(BundleContext bc) {
		this.bc = bc;
	}

	@Validate
	public void start() {

		System.out.println("starting service rose..");
		
		m = MachineBuilder.machine(bc, "example_helloworld-1")
				.host("localhost").create();
		try {
			m.out("(jaxrs=*)").add();
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		m.instance("RoSe_exporter.jersey").withProperty("jersey.servlet.name", "/rest").create();
		m.start();
		
	}
	
	@Invalidate
	public void stop(){
		if(m!=null){
			System.out.println("removing machine rose");
			m.stop();
		}
			
	}
	
}
```
