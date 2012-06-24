package org.osgi.services.mailboxes.pop3;


import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedService;

public class PopMailboxActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Properties properties = new Properties();
		properties.put(Constants.SERVICE_PID, PopMailboxRegistrator.class.getName());
		context.registerService(ManagedService.class.getName(), new PopMailboxRegistrator(context), properties);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}

}
