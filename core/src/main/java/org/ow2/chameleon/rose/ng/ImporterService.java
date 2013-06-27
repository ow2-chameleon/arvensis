package org.ow2.chameleon.rose.ng;

import org.ow2.chameleon.rose.ng.dto.ImportDeclaration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The component providing this service are capable of creating a proxy thanks
 * to an {@link org.ow2.chameleon.rose.ng.dto.ImportDeclaration}.
 *
 * @author barjo
 */
public interface ImporterService {

	/**
	 * Reify the importDeclaration of given description as a local service.
	 *
	 * @param importDeclaration The {@link org.ow2.chameleon.rose.ng.dto.ImportDeclaration} of the service to be imported.
	 * @param properties optional properties (must be crushed by the description properties if conflict).
	 * @return An {@link org.osgi.service.remoteserviceadmin.ImportRegistration}.
	 */
    // FIXME : what this method should return ?
	Object importService(ImportDeclaration importDeclaration, Map<String, Object> properties);

	/**
	 * @return The configuration prefix used or defined by this {@link org.ow2.chameleon.rose.ng.ImporterService}. (i.e <code>json-rpc,org.jabsorb,jax-rs</code>.
	 */
	List<String> getConfigPrefix();

	/**
     * @return All (TODO) references of services imported through this service.
     */
    // FIXME : what this method should return ?
    Collection<Object> getAllImportReferences();

    /**
     * @return The {@code Linker} linked to this {@code ImporterService}
     */
    Linker getLinker();
}
