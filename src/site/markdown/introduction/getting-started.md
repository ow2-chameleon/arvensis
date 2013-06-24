# Getting started with RoSe

## What RoSe arvensis is all about
The main idea behind RoSe is to have a clear separation of concern between the distributed part of your applications and the local ones. The arvensis framework is based on [OSGi](http://www.osgi.org) and [iPOJO](http://felix.apache.org/site/apache-felix-ipojo.html). Once you have populated your OSGi framework with the arvensis bundles and your applications ones you are able to dynamically create and publishes *Endpoint Description* for your local OSGi services. 

Let's say that you want one of your service to be accessible as a REST resource. The [JAX-RS](http://http://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services)(u) specification provides us some nice annotations that helps the job to be done. So the first thing to do is to create a wrapper component that require your business service and provide a service which use the Jax-Rs annotations to map your Pojo as a *resource* thanks to the great [jersey](http://http://jersey.java.net/) project. And, Done ! Well not totally done, you also need to provide a small configuration file ([[rose-conf.json]]) that will be interpreted on the fly to know which service should be exported as REST resources. The following configuration file tells RoSe to export the services which have the property *jaxrs* set. RoSe do it seamlessly using its jersey based [[ExporterService]].

    {
    "machine" : {
      "id" : "server1",
      "host" : "localhost",

      "connection" : [
        { "out" : { "service_filter" : "(jaxrs=*)" } }
      ],

      "component" : [
        { 
	  "factory" : "RoSe_exporter.jersey",
          "properties" : { "jersey.servlet.name" : "/rest" }
        }
      ],
    }

If you are accustomed to iPOJO, a simple hello world component exported as a resource looks like that:

    @Component(name="example_helloworld")
    @Instantiate(name="example_helloworld-1")
    @Provides(properties=@StaticServiceProperty(name="jaxrs",value="true", type = "String"))
    @Path(value="/helloworld/{name}")
    public class HelloImpl implements HelloWorld {

	  @GET
	  @Produces("text/plain")
	  public String hello(@PathParam("name") String name) {
	        	return "Hello "+name+" !";
	  }
    } 

So now, if you go to: http://localhost:8080/rest/helloworld/toto you will have: `hello toto !`. The good thing is that you are free to add or remove the services you want to have an endpoint for and the endpoint will be created or destroyed according to the service availability. Now, you need to export only the service providing the *HelloWorld* services, no probalem just change the configuration: `{ "out" : { "service_filter" : "(objectclass=com.acme.HelloWorld)" } }`
  
All right, we all loves jersey and the jax-rs specification, but what about JSON-RPC, XML-RPC, JAX-WS ? lucky you the framework comes with those flavors. And the good thing with those protocol is that you don't even need to use annotations :) since they all are [interface based](http://en.wikipedia.org/wiki/Interface-based_programming). Basically, the service contract will be the interface of the endpoint. 
But use we caution, distributed systems have some [fallacies](http://en.wikipedia.org/wiki/Fallacies_of_Distributed_Computing) and it's often a good thing to develop a component between your busyness logic and the remote world. Especially when you don't have to care about the protocol API and just have to say which service you want to be remotely accessible, and which protocol to use. 
You think that the `service_filter` is not enough ? You want to express some complex condition, or maintains thing like counter ? No problem, we will soon see how you can enhance that configuration. Still reticent ? Your favorite protocol is not part of the game, no problem the framework is easily extensible, actually you can even add, remove or change protocol during the execution. 

1. **Populate the gateway**
    * [Preparing OSGi platform](https://github.com/barjo/arvensis/wiki/Preparing-OSGi-platform-to-work-with-RoSe) 
    * [JSON-RPC (import/export)](JSON-RPC-(import-export\))
    * [JERSEY-WS (export)](JAX-RS-(export-only\))
1. **[Pubsubhubbub discovery technology in RoSe](https://github.com/barjo/arvensis/wiki/Pubsubhubbub-discovery-technology-in-RoSe)**
1. **Rose configuration file**
    *  [General overview](https://github.com/barjo/arvensis/wiki/General-overview-in-configuration-file) 
     *  [[Using “IN” in connection]]
     *  [[Using “OUT” in connection]]
     *  [["Component" section]]
    *  [[Using "Component" to work with RoSe]]
    *  [[Example of configuration file]]
1. **[[Running Pubsubhubbub Hub]]**
1. **[[Pubsubhubbub WebConsole plugin]]**

Extra: [Zookeeper Discovery (Pubsubhubbub alternative)](https://github.com/barjo/arvensis/wiki/Zookeeper-registry)
