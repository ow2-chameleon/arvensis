package org.ow2.chameleon.rose.importer.essay;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.ow2.chameleon.rose.api.Machine;
import org.ow2.chameleon.rose.api.Machine.MachineBuilder;

//@Component(name = "Hello-EnvStarter")
//@Instantiate(name = "Hello-EnvStarter-Instance")
public class RoseStarter {

	BundleContext bc;
	Machine m;

	public RoseStarter(BundleContext bc) {
		this.bc = bc;
	}

	@Validate
	public void start() {

		System.out.println("starting service rose..");
		
		m = MachineBuilder.machine(bc, "example_helloworld-1")
				.host("localhost").create();
		try {
			m.out("(jaxrs=*)").add();
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		m.instance("RoSe_exporter.jersey").withProperty("jersey.servlet.name", "/rest").create();
		m.start();
		
	}
	
	@Invalidate
	public void stop(){
		if(m!=null){
			System.out.println("removing machine rose");
			m.stop();
		}
			
	}
	
}
