package org.osgi.services.scr.checker.annotations;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.osgi.mailboxes.api.Mailbox;

@Component(label = "Mailbox checker", metatype = true, immediate = true)
public class MailboxChecker {
	private static final long DEFAULT_SCAN_PERIOD = 1000;
	
	@Property(label = "Check period", longValue = DEFAULT_SCAN_PERIOD)
	private static final String PERIOD_PROPERTY = "period";
	
	private long period = DEFAULT_SCAN_PERIOD;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	@Reference(referenceInterface = Mailbox.class,
			cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, 
			policy = ReferencePolicy.DYNAMIC, 
			bind = "addMailbox", unbind = "removeMailbox")
	private final List<Mailbox> mailboxes = new CopyOnWriteArrayList<Mailbox>();
	
	private class CheckerTask implements Runnable {
		@Override
		public void run() {
			if (mailboxes.size() > 0) {
				int count = 0;
				for(Mailbox mailbox: mailboxes) {
					count += mailbox.getAllMessages().length;
				}
				
				System.out.println("Total messages number: " + count);
			}
		}
	}
	
	public MailboxChecker() {
		System.out.println("Component created.");
	}
	
	@Activate
	protected void activate(Map<String, Object> configuration) {
		if (configuration.containsKey(PERIOD_PROPERTY)) {
			period = (Long) configuration.get(PERIOD_PROPERTY);
		} else {
			period = DEFAULT_SCAN_PERIOD;
		}
		
		System.out.println("Activating component. Period = " + period);
		
		scheduler.scheduleWithFixedDelay(new CheckerTask(), period, period, TimeUnit.MILLISECONDS);
	}
	
	@Deactivate
	protected void deactivate() {
		scheduler.shutdown();
	}
	
	protected void addMailbox(Mailbox mailbox) {
		mailboxes.add(mailbox);
	}
	
	protected void removeMailbox(Mailbox mailbox) {
		mailboxes.remove(mailbox);
	}
}
