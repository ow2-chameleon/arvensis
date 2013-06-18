
|Protocol| Export | Import |
|-----------|------------|------------|
|[JSON-RPC](JSON-RPC-(import-export\))|X|X|
|[JAX-RS](JAX-RS-(export-only\))|X| |
|[XML-RPC](XML-RPC-(import-export\))|X|X|
|[JAX-WS](JAX-WS-(import-export\))|X|X|
[Import/export capability by protocol]

Since all protocols are interface based. Basically, the service contract will be the interface of the endpoint. But use we caution, distributed systems have some fallacies and it's often a good thing to develop a component between your busyness logic and the remote world. Especially when you don't have to care about the protocol API and just have to say which service you want to be remotely accessible, and which protocol to use. You think that the service_filter is not enough ? You want to express some complex condition, or maintains thing like counter ? No problem, we will soon see how you can enhance that configuration. Still reticent ? 

Your favorite protocol is not part of the game, no problem the framework is easily extensible, actually you can even add, remove or change protocol during the execution.
