## Bundles and dependencies

Invocation technology based on "JAX RS":http://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services  is formed by:

|Artifact|Version|Description|
|------------|-----------|---------------|
|[RoSe JAX-RS Exporter (jersey)](https://github.com/barjo/arvensis/tree/develop/jax-rs/jersey-exporter)|(0.1.0.SNAPSHOT)|JAX-RS Exporter|

**Dependencies:**

|Artifact|Version|
|------------|-----------|
|[Apache Felix Http Jetty](http://mir2.ovh.net/ftp.apache.org/dist//felix/org.apache.felix.http.jetty-2.2.0.jar)|2.2.0|
|[Apache Felix EventAdmin](http://mirrors.ircam.fr/pub/apache//felix/org.apache.felix.eventadmin-1.2.14.jar)|1.2.14|
|[jersey-core](https://maven.java.net/service/local/repositories/releases/content/com/sun/jersey/jersey-core/1.9/jersey-core-1.9.jar)|1.9|
|[jersey-server](https://maven.java.net/service/local/repositories/releases/content/com/sun/jersey/jersey-server/1.9/jersey-server-1.9.jar)|1.9|

## Setting up

The [JAX-RS](http://http://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services) specification provides us some nice annotations that helps the job to be done. So the first thing to do is to create a wrapper component that require your business service and provide a service which use the Jax-Rs annotations to map your Pojo as a *resource* thanks to the great [jersey](http://http://jersey.java.net/) project. And, Done ! Well not totally done, you also need to provide a small configuration file ([[rose-conf.json]]) that will be interpreted on the fly to know which service should be exported as REST resources. The following configuration file tells RoSe to export the services which have the property *jaxrs* set. RoSe do it seamlessly using its jersey based [[ExporterService]].

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
