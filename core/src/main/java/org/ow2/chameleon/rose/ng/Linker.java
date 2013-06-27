package org.ow2.chameleon.rose.ng;

/**
 * The {@link Linker} are used by the RoseMachine to make the link between the
 * {@link org.ow2.chameleon.rose.ng.dto.ImportDeclaration} and the {@link ImporterService}.
 * A RoseMachine can have multiple {@link Linker}.
 *
 * @author Morgan Martinet
 */
public interface Linker {

    // TODO :
    // ServiceTracker for Importer ?
    // -> Maybe bind all for for the moment

    // BindAll the ImportDeclaration.
    // foreach ImportDeclaration, check if metadata match the filter given exposed by the importer.
}
