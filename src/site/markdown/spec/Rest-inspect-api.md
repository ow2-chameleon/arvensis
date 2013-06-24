The REST Inspect Api allows you to inspect the RoSe machines at runtime. It provides a simple way to know which machines has been properly created as well as the endpoints discovered, the service exporter and the endpoints imported via those machines.   

## Install
In order to use the REST Inspect API, you must deploy the [[Jax-Rs Exporter]] and its dependencies.
The bundle which contains the REST Inspect API resources is:

```xml
<dependency>
    <artifactId>rose-wui</artifactId>
    <groupId>org.ow2.chameleon.rose</groupId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```


## Overview

<table>
 <tr><th> Method </th> <th> Path  </th> <th> Query Param </th> <th>Type</th></tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/inspect/machines</td>
   <td>filter</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/inspect/machines/{id}</td>
   <td></td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/inspect/discovered</td>
   <td>machine</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/inspect/imported</td>
   <td>machine</td>
   <td>application/json</td>
 </tr>
 <tr>  
   <td>GET</td> 
   <td>/rose/inspect/exported</td>
   <td>machine</td>
   <td>application/json</td>
 </tr>
</table>

## Use the Web User Interface - WUI

Go to the following URL in any browser http://localhost:8080/rose/wui/index.html

## Usage

The following section show you how to use the Inspect API with [curl](http://curl.haxx.se/).



### Machines

* Get the ids of the RoSe machine available on the gateway:
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/inspect/machines
```

* Get the description of the machine {id}:
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/inspect/machines/{id}
```

### Endpoints

* Get the the description of the description of the endpoint discovered on this gateway.
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/inspect/discovered
```
to get only the description of the endpoint discovered via the RoSe machine of id {machineId}
```bash
...?machine={machineId}
```

* Get the the description of the remote endpoint successfully imported on this gateway (a proxy has been created). 
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/inspect/imported
```
to get only the endpoints imported via the RoSe machine of id {machineId}
```bash
...?machine={machineId}
```

### Services

* Get the the endpoint description of the OSGi service successfully exported.
```bash
curl -i -H "Accept: application/json" -X GET http://localhost:8080/rose/inspect/exported
```
to get only the endpoint description of services exported via the RoSe machine of id {machineId}
```bash
...?machine={machineId}
```