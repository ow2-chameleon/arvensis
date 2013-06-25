package org.ow2.chameleon.rose.importer.essay;
import javax.jws.WebMethod;
import javax.jws.WebService;
//@WebService
public interface HelloWorld {

	/**
	 * @return <code>"Hello "+name+ "!"</code>
	 **/
	@WebMethod(operationName = "hello")
	String hello(String name);

}
