package org.ow2.chameleon.rose.importer.module.philips;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.ow2.chameleon.rose.importer.module.generic.BinaryLight;

@Component(name = "BinaryLightPhilipsFactory")
@Provides
public class BinaryLightPhilipsFactory implements BinaryLightPhilips {

	@Requires
	private BinaryLight light;
	
	public Boolean isOn() throws Exception {
		return light.getState();
	}

	public void turnOn() throws Exception {
		light.setState(true);
	}

	public void turnOff() throws Exception {
		light.setState(false);
	}

}
