package org.osgi.services.scr.checker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.mailboxes.api.Mailbox;

public class MailboxChecker {
	private static final String PERIOD_PROPERTY = "period";
	private static final long DEFAULT_SCAN_PERIOD = 1000;
	
	private long period = DEFAULT_SCAN_PERIOD;
	private final List<Mailbox> mailboxes = new CopyOnWriteArrayList<Mailbox>();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final AtomicReference<ScheduledFuture<?>> runningTask = new AtomicReference<ScheduledFuture<?>>();;
	
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
	
	protected void activate(Map<String, Object> configuration) {
		if (configuration.containsKey(PERIOD_PROPERTY)) {
			period = (Long) configuration.get(PERIOD_PROPERTY);
		} else {
			period = DEFAULT_SCAN_PERIOD;
		}
		
		System.out.println("Activating component. Period = " + period);
		
		ScheduledFuture<?> newTask = scheduler.scheduleWithFixedDelay(new CheckerTask(), period, period, TimeUnit.MILLISECONDS);
		ScheduledFuture<?> oldTask = runningTask.getAndSet(newTask);
		if (oldTask != null) {
			oldTask.cancel(true);
		}
	}
	
	protected void deactivate() {
		scheduler.shutdown();
	}
	
	public void addMailbox(Mailbox mailbox) {
		mailboxes.add(mailbox);
	}
	
	public void removeMailbox(Mailbox mailbox) {
		mailboxes.remove(mailbox);
	}
}
