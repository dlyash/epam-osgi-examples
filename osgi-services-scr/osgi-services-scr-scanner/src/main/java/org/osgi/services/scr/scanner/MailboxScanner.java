package org.osgi.services.scr.scanner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.mailboxes.api.Mailbox;

public class MailboxScanner {
	private static final String PERIOD_PROPERTY = "period";
	private static final long DEFAULT_SCAN_PERIOD = 1000;
	
	private long period = DEFAULT_SCAN_PERIOD;
	private List<Mailbox> mailboxes = new CopyOnWriteArrayList<Mailbox>();
	
	protected void activate(Map<String, Object> configuration) {
		if (configuration.containsKey(PERIOD_PROPERTY)) {
			period = (Long) configuration.get(PERIOD_PROPERTY);
		} else {
			period = DEFAULT_SCAN_PERIOD;
		}
	}
	
	protected void deactivate() {
		
	}
	
	public void addMailbox(Mailbox mailbox) {
		mailboxes.add(mailbox);
	}
	
	public void removeMailbox(Mailbox mailbox) {
		mailboxes.remove(mailbox);
	}
}
