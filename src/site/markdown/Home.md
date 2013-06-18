Welcome to the RoSe arvensis wiki!

## Scientific grounds

RoSe is a product of a research, articles and documents have been produced to support and improve such product. The kick off of this research into production was done by Jonathan Bardin, in his [PhD. thesis](http://www.liglab.fr/spip.php?article1243).

## Synopsis

An example of how to dynamically create an [[endpoint]] for each available HelloWorld OSGi services.

#### With rose-json.conf

```json
{ 
  "machine" : { 
    "id" : "rose-server",
    "host" : "localhost",
    "connection" : [ { "out" : { "service_filter" : "(objectClass=org.acme.HelloWorld)"} } ],
    "component" : [ { "factory" : "RoSe_exporter.cxf", "properties" : { "cxf.servlet.name" : "/ws" } } ] 
  }
}
```

| | Grouping ||
First Header | Second Header | Third Header |
------------ | :-----------: | -----------: |
Content | *Long Cell* ||
Content | **Cell** | Cell |
New section | More | Data |
And more | And more ||
[Prototype table]

#### With the API

```java
import static org.ow2.chameleon.rose.api.Machine;
import static org.ow2.chameleon.rose.api.MachineBuilder.machine;
[...]

//create the rose-server machine
Machine server = machine(context, "rose-server").host("localhost").create();
//Add the cxf exporter to the machine
server.exporter("RoSe_exporter.cxf").withProperty("cxf.servlet.name", "/ws").create();
//Add an out connection, wich will create an endpoint for each HelloWorld services.
server.out("(objectClass=org.acme.HelloWorld)").add();
```

The list of available endpoint created with the cxf exporter is published on `http://localhost/ws`.

## Documentation

The _core_ javadoc is now available on http://ow2-chameleon.github.io/arvensis/apidocs/.

* [[Getting-Started]]
* [[Arvensis White Paper]]
* [[Proposals]]
* [[TODO]]
