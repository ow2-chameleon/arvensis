package org.ow2.chameleon.rose.importer.upnp.discovery;

import java.util.Dictionary;
import java.util.Hashtable;
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
import org.ow2.chameleon.rose.dto.ImportationDeclaration;
import org.ow2.chameleon.rose.dto.RoseImporterDiscovery;

@Component
@Instantiate
@Provides
public class BinaryLightUPnPDiscovery implements RoseImporterDiscovery {

	@Requires(filter = "(factory.name=ImportationDeclarationImplFactory)")
	Factory importationDeclarationFactory;

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

		System.out.println(this.getClass().getSimpleName()+" binding "+device);
		
		String instancename = generateInstanceName(sr);
		String id = generateID(sr);
		
		System.out.println("----- DEVICE --->"
				+ sr.getProperty(device.FRIENDLY_NAME));
		
		String aliasDevice = String.format("%s-%s", instancename, id);
		String aliasImportationDeclaration = String.format("ImportationDeclaration-%s", id);
		
		InstanceManager ci = null;

		try {

			Dictionary importationDeclarationConfiguration = new Properties();
			importationDeclarationConfiguration.put("rose.importer.level", "1");
			importationDeclarationConfiguration.put("rose.upnp.id", id);
			importationDeclarationConfiguration.put("rose.upnp.device", device);
			importationDeclarationConfiguration.put("rose.upnp.alias", aliasDevice);
			
			ci = (InstanceManager)importationDeclarationFactory.createComponentInstance(importationDeclarationConfiguration);
			
			ImportationDeclaration impDec=(ImportationDeclaration)ci.getPojoObject();
			
			
			context.registerService(new String[]{ImportationDeclaration.class.getCanonicalName()}, ci.getPojoObject(), importationDeclarationConfiguration);
			
			managedComponentInstances.put(id, ci);
			
		} catch (Exception e) {
			
			managedComponentInstances.remove(id);
			
			e.printStackTrace();
		}

	}

	@Unbind(filter = "(UPnP.device.type=urn:schemas-upnp-org:device:BinaryLight:1)", id = UPnPDevice.ID)
	@SuppressWarnings("unused")
	private void unboundDevice(UPnPDevice device, ServiceReference sr) {

		System.out.println(this.getClass().getSimpleName()+" binding "+device);
		
		String id = sr.getProperty(UPnPDevice.UDN).toString();
		String instancename = sr.getProperty(UPnPDevice.FRIENDLY_NAME)
				.toString().replace(" ", "");

		ComponentInstance cm = managedComponentInstances.get(id);
		
		if(cm!=null){
			cm.dispose();
		}

	}
	
	private String generateInstanceName(ServiceReference sr){
		return sr.getProperty(UPnPDevice.FRIENDLY_NAME)
		.toString().replace(" ", "");
	}
	
	private String generateID(ServiceReference sr){
		return sr.getProperty(UPnPDevice.UDN).toString();
	}
}
