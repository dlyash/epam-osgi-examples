package org.osgi.services.mailboxes.fixed;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.mailboxes.api.Mailbox;
import org.osgi.services.mailboxes.fixed.impl.FixedMailbox;

public class FixedMailboxActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(Mailbox.class.getName(), new FixedMailbox(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
}
