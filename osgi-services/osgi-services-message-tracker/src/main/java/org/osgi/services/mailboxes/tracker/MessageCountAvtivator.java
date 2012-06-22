package org.osgi.services.mailboxes.tracker;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.mailboxes.api.Mailbox;
import org.osgi.util.tracker.ServiceTracker;

public class MessageCountAvtivator implements BundleActivator {
	
	private ServiceTracker mailboxTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {
		mailboxTracker = new ServiceTracker(context, Mailbox.class.getName(), null);
		mailboxTracker.open();
		
		printMessageCount();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		mailboxTracker.close();
	}
	
	private void printMessageCount() {
		Mailbox mailbox = (Mailbox) mailboxTracker.getService();
		if (mailbox != null) {
			System.out.printf("There are %d messages in the mailbox.%n", mailbox.getAllMessages().length);
		}
	}
}
