Rose works with discovery technology based on Pubsubhubbub.  Nevertheless this technology can be easily exchange to Zookeeper discovery.

**How to run:**

Install bundle:

1. RoSe Zookeeper Registry

1. Create an instance of “RoSe_registry.zookeeper”  with “connection” property, Rose configuration file can handle it.
Example of “component” section in configuration file:

`{ 
"factory" : "RoSe_registry.zookeeper",
  "properties" : { "connection" : "localhost/rose" }
}`

* “connection"”- Zookeeper server url


**Using Zookeeper You have to keep in mind that this technology works on nonstandard http ports, so you can encounter some problems with firewalls! Comparing to Pubsubhubbub many of useless data are transmitted in subscriber point of view.**
