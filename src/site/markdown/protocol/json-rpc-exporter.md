The JSONRPC exporter (<code>RoSe_exporter.jabsorb</code>) allows you to create a JSONRPC endpoint from an OSGi service.

##Install
In order to use the RoSe JSONRPC exporter you need to deploy the following bundles:
 
* the Jabsorb RoSe exporter ([snapshot](http://repository-barjo.forge.cloudbees.com/snapshot/org/ow2/chameleon/rose/jsonrpc/jabsorb-exporter/), [release](http://repository-barjo.forge.cloudbees.com/release/org/ow2/chameleon/rose/jsonrpc/jabsorb-exporter/)),

```xml
<dependency>
    <artifactId>jabsorb-exporter</artifactId>
    <groupId>org.ow2.chameleon.rose.jsonrpc</groupId>
    <version>${rose.version}</version>
</dependency>
```
  
* the org.jabsorb bundle ([here](http://maven.ow2.org/maven2/org/jabsorb/org.ow2.chameleon.commons.jabsorb/1.3.1-0002/)),
* an HttpService (such as the felix HttpService Jetty, [here](http://felix.apache.org/site/downloads.cgi),
* and the RoSe core ([snapshot](http://repository-barjo.forge.cloudbees.com/snapshot/org/ow2/chameleon/rose/rose-core), [release](http://repository-barjo.forge.cloudbees.com/release/org/ow2/chameleon/rose/rose-core)).

## Overview
Component name: <code>RoSe_exporter.jabsorb</code>
<table>
<thead>
<tr>
 <th>Property</th><th>Default Value</th><th>Type</th><th>Description</th>
</tr>
</thead>
<tr>
  <td><code>jsonrpc.servlet.name</code></td><td>"/JSONRPC"</td><td>String</td><td>path of the JSONRPC broker</td>
</tr>
<tbody>
</tbody>
</table>

##Usage

Once you have deployed the bundles on the gateway, in order to the JSONRPC exporter, you must add an instance of <code>RoSe_exporter.jabsorb</code> to your RoSe machine. 

* If you have create the machine through the [[Json Configuration]] file.
```json
"component" : [ { "factory" : "RoSe_exporter.jabsorb" } ]
```

* If you have create the machine through the [[Java Fluent API]]
```java
import static org.ow2.chameleon.rose.api.Machine;
import static org.ow2.chameleon.rose.api.MachineBuilder.machine;
[...]
//add the jsonrpc exporter to your machine.
machine.exporter("RoSe_exporter.jabsorb").create();
```

By default, all the out connection defined in your machine will then use the jsonrpc exporter to create an endpoint. The endpoint will be publish through the url: ``http://{machineUrl}:{port}/JSONRPC``.

You can change the default endpoint url, by setting the property <code>jsonrpc.servlet.name</code> when you declare the instance. 
