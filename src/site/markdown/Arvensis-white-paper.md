# Rose White Paper

## Motivation

## Setup 

The core bundle runs on any framework. For its configuration, it will use the following framework or system properties:

<table>
<tr>
  <th>Property</th>
  <th>Default</th>
  <th>Description</th>
</tr>
<tr>
  <td>rose.machine.id</td>
  <td>a generated uuid</td>
  <td>The id of the machine.</td>
</tr>
<tr>
  <td>rose.machine.host</td>
  <td>localhost</td>
  <td>The host name of the machine.</td>
</tr>
<tr>
  <td>rose.machine.ip</td>
  <td>127.0.0.1</td>
  <td>The local machine ip address.</td>
</tr>
</table>

### Json configurator


## Component & Service

### RoseMachine

### NetworkRegistry

### InConnector

### OutConnector

### ExporterService (Server/Endpoint factory)

### ImporterService (Client/Proxy factory)

### Configurator

## OO API

### DynamicExporter (Dynamic Server/Endpoint creator)

### DynamicImporter (Dynamic Client/Proxy creator)

## Usage

## Off the shell components

### JSON-RPC
* ExporterService
* ImporterService

### XML-RPC 
* ExporterService
* ImporterService

### JAX-WS (CXF)
* ExporterService
* ImporterService

### JAX-RS (Jersey)
* ExporterService

### Zookeeper 
* NetworkRegistry 

### PubSubHubHub
* Registry