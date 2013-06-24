#RoSe Readme 

## Source Organization

This folder contains the code source of the OW2 Chameleon RoSe project.

  - _core_: This project contains the RoSe API and core component.  
  - _jaxrs_: This project contains the RoSe components working with the jax-rs API.
  - _jaxws_: This project contains the RoSe components working with the jax-ws API.
  - _jsonrpc_: This project contains the RoSe components working with the json-rpc protocol.
  - _machines_: This project contains several RoSe distributions. 
  - _registry_: This project contains the RoSe networked registry component implementation.
  - _testing_: This project contains the RoSe testing helpers.  

## License

RoSe is licensed under the Apache License 2.0.

## Infrastructure

### Maven Repository 
		
```xml
<repository>
	<id>ow2-nexus-public</id>
	<name>RoSe - Releases and Snapshots</name>
	<url>http://repository.ow2.org/nexus/content/groups/public/</url>
	<layout>default</layout>
</repository>
```

# Release

## Changelog

* Rose 1.2.3 [details](https://github.com/ow2-chameleon/arvensis/issues?milestone=5&page=1&state=closed) (currently in development) 
	* Not definitive list of features yet

* Rose 1.2.2 [details](https://github.com/ow2-chameleon/arvensis/issues?milestone=2&page=1&state=closed)  
	* Including Rose Deployment package
	* Including Rose Atmosphere OSGi

* Rose 1.2.0 [details](http://github.com/ow2-chameleon/arvensis/issues?milestone=4&state=closed) 
	* **Migrating maven repository** from cloudbees to OW2
	* Supporting **iPOJO 1.10.0**

## Released Version semantic

 major.minor.revision 

 * _major_ changed when there are modification or addition in the functionalities. 
 * _minor_ changed when minor features or critical fixes have been added.
 * _revision_ changed when minor bugs are fixed.

