package org.ow2.chameleon.rose;

import org.apache.felix.ipojo.ComponentFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.service.remoteserviceadmin.EndpointDescription;
import org.osgi.service.remoteserviceadmin.ExportReference;
import org.osgi.service.remoteserviceadmin.ExportRegistration;
import org.ow2.chameleon.rose.internal.BadExportRegistration;
import org.ow2.chameleon.rose.util.ConcurrentMapOfSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static org.osgi.service.log.LogService.LOG_WARNING;
import static org.ow2.chameleon.rose.util.RoseTools.computeEndpointExtraProperties;

/**
 * Abstract implementation of an endpoint-creator {@link ComponentFactory} which provides an {@link ExporterService}.
 * Start must be call before registering the service !
 * Stop must be called while the service is no more available !
 *
 * @author barjo
 * @version 0.2.0
 */
public abstract class AbstractExporterComponent implements ExporterService {
    private final ConcurrentMapOfSet<ServiceReference, ExportRegistration> registrations;
    private volatile boolean isValid = false;
    private String frameworkId;


    public AbstractExporterComponent() {
        registrations = new ConcurrentMapOfSet<ServiceReference, ExportRegistration>();
    }

	/*--------------------------*
	 * EndpointCreator methods. *
	 *--------------------------*/

    /**
     * Create an endpoint for the service linked to {@code sref}.
     * If the service has already an endpoint with compatible properties,
     * return the existing {@link EndpointDescription}, otherwise an exception must be thrown.
     *
     * @param sref The {@link ServiceReference}
     * @return The {@link EndpointDescription} of the created endpoint.
     */
    protected abstract EndpointDescription createEndpoint(ServiceReference sref, Map<String, Object> extraProperties);

    /**
     * Destroy the endpoint of {@code endesc} description.
     *
     * @param endesc The {@link EndpointDescription}
     */
    protected abstract void destroyEndpoint(EndpointDescription endesc);

    /**
     * Stop the endpoint-creator, iPOJO Invalidate instance callback.
     * Must be override !
     * Close all endpoints.
     */
    protected void stop() {

        synchronized (registrations) {
            Collection<ServiceReference> srefs = registrations.keySet();

            for (Iterator<ServiceReference> iterator = srefs.iterator(); iterator.hasNext(); ) {
                ServiceReference ref = iterator.next();
                ExportReference xref = registrations.getElem(ref).getExportReference();
                getRoseMachine().removeLocal(xref); //TODO check !=null
                destroyEndpoint(xref.getExportedEndpoint()); //TODO check != null
                iterator.remove();
            }

            isValid = false;
        }
    }

    /**
     * Start the endpoint-creator component, iPOJO Validate instance callback.
     * Must be override !
     */
    protected void start() {
        synchronized (registrations) {
            isValid = true;
            frameworkId = getRoseMachine().getId(); //Set the machine id

            if (registrations.size() > 0) {
                getLogService().log(LOG_WARNING, "Internal structures have not been cleared while stopping the instance.");
            }
        }
    }

    /**
     * @return <code>true</code> if the {@link ExporterService} is in a valid state, <code>false</code> otherwise.
     */
    protected final boolean isValid() {
        return isValid;
    }
	
	/*--------------------------------------------*
	 *  Convenient access to some useful service. *
	 *--------------------------------------------*/

    /**
     * @return The {@link LogService} service.
     */
    protected abstract LogService getLogService();

	
	/*---------------------------------*
	 *  ExporterService implementation *
	 *---------------------------------*/

    /**
     * @param sref
     * @param extraProperties
     * @return
     */
    public final ExportRegistration exportService(ServiceReference sref, Map<String, ?> extraProperties) {
        ExportRegistration xreg;

        if (!isValid) {
            return new BadExportRegistration(new Throwable("This ExporterService is no more available. !"));
        }

        if (registrations.containsKey(sref)) {
            //Clone Registration
            xreg = new MyExportRegistration(registrations.getElem(sref));
        } else {
            //First registration, create the endpoint
            try {
                EndpointDescription enddesc = createEndpoint(sref, computeEndpointExtraProperties(sref, extraProperties, getConfigPrefix(), frameworkId));
                xreg = new MyExportRegistration(sref, enddesc);
            } catch (Exception e) {
                xreg = new BadExportRegistration(e); //an exception occurred, bad export registration.
            }

            registrations.add(sref, xreg);
        }

        return xreg;
    }


