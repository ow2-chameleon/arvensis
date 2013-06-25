package org.ow2.chameleon.rose.importer.module.generic;

/**
 * Low level BinaryLight
 * @author jnascimento
 *
 */
public interface BinaryLight {

	/**
	 * Fetches the current lamp state (indicating if its powered on or off)
	 * @param state
	 */
	public Boolean getState() throws Exception;
	
	/**
	 * Changes the lamp state (indicating if its powered on or off)
	 * @param state
	 */
	public void setState(Boolean state) throws Exception;
	
}
