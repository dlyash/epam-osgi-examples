package org.osgi.mailboxes.api;

public interface Mailbox {
	long[] getAllMessages();
	
	long[] getMesagesSince(long id);
	
	Message[] getMessages(long[] ids);
}
