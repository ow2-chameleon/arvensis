package org.ow2.chameleon.rose.ng.dto;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.apache.felix.ipojo.extender.InstanceDeclaration;
import org.osgi.framework.BundleContext;
import org.ow2.chameleon.rose.ImporterService;

/**
 * ImportDeclaration is a data transfer object (DTO pattern) that transit between layers
 * in Rose.
 * They are created by a lower abstraction level Rose discovery
 *
 * @author jnascimento
 */
@Component(name = "ImportDeclarationFactory")
public class ImportDeclarationImpl implements ImportDeclaration {

    private final List<ImporterService> importerServicesBound = new ArrayList<ImporterService>();

    private String m_importDeclarationName;

    @Property(name = "metadata", immutable = true)
    @ServiceProperty
    private Dictionary<String, Object> m_metadata = new Hashtable<String, Object>();

    BundleContext m_context;

    public ImportDeclarationImpl(BundleContext bundleContext) {
        this.m_context = bundleContext;
    }

    public ImportDeclarationImpl(BundleContext bundleContext,
                                 Dictionary<String, Object> configuration) {
        this(bundleContext);
        m_metadata = configuration;
    }

    public Dictionary<String, Object> getMetadata() {
        return m_metadata;
    }

    protected Dictionary<String, ?> getServiceProperties() {
        Hashtable<String, Object> properties = new Hashtable<String, Object>();
        properties.put(InstanceDeclaration.COMPONENT_NAME_PROPERTY, m_importDeclarationName);

        return properties;
    }

    // TODO : synchronized
    public Boolean isBound() {
        return (importerServicesBound.size() > 0);
    }

    // TODO : synchronized
    public void bind(ImporterService importerService) {
        importerServicesBound.add(importerService);
    }

    // TODO : synchronized
    public void unBind(ImporterService importerService) {
        importerServicesBound.remove(importerService);
    }

    // TODO : synchronized
    public List<ImporterService> getImporters() {
        // TODO : make a copy
        return importerServicesBound;
    }

}
