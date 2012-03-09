package org.ow2.chameleon.rose.pubsubhubbub.hub.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.osgi.service.log.LogService;
import org.osgi.service.remoteserviceadmin.EndpointDescription;
import org.ow2.chameleon.json.JSONService;
import org.ow2.chameleon.rose.pubsubhubbub.hub.Registrations;

import static org.ow2.chameleon.rose.pubsubhubbub.constants.PubsubhubbubConstants.HUB_SUBSCRIPTION_UPDATE_ENDPOINT_ADDED;
import static org.ow2.chameleon.rose.pubsubhubbub.constants.PubsubhubbubConstants.HUB_SUBSCRIPTION_UPDATE_ENDPOINT_REMOVED;

/**
 * Keep all informations about registered rss topics with related endpoints and
 * subscribers with related endpoints filters.
 * 
 * @author Bartek
 * 
 */
public class RegistrationsImpl implements Registrations {

	// publisher endpoint description with endpoint descriptions machine id
	private Map<EndpointDescription, String> endpoints;

	// connected publishers (topic rss url with machine id))
	private Map<String, String> publishers;

	// connected subscribers with subscribed endpoints
	private Map<String, EndpointsByFilter> subscribers;
	private ReentrantReadWriteLock lock;

	private SendSubscription sendSubscription;

	/**
	 * Main constructor.
	 * 
	 * @param json
	 * @param logger
	 */
	public RegistrationsImpl(JSONService json, LogService logger) {
		endpoints = new HashMap<EndpointDescription, String>();
		publishers = new HashMap<String, String>();
		subscribers = new ConcurrentHashMap<String, EndpointsByFilter>();
		lock = new ReentrantReadWriteLock();
		sendSubscription = new SendSubscription(logger, json);
	}

