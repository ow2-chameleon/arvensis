## RoSe machine description language

1. Proposition A
```json
machine: { 
  id: "",
  host:  "",
  bind : [
    in : {
      endpoint-filter : "",	
      customizer : { factory : "", properties : {} },
      importer : [ { factory = "" } ]
    },
    out : {
      service-filter : "", 
      exporter : [{ factory : "", properties : {} }]
    }
  ],
  registry : [{ }]
}
```

1. Proposition B.
```json
machine {  
  id : "",
  host : "",
  
  exporters : [
   {jsonrpc : {type : "rose_jsonrpc_exporter", properties : {}},
   {rest : {type : "rose_jersey_exporter"}, properties : {}}
  ],

  connection : [
    out : {
      service-filter : "", 
      exporters : ["json","rest"]
    }
  ],
  registry : [{ }]
}
```
