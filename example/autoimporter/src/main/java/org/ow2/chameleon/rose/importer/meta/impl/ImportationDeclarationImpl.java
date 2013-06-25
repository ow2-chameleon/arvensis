package org.ow2.chameleon.rose.importer.meta.impl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.extender.InstanceDeclaration;
import org.osgi.framework.BundleContext;
import org.ow2.chameleon.rose.ImporterService;
import org.ow2.chameleon.rose.dto.ImportationDeclaration;

/**
 * Importation Declaration is a data transfer object that transit between layers
 * in ROSE. They are created by a lower abstraction level Rose discovery
 * 
 * @author jnascimento
 * 
 */
@Component(name = "ImportationDeclarationImplFactory")
public class ImportationDeclarationImpl implements ImportationDeclaration {

	private String m_importationDeclarationName;

	private Dictionary<String, Object> m_metadata = new Hashtable<String, Object>();

	BundleContext context;

	public ImportationDeclarationImpl(BundleContext bundleContext) {
		this.context = bundleContext;
	}

	public ImportationDeclarationImpl(BundleContext bundleContext,
			Dictionary<String, Object> configuration) {
		this(bundleContext);
		m_metadata = configuration;
	}

	public Dictionary<String, Object> getMetadata() {
		return m_metadata;
	}

	protected Dictionary<String, ?> getServiceProperties() {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(InstanceDeclaration.COMPONENT_NAME_PROPERTY,
				m_importationDeclarationName);

		return properties;
	}

	public Boolean isBound() {
		return false;
	}

	public void bind() {
		
	}

	public List<ImporterService> getImporters() {
		// TODO Auto-generated method stub
		return null;
	}

}
