package org.ow2.chameleon.rose.importer.module.generic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.felix.ipojo.ComponentInstance;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.InstanceManager;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.util.Log;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.osgi.service.remoteserviceadmin.EndpointDescription;
import org.ow2.chameleon.rose.AbstractImporterComponent;
import org.ow2.chameleon.rose.ImporterService;
import org.ow2.chameleon.rose.RoseMachine;
import org.ow2.chameleon.rose.dto.ImportationDeclaration;
import org.ow2.chameleon.rose.util.RoseTools;

/**
 * Low level importer, UPnP device directly dependent
 * 
 * @author jnascimento
 * 
 */
@Component//(name = "UPnP_importer")
@Provides(specifications = { ImporterService.class })
@Instantiate
public class BinaryLightImporter extends AbstractImporterComponent {

	@Requires(filter = "(factory.name=ImportationDeclarationImplFactory)")
	Factory importationDeclarationFactory;
	
	@Requires(filter = "(factory.name=BinaryLightUPnPFactory)")
	Factory binaryLightFactory;

//	@Requires(optional = false, id = "rose.machine")
//	RoseMachine machine;

	@Requires(optional = true)
	private LogService logger;

	BundleContext context;

	Hashtable<String, ComponentInstance> managedComponentInstances = new Hashtable<String, ComponentInstance>();

	public BinaryLightImporter(BundleContext context) {
		this.context = context;
	}

	public List<String> getConfigPrefix() {
		return new ArrayList<String>();
	}

	public RoseMachine getRoseMachine() {
		return null; //machine;
	}

	@Bind(filter = "(rose.importer.level=1)",optional = true) 
	public void bind(ImportationDeclaration declaration,
			Map<String, Object> properties) {

		System.out.println(this.getClass().getSimpleName()+" binding "+declaration);
		
		InstanceManager ci = null;
		ServiceRegistration sr = null;

		Dictionary c1 = new Properties();

		for (Map.Entry<String, Object> val : properties.entrySet()) {

			if (val.getKey().equals("instance.name")
					|| val.getKey().equals("objectClass")
					|| val.getKey().equals("service.id"))
				continue;

			System.out.println("copying:" + val.getKey());

			c1.put(val.getKey(), val.getValue());
		}
		
		c1.put("instance.name", properties.get("rose.upnp.alias"));
		//c1
		
		Dictionary importationDeclarationConfiguration = new Properties();
	
		try {
			importationDeclarationConfiguration.put("protocole.name", "upnp");
			//importationDeclarationConfiguration.put("rose.upnp.id", id);
			//importationDeclarationConfiguration.put("rose.upnp.device", device);
			//importationDeclarationConfiguration.put("rose.upnp.alias", aliasDevice);
			importationDeclarationConfiguration.put("rose.importer.level", "2");
			ci = (InstanceManager)importationDeclarationFactory.createComponentInstance(importationDeclarationConfiguration);
			
		} catch (UnacceptableConfiguration e1) {
			e1.printStackTrace();
		} catch (MissingHandlerException e1) {
			e1.printStackTrace();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		
		context.registerService(new String[]{ImportationDeclaration.class.getCanonicalName()}, ci.getPojoObject(), importationDeclarationConfiguration);
		
		try {

			ci = (InstanceManager) binaryLightFactory
					.createComponentInstance(c1);

			 managedComponentInstances.put(properties.get("rose.upnp.id").toString(), ci);

		} catch (UnacceptableConfiguration e) {
			e.printStackTrace();
		} catch (MissingHandlerException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	@Unbind
	public void unbind(ImportationDeclaration declaration,Map<String, Object> properties) {
		System.out.println(this.getClass().getSimpleName()+" binding "+declaration);
		ComponentInstance instance=managedComponentInstances.get(properties.get("rose.upnp.id").toString());
		instance.dispose();
	}

	@Override
	protected ServiceRegistration createProxy(EndpointDescription description,
			Map<String, Object> extraProperties) {

		logger.log(Log.INFO, "create proxy from importer called");
		System.out.println("create proxy from importer called");

		InstanceManager ci = null;
		ServiceRegistration sr = null;

		Dictionary c1 = new Properties();

		String instanceName = (String) description.getProperties().get(
				"rose.upnp.alias");

		c1.put("instance.name", instanceName);
		c1.put("rose.upnp.device",
				description.getProperties().get("rose.upnp.device"));

		try {

			ci = (InstanceManager) binaryLightFactory
					.createComponentInstance(c1);

			sr = RoseTools.registerProxy(ci.getContext(), ci.getPojoObject(),
					description, extraProperties);

			managedComponentInstances.put(instanceName, ci);

		} catch (UnacceptableConfiguration e) {
			e.printStackTrace();
		} catch (MissingHandlerException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		return sr;
	}

	@Override
	protected void destroyProxy(EndpointDescription description,
			ServiceRegistration registration) {
		System.out.println("Destroy endpoint called");

		String instanceName = (String) description.getProperties().get(
				"rose.upnp.alias");

		ComponentInstance ci = managedComponentInstances.get(instanceName);

		if (ci != null) {
			System.out.println("Removing device instance:" + ci);
			ci.dispose();
		} else {
			System.out.println("Device instance not found");
		}

		registration.unregister();
	}

	@Override
	protected LogService getLogService() {
		return logger;
	}

	@Override
	@Validate
	protected void start() {
		super.start();
	}

	@Override
	@Invalidate
	protected void stop() {
		super.stop();
	}

}
