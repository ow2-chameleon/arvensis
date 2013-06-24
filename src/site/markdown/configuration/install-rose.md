### Work in progress, waiting for release version. Some links may not be available

## Prerequisites
 * Java SDK version 1.5 or later. If necessary, [download and install the Java SE Development Kit (JDK)](http://java.sun.com/javase/downloads/) for your system. 
 * An OSGi distribution. We suggest [Felix](http://felix.apache.org/). 
 * The [iPOJO](http://felix.apache.org/site/apache-felix-ipojo.html) component model. 
 * Apache Maven2 is also necessary in order to build the project. The Apache Maven project is available [here](http://maven.apache.org/), you can also follow the [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) tutorial. 

## How To Get And Install RoSe
 * Get the latest stable distribution (set of RoSe bundles) from here. Working with both, [OW2 Chameleon](http://wiki.chameleon.ow2.org/xwiki/bin/view/Main) and iPOJO 1.8.0. 
 * Or get only the RoSe bundles and use your preferred OSGi distribution. For example, in Felix distribution you only have to put them into the bundle directory. However, iPOJO is a mandatory dependency. 
 * RoSe (without exporting and importing bundles) is composed of the following bundles: 

**Artifact**|**Version**|**Description**
------------|-----------|---------------
[RoSe Core](https://github.com/barjo/arvensis/tree/develop/core)|0.2.4.SNAPSHOT|RoSe core bundle
[RoSe Json Configurator](https://github.com/barjo/arvensis/tree/develop/configurator)|0.1.0.SNAPSHOT|RoSe Configuration bundle

 * Json configurator dependencies:

**Artifact**|**Version**
------------|-----------
[Apache Felix File Install](http://mirrors.ircam.fr/pub/apache//felix/org.apache.felix.fileinstall-3.1.10.jar)|3.1.10
[JSON Service Implementation](http://maven.ow2.org/maven2/org/ow2/chameleon/json/json-service-json.org/0.4.0/json-service-json.org-0.4.0.jar)|0.4.0 


You can also get the latest development release of RoSe and build it by yourself. Source code is available [here](https://github.com/barjo/arvensis/tree/develop/core). 

**Remember to set** `felix.fileinstall.dir=./deploy` **in felix config file**

To be able to export and import services, additional bundles are required. RoSe works with several protocols, each protocol have their own components and dependencies:

 * _JSON-RPC_ (based on jabsorb.org) 
 * _XML-RPC_ (based on apache xml-rpc)
 * _JAX-RS_ (based on Jersey) 
 * _JAX-WS_ (based on Apache Cxf)

Additionaly, for the endpoint description registry Pubsubhubbub Registry bundle (publisher,subscirber,hub) must be installed. 

More informations can be found [here](Pubsubhubbub-discovery-technology-in-RoSe)

Artifact|Version|Description
------------|-----------|---------------
[Pubsubhubub](https://github.com/barjo/arvensis/tree/develop/registry/pubsubhubbub)|(0.1.0.SNAPSHOT)|Pubsubhubbub bundle

* Pubsubhubhub Dependencies:

Artifact|Version
------------|------------
[Syndication Service Specification](http://maven.ow2.org/maven2/org/ow2/chameleon/syndication/syndication-service/0.2.0/syndication-service-0.2.0.jar) |0.2.0
[Apache Felix Http Jetty](http://mir2.ovh.net/ftp.apache.org/dist//felix/org.apache.felix.http.jetty-2.2.0.jar)|2.2.0
[Apache Felix EventAdmin](http://mirrors.ircam.fr/pub/apache//felix/org.apache.felix.eventadmin-1.2.14.jar)|1.2.14
[HttpComponents Client](http://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient-osgi/4.1.1/httpclient-osgi-4.1.1.jar)|4.1.1
[Apache Commons Logging Bundle](http://maven.ow2.org/maven2/commons-logging/org.ow2.chameleon.commons.logging/1.1.1-0002/org.ow2.chameleon.commons.logging-1.1.1-0002.jar)|1.1.1
[HttpComponents Core (OSGi bundle)](http://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore-osgi/4.1.1/httpcore-osgi-4.1.1.jar)|4.1.1
[SLF4J Jakarta Commons Logging Binding](http://ebr.springsource.com/repository/)|1.6.1
[SLF4J Api](http://ebr.springsource.com/repository/app/bundle/)|1.6.1
[JDOM](http://ebr.springsource.com/repository/app/bundle/)|1.1.0
|Syndication-Service : Rome (rome patched)

## The Preconfigured RoSe Platform

A preconfigured RoSe distribution is also available, it is based on the Chameleon Core distribution. The default distribution includes the necessary bundles to run the RoSe framework. You can get it here. The project needs to be compiled by typing `mvn compile`.

### Distribution directory structure

The RoSe distribution is based on the OW2 Chameleon project and is composed of the following directories and file: 

 * core : contains chameleon core bundles.
 * deploy : contains RoSe configuration file (rose-conf.json). This is where you can configure the framework behaviour.
 * log: contains the default chameleon log file.
runtime: contains the technical services and configuration

### How to Start The Preconfigured RoSe Runtime Framework
You can also launch it (with Felix shell) by using the following command line:

    java -jar core/core-0.4.1-SNAPSHOT.jar --debug

When the platform is ready, you can interact with the command processor thanks to the Gogo shell, the `lb` (Felix - `ps`) command allows you to list bundles installed on the gateway. Gateway (felix shell) with all installed bundles and theirs  dependencies suppose to look like it is depicted below, with JSON-RPC (importer/exporter) and Pubsubhubbub bundles. Please note that id`s can differ and installed bundles depend on needs.

|ID|State|Level|Name|
|-----------|------------|------------|------------|
|[ 0] |[Active ]| [ 0]| System Bundle (3.2.2)|
|[ 1]| [Active ]| [ 1]| Apache Felix Bundle Repository (1.6.2)|
|[ 2]| [Active ]| [ 1]| Apache Felix iPOJO (1.8.0)|
|**[ 3]**|**[Active ]**|**[ 1]**|**OW2 Chameleon - RoSe Core (0.2.4.SNAPSHOT)**|
|**[ 4]**|**[Active ]**|**[ 1]**|**OW2 Chameleon - RoSe Json Configurator (0.1.0.SNAPSHOT)**|
|[ 5]| [Active ]| [ 1]| Apache Felix File Install (3.1.10)|
|[ 6]| [Active ]| [ 1]| OW2 Chameleon - json.org JSON Service Implementation (0.4.0)|
|**[ 7]**| **[Active ]**|**[ 1]**|**OW2 Chameleon - RoSe Pubsubhubub Registry (0.1.0.SNAPSHOT)**|
|[ 8]| [Active ]| [ 1]| Syndication Service Specification (0.2.0)|
|[ 9]| [Active ]| [ 1]| Apache Felix Http Jetty (2.2.0)|
|[ 10]| [Active ]| [ 1]| Apache Felix EventAdmin (1.2.14)|
|[ 11]| [Active ]| [ 1]| Apache HttpClient OSGi bundle (4.1.1)|
|[ 12]| [Active ]| [ 1]| OW2 Chameleon - Apache Commons Logging Bundle (1.1.1.0002)|
|[ 13]| [Active ]| [ 1]| Apache HttpCore OSGi bundle (4.1.1)|
|[ 14]| [Resolved ]| [ 1]| SLF4J Jakarta Commons Logging Binding (1.6.1)|
|[ 15]| [Active ]| [ 1]| SLF4J API (1.6.1)|
|[ 16]| [Active ]| [ 1]| JDOM DOM Processor (1.1.0)|
|[ 17]| [Active ]| [ 1]| Syndication-Service : Rome - rome 1.0.1 patched (0.2.1)|
|**[ 18]**|**[Active ]**|**[ 1]**|**OW2 Chameleon - RoSe JSON-RPC Importer (0.1.0.SNAPSHOT)**|
|**[ 19]**|**[Active ]**|**[ 1]**|**OW2 Chameleon - RoSe JSON-RPC Exporter (0.1.4.SNAPSHOT)**|
|[ 20]| [Active ]| [ 1]| OW2 Chameleon - JSONRPC Bundle (from jabsorb) (1.3.1.0003-SNAPSHOT)|


