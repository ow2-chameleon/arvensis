## Global overview
Configurtion File contains RoSe machine description language 

Name of file supposed to start with "rose-conf" and saved as a json file, name example:

`json-conf-x.json`

where x can be either digits or chars

And need to be placed in `deploy` folder or other if different has been set in `felix.fileinstall.dir` platform property.

Root of configuration file is “machine” and it contains 4 sections:

* id – identification name 
* host – network node address
* connection – specify importing and/or exporting particular services; has 2 sections:
 * in – for importing purpose
 * out – for exporting purpose 
* component – section to  easily instantiate components with given properties

## “IN” in connection

This section specifies which services are demanded to be imported.

To import endpoints just set a filter in “endpoint_filter” property, example:

`"endpoint_filter" : "(endpoint.id=*)"`

This filter will import every discovered endpoints.

## “OUT” in connection

This section specifies which services are demanded to be exported.

To export services set a filter in "service_filter" property a filter, example:

`"service_filter" : "(objectClass= objectClass=org.osgi.service.log.LogService)"`

Using this filter service providing LogService will be exported

Some additional properties can be added by setting “properties” property:

`"properties" : { "tag" : ["log","service","id"]`

## "COMPONENT" section

This section was created in order to simplify a components instantiations. Required  is “factory” section which specifies a factory method, additional properties  can be set in "properties" property , example:

`{"factory" : "factory_name",
"properties" : { "property1" : "value1", "property2":"value2" } 
}`

## Example file
Full example of configuration file is available [here](https://github.com/barjo/arvensis/wiki/Example-of-configuration-file)