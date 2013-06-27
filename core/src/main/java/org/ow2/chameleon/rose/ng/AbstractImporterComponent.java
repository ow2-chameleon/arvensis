package org.ow2.chameleon.rose.ng;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;
import org.ow2.chameleon.rose.ng.dto.ImportDeclaration;

import java.util.Collection;
import java.util.Map;

/**
 * Abstract implementation of an proxy-creator which provides an {@link org.ow2.chameleon.rose.ImporterService}.
 * Start must be call before registering the service !
 * Stop must be called while the service is no more available !
 *
 * @author barjo
 * @version 0.2.0
 */
public abstract class AbstractImporterComponent implements ImporterService {
    //
    // TODO : structures to keep all references of ImportDeclaration, ServiceRegistration, ... ???
    //

    private volatile boolean isValid = false;

    public AbstractImporterComponent() {
    }

	/*--------------------------*
     * EndpointCreator methods. *
	 *--------------------------*/

    /**
     */
    protected abstract ServiceRegistration createProxy(final ImportDeclaration importDeclaration, final Map<String, Object> extraProperties);

    /**
     * Abstract method, is called when the sub class must destroy the proxy. Do not forget to unregister the proxy, call:
     * <code>registration.unregister()</code>
     */
    protected abstract void destroyProxy(final ImportDeclaration importDeclaration, final ServiceRegistration registration);

    /**
     * Stop the proxy-creator, iPOJO Invalidate instance callback.
     * Must be override !
     * Close all endpoints.
     */
    protected void stop() {
        // destroy all the proxies
        // TODO

        isValid = false;
    }

    /**
     * Start the endpoint-creator component, iPOJO Validate instance callback.
     * Must be override !
     */
    protected void start() {
        isValid = true;
    }

    /**
     * @return <code>true</code> if the {@link org.ow2.chameleon.rose.ImporterService} is in a valid state, <code>false</code> otherwise.
     */
    protected final boolean isValid() {
        return isValid;
    }

	/*--------------------------------------------*
	 *  Convenient access to some useful service. *
	 *--------------------------------------------*/

    /**
     * @return The {@link org.osgi.service.log.LogService} service.
     */
    protected abstract LogService getLogService();


	/*---------------------------------*
	 *  ExporterService implementation *
	 *---------------------------------*/

    /**
     * @param importDeclaration
     * @param properties
     * @return
     */
    // FIXME : what this method should return ?
    public Object importService(ImportDeclaration importDeclaration, Map<String, Object> properties) {
        if (!isValid) {
            // TODO : pb
        }


        if (true) {
            // Already register, lone Registration
            // TODO
        } else {
            //First registration, create the proxy
            ServiceRegistration registration = createProxy(importDeclaration, properties);
            // TODO
        }
        return null;
    }

    // FIXME : what this method should return ?
    public Collection<Object> getAllImportReferences() {
        // TODO
        return null;
    }

}
