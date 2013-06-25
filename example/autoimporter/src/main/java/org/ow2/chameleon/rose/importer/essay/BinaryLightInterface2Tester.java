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
import org.ow2.chameleon.rose.importer.module.philips.BinaryLightPhilips;

@Component
@Instantiate
public class BinaryLightInterface2Tester extends Thread {

	@Requires
	BinaryLightPhilips light;

	boolean isRunning;

	public BinaryLightInterface2Tester(BundleContext bc) {
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
				if(!light.isOn()){
					light.turnOn();
				}else {
					light.turnOff();
				}
				
			} catch (Exception e) {
				//Device probably disconnected
				isRunning = false;
			} finally {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
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
