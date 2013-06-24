The REST Api allows you to create and configure RoSe Machine through HTTP. 

## Install
In order to use the REST API, you must deploy the [[Jax-Rs Exporter]] and its dependencies.
The bundle which contains the REST API resources is:

```xml
<dependency>
    <artifactId>rose-wui</artifactId>
    <groupId>org.ow2.chameleon.rose</groupId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```


## Overview

<table>
 <tr><th> Method </th> <th> Path  </th> <th>Type</th></tr>
 <tr>  
   <td>GET, POST</td> 
   <td>/rose/machines</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET, DELETE</td> 
   <td>/rose/machines/:id</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/machines/:id/instances</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET, PUT, DELETE</td> 
   <td>/rose/machines/:id/instances/:instanceId</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/machines/:id/ins</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET, PUT, DELETE</td> 
   <td>/rose/machines/:id/ins/:inId</td>
   <td>application/json</td>
 </tr>
<tr>  
   <td>GET</td> 
   <td>/rose/machines/:id/outs</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET, PUT, DELETE</td> 
   <td>/rose/machines/:id/outs/:outId</td>
   <td>application/json</td>
 </tr>
</table>

## Usage

The following section show you how to use the API with [curl](http://curl.haxx.se/).

### Machines

* Get the ids of the RoSe machine created through this API:
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines
```

* Get the description of the machine _:id_
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/:id
```

* Create a new machine _:id_
```bash
curl -i -X POST -d '{ "id" : ":id" }' http://localhost:8080/rose/machines
```
you can also set the hostname _:hostname_
```bash
-d '{ "id" : ":id", "host" : ":hostname"}'
```

* Destroy the machine {id}:
```bash
curl -i -X DELETE http://localhost:8080/rose/machines/{id}
```

### Instances

* Get the instances linked to the machine {mId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/instances
```

* Get the description of the instance {inId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/instances/{inId}
```

* Create the instance named {inId} from the component {component}
```bash
curl -i -X PUT http://localhost:8080/rose/machines/{mId}/instances/{inId}?component={component}
```
you can set some optional properties (as content)
```bash
-d "{ 'jsonrpc.servlet.name' : '/jsonrpc' }"
```

* Destroy the instance {inId}
```bash
curl -i -X DELETE http://localhost:8080/rose/machines/{mId}/instances/{inId}
```


### In Connections

* Get the InConnection linked to the machine {mId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/ins
```

* Get the description of the InConnection {inId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/ins/{inId}
```

* Create the InConnection named {inId} with the service_filter {filter}
```bash
curl -i -X PUT http://localhost:8080/rose/machines/{mId}/ins/{inId}?service_filter={filter}
```
you can set the protocol
```bash
...?protocol={protocol}
```
and some optional properties as content
```bash
-d "{ 'key' : 'value' }"
```

* Destroy the InConnection {inId}
```bash
curl -i -X DELETE http://localhost:8080/rose/machines/{mId}/ins/{inId}
```

### Out Connections

* Get the OutConnection linked to the machine {mId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/outs
```

* Get the description of the OutConnection {outId}
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/machines/{mId}/outs/{outId}
```

* Create the OutConnection named {outId} with the service_filter {filter}
```bash
curl -i -X PUT http://localhost:8080/rose/machines/{mId}/outs/{outId}?endpoint_filter={filter}
```
you can set the protocol
```bash
...?protocol={protocol}
```
and some optional properties as content
```bash
-d "{ 'key' : 'value' }"
```

* Destroy the OutConnection {outId}
```bash
curl -i -X DELETE http://localhost:8080/rose/machines/{mId}/outs/{outId}
```