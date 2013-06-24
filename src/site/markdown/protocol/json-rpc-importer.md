The JSONRPC importer (<code>RoSe_importer.jabsorb</code>) allows you to create an OSGi service from a JSONRPC endpoint.

##Install
In order to use the RoSe JSONRPC importer you need to deploy the following bundles:
 
* the Jabsorb RoSe importer ([snapshot](http://repository-barjo.forge.cloudbees.com/snapshot/org/ow2/chameleon/rose/jsonrpc/jabsorb-importer/), [release](http://repository-barjo.forge.cloudbees.com/release/org/ow2/chameleon/rose/jsonrpc/jabsorb-importer/)),

```xml
<dependency>
    <artifactId>jabsorb-importer</artifactId>
    <groupId>org.ow2.chameleon.rose.jsonrpc</groupId>
    <version>${rose.version}</version>
</dependency>
```
  
* the org.jabsorb bundle ([here](http://maven.ow2.org/maven2/org/jabsorb/org.ow2.chameleon.commons.jabsorb/1.3.1-0002/)),
* http client **TODO add bundle link**
* and the RoSe core ([snapshot](http://repository-barjo.forge.cloudbees.com/snapshot/org/ow2/chameleon/rose/rose-core), [release](http://repository-barjo.forge.cloudbees.com/release/org/ow2/chameleon/rose/rose-core)).

## Overview
Component name: <code>RoSe_importer.jabsorb</code>

##Usage

Once you have deployed the bundles on the gateway, in order to the JSONRPC importer, you must add an instance of <code>RoSe_importer.jabsorb</code> to your RoSe machine. 

* If you have create the machine through the [[Json Configuration]] file.
```json
"component" : [ { "factory" : "RoSe_importer.jabsorb" } ]
```

* If you have create the machine through the [[Java Fluent API]]
```java
import static org.ow2.chameleon.rose.api.Machine;
import static org.ow2.chameleon.rose.api.MachineBuilder.machine;
[...]
//add the jsonrpc importer to your machine.
machine.importer("RoSe_importer.jabsorb").create();
```