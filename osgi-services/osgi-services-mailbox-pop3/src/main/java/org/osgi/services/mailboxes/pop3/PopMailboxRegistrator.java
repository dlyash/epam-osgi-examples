package org.osgi.services.mailboxes.pop3;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.mailboxes.api.Mailbox;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.services.mailboxes.pop3.impl.PopMailbox;

class PopMailboxRegistrator implements ManagedService {
	
	private static final String HOST_PROPERTY = "host";
	private static final String PORT_PROPERTY = "port";
	private static final String PASSWORD_PROPERTY = "password";
	private static final String USER_PROPERTY = "user";
	
	private static final int DEFAULT_PORT = 110;
	
	private final BundleContext context;
	private final AtomicReference<ServiceRegistration> registration = new AtomicReference<ServiceRegistration>();
	
	public PopMailboxRegistrator(BundleContext context) {
		this.context = context;
	}
	
	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		ServiceRegistration newRegistration = null;
		try {
			if (properties != null) {
				String host = (String) properties.get(HOST_PROPERTY);
				String portString = (String) properties.get(PORT_PROPERTY);
				int port = portString != null ? Integer.valueOf(portString) : DEFAULT_PORT;
				String user = (String) properties.get(USER_PROPERTY);
				String password = (String) properties.get(PASSWORD_PROPERTY);
				
				if (host == null) {
					throw new ConfigurationException(HOST_PROPERTY, "Mandatory property");
				}
				
				if (user != null && password == null) {
					throw new ConfigurationException(PASSWORD_PROPERTY, "Must be specified for non-anonymous user");
				}
				
				if (user == null) {
					user = "anonymous";
					password = "anonymous";
				}
				
				System.out.println("POP3 mailbox configured. Applying changes...");
				newRegistration = context.registerService(Mailbox.class.getName(), new PopMailbox(host, port, user, password), null);
			} else {
				System.out.println("Configuration of POP3 mailbox is unavailable.");
			}
		} finally {
			ServiceRegistration oldRegistration = registration.getAndSet(newRegistration);
			if (oldRegistration != null) {
				oldRegistration.unregister();
			}
		}
	}
}