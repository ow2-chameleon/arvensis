## Exporter
An importer is a component providing an `ExporterService`. The easiest way to do it, is to create an _iPOJO_ component which extends the [AbstractExporterComponent](https://github.com/barjo/arvensis/blob/develop/core/src/main/java/org/ow2/chameleon/rose/AbstractExporterComponent.java). 

## Importer 
An importer is a component providing an `ImporterService`. The easiest way to do it, is to create an _iPOJO_ component which extends the [AbstractImporterComponent](https://github.com/barjo/arvensis/blob/develop/core/src/main/java/org/ow2/chameleon/rose/AbstractImporterComponent.java). 

## !Be Aware
Be aware that your importers and exporters need to require the `RoseMachine` service. Furthermore it is mandatory that this requirement has the following id: _rose.machine_. This id is necessary to ensure a correct scoping. Thanks to it RoSe will be able to link your component with the RoseMachine corresponding to its configuration. 