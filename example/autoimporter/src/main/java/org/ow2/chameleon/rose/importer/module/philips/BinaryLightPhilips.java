package org.ow2.chameleon.rose.importer.module.philips;

/**
 * 
 * @author jnascimento
 *
 */
public interface BinaryLightPhilips {

	/**
	 * Checks the state of the lamp
	 * @return
	 * @throws Exception
	 */
	Boolean isOn() throws Exception;
	
	/**
	 * Activate the lamp
	 * @return
	 * @throws Exception
	 */
	void turnOn() throws Exception;
	
	/**
	 * Deactivate the lamp
	 * @return
	 * @throws Exception
	 */
	void turnOff() throws Exception;
	
}