	/**
	 * Add new topic (publisher registers a topic).
	 * 
	 * @param rssURL
	 *            publisher rss topic url
	 */
	public final void addTopic(final String rssURL, final String MachineID) {
		lock.writeLock().lock();
		try {
			publishers.put(rssURL, MachineID);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Remove topic (publisher unregisters a topic).
	 * 
	 * @param rssURL
	 *            publisher rss topic url
	 */
	public void removeTopic(String rssUrl) {
		Set<String> subscribers = new HashSet<String>();
		lock.writeLock().lock();
		for (Entry<EndpointDescription, String> endpoint : endpoints.entrySet()) {
			// find all endpoints registered by publisher
			if (endpoint.getValue().equals(publishers.get(rssUrl))) {
				// get subscribers who use this endpoint
				subscribers = this.getSubscribersByEndpoint(endpoint.getKey(),
						false);
				if (subscribers.size() != 0) {
					sendSubscription.sendSubscriptions(subscribers,
							endpoint.getKey(),
							HUB_SUBSCRIPTION_UPDATE_ENDPOINT_REMOVED);
				}
				// remove registration of endpoint
				endpoints.remove(endpoint.getKey());
			}
		}
		lock.writeLock().lock();
	}

	/**
	 * Add endpoint to the topic.
	 * 
	 * @param rssUrl
	 *            publisher rss topic url
	 * @param endp
	 * @param @EndpointDescription description to add
	 */
	public final void addEndpointByTopicRssUrl(final String rssUrl,
			final EndpointDescription endp) {
		Set<String> subscribers;

		lock.writeLock().lock();
		endpoints.put(endp, publishers.get(rssUrl));
		subscribers = getSubscribersByEndpoint(endp, true);
		if (subscribers.size() != 0) {
			sendSubscription.sendSubscriptions(subscribers, endp,
					HUB_SUBSCRIPTION_UPDATE_ENDPOINT_ADDED);
		}
		lock.writeLock().unlock();
	}

	/**
	 * Add endpoint to topic.
	 * 
	 * @param machineID
	 *            publisher machineID
	 * @param endp
	 * @EndpointDescription description to add
	 */
	public final void addEndpointByMachineID(final String machineID,
			final EndpointDescription endp) {
		Set<String> subscribers;

		lock.writeLock().lock();
		endpoints.put(endp, machineID);
		subscribers = getSubscribersByEndpoint(endp, true);
		if (subscribers.size() != 0) {
			sendSubscription.sendSubscriptions(subscribers, endp,
					HUB_SUBSCRIPTION_UPDATE_ENDPOINT_ADDED);
		}
		lock.writeLock().unlock();
	}

	/**
	 * Remove endpoint to topic.
	 * 
	 * @param rssUrl
	 *            publisher rss topic url
	 * @param endp
	 * @EndpointDescription description to remove
	 */
	public final void removeEndpointByTopicRssUrl(final String rssUrl,
			final EndpointDescription endp) {
		Set<String> subscribers;

		lock.writeLock().lock();
		endpoints.remove(endp);
		subscribers = getSubscribersByEndpoint(endp, false);
		if (subscribers.size() != 0) {
			sendSubscription.sendSubscriptions(subscribers, endp,
					HUB_SUBSCRIPTION_UPDATE_ENDPOINT_REMOVED);
		}
		lock.writeLock().unlock();
	}

	/**
	 * Create new subscription with endpoint filter.
	 * 
	 * @param callBackUrl
	 *            subscriber full url address to send notifications
	 * @param endpointFilter
	 *            filter to specify endpoints
	 */
	public final void addSubscriber(final String callBackUrl,
			final String endpointFilter) {
		EndpointsByFilter endpointsByFiler = new EndpointsByFilter(
				endpointFilter);
		lock.writeLock().lock();
		try {
			for (EndpointDescription endpoint : endpoints.keySet()) {
				if (endpoint.matches(endpointFilter))
					endpointsByFiler.addEndpoint(endpoint);
				new HashSet<String>(Arrays.asList(callBackUrl));
				sendSubscription.sendSubscriptions(
						new HashSet<String>(Arrays.asList(callBackUrl)),
						endpoint, HUB_SUBSCRIPTION_UPDATE_ENDPOINT_ADDED);
			}
			subscribers.put(callBackUrl, endpointsByFiler);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * Remove subscription.
	 * 
	 * @param callBackUrl
	 *            subscriber full url address to send notifications
	 */
	public final void removeSubscriber(final String callBackUrl) {
		lock.writeLock().lock();
		try {
			subscribers.remove(callBackUrl);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * Provides all registered @EndpointSescription with MachineID.
	 * 
	 * @return all @EndpointSescription as Map collection, key as index
	 */
	public final Map<EndpointDescription, String> getAllEndpoints() {
		return endpoints;
	}


	private final Set<EndpointDescription> getEndpointsByMachineId(
			String machineID) {
		Set<EndpointDescription> endp = new HashSet<EndpointDescription>();
		for (Entry<EndpointDescription, String> endpointEntry : endpoints
				.entrySet()) {
			if (endpointEntry.getValue().equals(machineID)) {
				endp.add(endpointEntry.getKey());
			}
		}
		return endp;

	}

	private final String getTopicRssUrlByMachineID(String machineID) {

		for (Entry<String, EndpointsByFilter> subscribersEntry : subscribers
				.entrySet()) {
			if (subscribersEntry.getValue().equals(machineID)) {
				return subscribersEntry.getKey();
			}
		}
		return null;
	}

	/**
	 * Provides information about registered subscribers whose endpoints filters
	 * matches given @EndpointDescription.
	 * 
	 * @param endp
	 * @EndpointDescription to search
	 * @param option
	 *            true - matched @EndpointDescription to subscribers, false -
	 *            add remove matched @EndpointDescription to subscribers
	 * @return subscribers with matching endpoint filter
	 */
	private final Set<String> getSubscribersByEndpoint(
			final EndpointDescription endp, boolean option) {
		Set<String> matchedSubscribers = new HashSet<String>();
		for (String subscriber : subscribers.keySet()) {

			if (endp.matches(subscribers.get(subscriber).getFilter())) {
				// check option
				if (option) {
					subscribers.get(subscriber).addEndpoint(endp);
					matchedSubscribers.add(subscriber);
					// check if subscriber still registers given endpoint
				} else if (subscribers.get(subscriber).removeEndpoint(endp)) {
					matchedSubscribers.add(subscriber);
				}
			}
		}
		return matchedSubscribers;
	}



	/**
	 * Keeps connection between particular filter and @EndpointSescription which
	 * satisfies it.
	 * 
	 * @author Bartek
	 * 
	 */
	private static class EndpointsByFilter {
		private String filter;
		private Set<EndpointDescription> matchedEndpoints;

		public EndpointsByFilter(final String pFilter) {
			this.filter = pFilter;
			matchedEndpoints = new HashSet<EndpointDescription>();
		}

		public String getFilter() {
			return filter;
		}

		public void addEndpoint(final EndpointDescription endp) {
			matchedEndpoints.add(endp);
		}

		public boolean removeEndpoint(final EndpointDescription endp) {
			return matchedEndpoints.remove(endp);
		}

		public Set<EndpointDescription> getEndpoints() {
			return matchedEndpoints;
		}

	}

}
