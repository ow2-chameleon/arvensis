package org.ow2.chameleon.rose.importer.upnp.discovery;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.upnp.UPnPDevice;
import org.ow2.chameleon.rose.ng.DiscoveryService;
import org.ow2.chameleon.rose.ng.dto.ImportDeclaration;

@Component
@Instantiate
@Provides
public class BinaryLightUPnPDiscovery implements DiscoveryService {

	@Requires(filter = "(factory.name=ImportDeclarationFactory)")
	Factory importDeclarationFactory;

	Hashtable<String, ComponentInstance> managedComponentInstances = new Hashtable<String, ComponentInstance>();

	BundleContext context;

	public BinaryLightUPnPDiscovery(BundleContext bc) {
		context = bc;
	}

	@Validate
	public void start() {
		System.out.println("starting discovery..");
	}

	@Invalidate
	public void stop() {
		System.out.println("stopping discovery..");
	}

	@Bind(filter = "(UPnP.device.type=urn:schemas-upnp-org:device:BinaryLight:1)", id = UPnPDevice.ID, aggregate = true, optional = true)
	@SuppressWarnings("unused")
	private void boundDevice(UPnPDevice device, ServiceReference sr) {

		System.out.println(this.getClass().getSimpleName() + " binding " + device);

		String instanceName = generateInstanceName(sr);
		String id = generateID(sr);

		System.out.println("----- DEVICE --->" + sr.getProperty(device.FRIENDLY_NAME));

		String aliasDevice = String.format("%s-%s", instanceName, id);
		String aliasImportationDeclaration = String.format("ImportationDeclaration-%s", id);

		InstanceManager ci = null;

		try {

			Map<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("rose.importer.level", "1");
			metadata.put("rose.upnp.id", id);
			metadata.put("rose.upnp.device", device);
			metadata.put("rose.upnp.alias", aliasDevice);

			Dictionary config = new Properties();
			config.put("metadata", metadata);

			ci = (InstanceManager) importDeclarationFactory.createComponentInstance(config);

			ImportDeclaration impDec = (ImportDeclaration) ci.getPojoObject();

			context.registerService(new String[]{ImportDeclaration.class.getCanonicalName()}, ci.getPojoObject(), config);

			managedComponentInstances.put(id, ci);

		} catch (Exception e) {
			managedComponentInstances.remove(id);
			e.printStackTrace();
		}

	}

	@Unbind(filter = "(UPnP.device.type=urn:schemas-upnp-org:device:BinaryLight:1)", id = UPnPDevice.ID)
	@SuppressWarnings("unused")
	private void unboundDevice(UPnPDevice device, ServiceReference sr) {

		System.out.println(this.getClass().getSimpleName() + " binding " + device);

		String id = sr.getProperty(UPnPDevice.UDN).toString();

		ComponentInstance cm = managedComponentInstances.get(id);

		if (cm != null) {
			cm.dispose();
		}

	}

	private String generateInstanceName(ServiceReference sr) {
		return sr.getProperty(UPnPDevice.FRIENDLY_NAME).toString().replace(" ", "");
	}

	private String generateID(ServiceReference sr) {
		return sr.getProperty(UPnPDevice.UDN).toString();
	}
}
