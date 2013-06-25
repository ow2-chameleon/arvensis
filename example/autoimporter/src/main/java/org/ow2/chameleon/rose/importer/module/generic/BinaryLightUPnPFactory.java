package org.ow2.chameleon.rose.importer.module.generic;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.upnp.basedriver.util.Converter;
import org.osgi.framework.BundleContext;
import org.osgi.service.upnp.UPnPAction;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPService;

/**
 * Factory for UPnP device that will be imported into OSGi
 * 
 * @author jnascimento
 * 
 */
@Component(name = "BinaryLightUPnPFactory")
@Provides
public class BinaryLightUPnPFactory implements BinaryLight {

	private static String UPNP_SWITCH_POWER = "urn:upnp-org:serviceId:SwitchPower";
	private static String UPNP_SWITCH_POWER_ACTION_GET = "GetTarget";
	private static String UPNP_SWITCH_POWER_ACTION_SET = "SetTarget";
	private static String UPNP_SWITCH_POWER_SET_PARAM = "NewTargetValue";
	private static String UPNP_SWITCH_POWER_GET_PARAM = "RetTargetValue";

	private BundleContext context = null;

	@Property(name = "rose.upnp.device", mandatory = true)
	UPnPDevice device;

	public BinaryLightUPnPFactory(BundleContext context) {
		this.context = context;
	}

	/**
	 * Fetches the current lamp state (indicating if its powered on or off)
	 * 
	 * @param state
	 * @throws Exception
	 */
	public Boolean getState() throws Exception {

		for (UPnPService service : device.getServices()) {

			if (service.getId().equals(UPNP_SWITCH_POWER)) {
				UPnPAction action = service
						.getAction(UPNP_SWITCH_POWER_ACTION_GET);

				Dictionary dict = new Hashtable();

				Dictionary res = action.invoke(dict);

				return Boolean.parseBoolean(res
						.get(UPNP_SWITCH_POWER_GET_PARAM).toString());

			}
		}

		return false;
	}

	/**
	 * Changes the lamp state (indicating if its powered on or off)
	 * 
	 * @param state
	 */
	public void setState(Boolean state) throws Exception {

		for (UPnPService service : device.getServices()) {

			if (service.getId().equals(UPNP_SWITCH_POWER)) {
				UPnPAction action = service
						.getAction(UPNP_SWITCH_POWER_ACTION_SET);

				Dictionary dict = new Hashtable();

				dict.put(UPNP_SWITCH_POWER_SET_PARAM, Converter.parseString(
						state.toString(),
						action.getStateVariable(UPNP_SWITCH_POWER_SET_PARAM)
								.getUPnPDataType()));

				action.invoke(dict);

			}
		}

	}

}
