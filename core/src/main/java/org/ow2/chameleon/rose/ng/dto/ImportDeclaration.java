package org.ow2.chameleon.rose.ng.dto;

import java.util.Dictionary;
import java.util.List;

import org.ow2.chameleon.rose.ImporterService;

public interface ImportDeclaration {

	String IMPORTATION_PROTOCOL_NAME = "rose.importation.protocol.name";

	String IMPORTATION_PROTOCOL_VERSION = "rose.importation.protocol.version";

	String IMPORTATION_ENDPOINT_URL = "rose.importation.endpoint.url";

	Dictionary<String, Object> getMetadata();
	
	Boolean isBound();
	
	void bind(ImporterService importerService);

    void unBind(ImporterService importerService);
	
	List<ImporterService> getImporters();
	
}
