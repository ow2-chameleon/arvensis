## Bundles and dependencies

Invocation technology based on [JSON RPC](http://json-rpc.org/)  is formed by:

|Artifact|Version|Description|
|-----------|------------|------------|
|[RoSe JSON-RPC Exporter](https://github.com/barjo/arvensis/tree/develop/json-rpc/jabsorb-exporter)|0.1.4.SNAPSHOT|Exporter JSON-RPC|
|[RoSe JSON-RPC Importer](https://github.com/barjo/arvensis/tree/develop/json-rpc/jabsorb-importer)|0.1.0.SNAPSHOT|Importer JSON-RPC|

**Dependencies:**

|Artifact|Version|
|-----------|------------|
|[Apache Commons Logging Bundle](http://maven.ow2.org/maven2/commons-logging/org.ow2.chameleon.commons.logging/1.1.1-0002/org.ow2.chameleon.commons.logging-1.1.1-0002.jar)|1.1.1|
|[Apache Felix Http Jetty](http://mir2.ovh.net/ftp.apache.org/dist//felix/org.apache.felix.http.jetty-2.2.0.jar)|2.2.0|
|JSONRPC Bundle (from jabsorb)|1.3.1.0003-SNAPSHOT|
|[Apache Felix EventAdmin](http://mirrors.ircam.fr/pub/apache//felix/org.apache.felix.eventadmin-1.2.14.jar)|1.2.14|
|[HttpComponents Client](http://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient-osgi/4.1.1/httpclient-osgi-4.1.1.jar)|4.1.1|
|[HttpComponents Core (OSGi bundle)](http://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore-osgi/4.1.1/httpcore-osgi-4.1.1.jar)|4.1.1|


##Setting up in JSON configuration file

Additional sections need to added in "COMPONENT" section

* Exporter

`{"factory" : "RoSe_exporter.jabsorb"}`   

* Importer

`{"factory" : "RoSe_importer.jabsorb"}` 
