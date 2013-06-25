package org.ow2.chameleon.rose.importer.essay;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.BundleContext;
import org.ow2.chameleon.rose.importer.module.generic.BinaryLight;

public class BinaryLightInterface1Tester extends Thread {

	@Requires
	BinaryLight light;

	boolean isRunning;

	public BinaryLightInterface1Tester(BundleContext bc) {
		super.setDaemon(true);
	}
	
	@Bind
	public void bind(BinaryLight lightBinded){
		this.start();
	}
	
	@Unbind
	public void unbind(BinaryLight lightBinded){
		try {
			this.wait(5000);
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		while (isRunning) {

			try {
				light.setState(!light.getState());
			} catch (Exception e) {
				//Device probably disconnected
				isRunning = false;
			} finally {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			}

		}
	}

	@Validate
	public void validate() {
		isRunning = true;
	}

	@Invalidate
	public void invalidate() {
		isRunning = false;
	}

}
