Hub is a component in order to discover all exported endpoints  by publishers and send updates (notification) to all subscribers. Can run on different host.

**How to run:**

Dependency bundles:

1. Install all bundles like in section [1.1](https://github.com/barjo/arvensis/wiki/Preparing-OSGi-platform-to-work-with-RoSe) and [1.2](https://github.com/barjo/arvensis/wiki/Exporting-services-using-JSON-RPC-and-Pubsubhubbub).

1. Create an instance of “Rose_Pubsubhubbub.hub factory”  with “hub.url” property, Rose configuration file can handle it.
Example of “component” section in configuration file:

`{"factory" : "Rose_Pubsubhubbub.hub",
"properties" : { "hub.url" : "/hub" }
}`

* “hub.url”- relative http address path were Hub is listening to requests
