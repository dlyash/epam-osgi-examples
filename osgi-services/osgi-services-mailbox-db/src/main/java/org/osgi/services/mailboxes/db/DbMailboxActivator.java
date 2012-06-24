package org.osgi.services.mailboxes.db;

import javax.sql.DataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.mailboxes.api.Mailbox;
import org.osgi.services.mailboxes.db.impl.DbMailbox;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class DbMailboxActivator implements BundleActivator {
	private ServiceTracker dataSourceTracker;
	
	private static class DsCustomizer implements ServiceTrackerCustomizer {
		private BundleContext context;
		
		public DsCustomizer(BundleContext context) {
			this.context = context;
		}
		
		@Override
		public Object addingService(ServiceReference reference) {
			DataSource ds = (DataSource) context.getService(reference);
			
			ServiceRegistration registration =  context.registerService(Mailbox.class.getName(), 
					new DbMailbox(ds), null);
			
			return registration;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object serviceRegistration) {
			
		}

		@Override
		public void removedService(ServiceReference reference, Object serviceRegistration) {
			ServiceRegistration registration = (ServiceRegistration) serviceRegistration;
			registration.unregister();
			context.ungetService(reference);
		}
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		dataSourceTracker = new ServiceTracker(context, DataSource.class.getName(), new DsCustomizer(context));
		dataSourceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		dataSourceTracker.close();
	}
	
}
