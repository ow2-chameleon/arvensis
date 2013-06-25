package org.ow2.chameleon.rose.importer.module.philips;

import java.util.Dictionary;
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
import org.apache.felix.ipojo.annotations.Requires;
import org.ow2.chameleon.rose.dto.ImportationDeclaration;

@Component
@Instantiate
public class BinaryLightPhilipsImporter {

	@Requires(filter = "(factory.name=BinaryLightPhilipsFactory)")
	Factory binaryLightPhilipsFactory;
	
	@Bind(filter = "(rose.importer.level=2)",optional = true)   
	public void bind(ImportationDeclaration declaration,
			Map<String, Object> properties) {
			System.out.println(this.getClass().getSimpleName()+" binding "+declaration);
			
			Dictionary c1=new Properties();
			//c1.put("rose.philips.device", value);
			
			try {

				ComponentInstance ci = (InstanceManager) binaryLightPhilipsFactory
						.createComponentInstance(c1);

			} catch (UnacceptableConfiguration e) {
				e.printStackTrace();
			} catch (MissingHandlerException e) {
				e.printStackTrace();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
			
	}
	
	public void unbind(ImportationDeclaration declaration) {
			System.out.println(this.getClass().getSimpleName()+" unbinding "+declaration);
	}
	
}
