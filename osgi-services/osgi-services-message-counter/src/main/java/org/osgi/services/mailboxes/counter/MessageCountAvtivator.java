package org.osgi.services.mailboxes.counter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.mailboxes.api.Mailbox;

public class MessageCountAvtivator implements BundleActivator {
	
	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference ref = context.getServiceReference(Mailbox.class.getName());
		if (ref != null) {
			Mailbox mailbox = (Mailbox) context.getService(ref);
			if (mailbox != null) {
				try {
					System.out.printf("There are %d messages in the mailbox.%n", mailbox.getAllMessages().length);
				} finally {
					context.ungetService(ref);
				}
			}
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
}
