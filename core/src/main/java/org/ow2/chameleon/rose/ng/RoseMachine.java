package org.ow2.chameleon.rose.ng;

import java.util.Map;
import java.util.Set;

public interface RoseMachine {
    /**
     * System property identifying the host name for this rose machine.
     */
    final static String ROSE_MACHINE_HOST = "host";

    /**
     * TimeStamp
     */
    final static String ROSE_MACHINE_DATE = "date";

    final static String ENDPOINT_LISTENER_INTEREST = "endpoint.listener.interest";

    enum EndpointListenerInterest {
        LOCAL, REMOTE, ALL;
    }

    /**
     * @return The ImporterService linked to this RoseMachine
     */
    Set<ImporterService> getImporters();

    /**
     * @return This rose machine host.
     */
    String getHost();

    /**
     * @return This RoSe machine properties.
     */
    Map<String, Object> getProperties();

}