    /*
     * (non-Javadoc)
     * @see org.ow2.chameleon.rose.ExporterService#getExportReference(org.osgi.framework.ServiceReference)
     */
    public ExportReference getExportReference(ServiceReference sref) {
        if (!registrations.containsKey(sref)) {
            return null;
        }
        return registrations.getElem(sref).getExportReference();
    }

    /*
     * (non-Javadoc)
     * @see org.ow2.chameleon.rose.introspect.ExporterIntrospection#getAllExportReference()
     */
    public Collection<ExportReference> getAllExportReference() {
        Collection<ExportReference> xrefs = new HashSet<ExportReference>();
        ExportReference xref;

        for (ServiceReference sref : registrations.keySet()) {
            xref = registrations.getElem(sref).getExportReference();
            if (xref != null) {
                xrefs.add(xref);
            }
        }

        return xrefs;
    }
	
	
	/*--------------------------------*
	 *         INNER CLASS            *
	 *--------------------------------*/

    /**
     * Implementation of an {@link ExportReference}.
     *
     * @author barjo
     */
    private final class MyExportReference implements ExportReference {
        private ServiceReference sref;
        private EndpointDescription desc;

        /**
         * Register the {@code pEndesc} thanks to {@code context}.
         *
         * @param pSref
         * @param pEnddesc
         */
        public MyExportReference(ServiceReference pSref, EndpointDescription pEnddesc) {
            sref = pSref;
            desc = pEnddesc;
        }

        /*
         * (non-Javadoc)
         * @see org.osgi.service.remoteserviceadmin.ExportReference#getExportedService()
         */
        public ServiceReference getExportedService() {
            return sref;
        }

        /*
         * (non-Javadoc)
         * @see org.osgi.service.remoteserviceadmin.ExportReference#getExportedEndpoint()
         */
        public EndpointDescription getExportedEndpoint() {
            return desc;
        }
    }

    /**
     * Implementation of an {@link ExportRegistration}.
     *
     * @author barjo
     */
    private final class MyExportRegistration implements ExportRegistration {
        private volatile ExportReference xref;
        private Throwable exception;

        private MyExportRegistration(ExportRegistration reg) {
            xref = reg.getExportReference();
            exception = reg.getException();

            //Add the registration to the registrations mapOfSet
            registrations.add(reg.getExportReference().getExportedService(), this);
        }

        private MyExportRegistration(ServiceReference sref, EndpointDescription desc) {
            //Create the ExportedReference
            xref = new MyExportReference(sref, desc);

            //Add the registration to the registrations mapOfSet
            registrations.add(xref.getExportedService(), this);

            //register the ExportReference within the ExportRegistry
            getRoseMachine().putLocal(xref, xref);

            //no exception
            exception = null;
        }


        /*
         * (non-Javadoc)
         * @see org.osgi.service.remoteserviceadmin.ExportRegistration#getExportReference()
         */
        public ExportReference getExportReference() {
            return xref;
        }

        /*
         * (non-Javadoc)
         * @see org.osgi.service.remoteserviceadmin.ExportRegistration#close()
         */
        public void close() {
            if (xref != null) {
                // Last registration, remove the ExportReference from the ExportRegistry
                if (registrations.remove(xref.getExportedService(), this)) {
                    try {
                        getRoseMachine().removeLocal(xref);
                    } catch (RuntimeException re) {
                        getLogService().log(LOG_WARNING, "The RoseMachine has probably been stoped", re);
                    }
                    destroyEndpoint(xref.getExportedEndpoint());
                }
                xref = null; // is now closed
            }

            exception = null; // closed :)
        }

        /*
         * (non-Javadoc)
         * @see org.osgi.service.remoteserviceadmin.ExportRegistration#getException()
         */
        public Throwable getException() {
            return exception;
        }
    }

}
