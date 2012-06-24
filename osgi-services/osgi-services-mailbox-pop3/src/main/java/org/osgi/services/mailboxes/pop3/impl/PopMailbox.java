package org.osgi.services.mailboxes.pop3.impl;

import org.osgi.mailboxes.api.Mailbox;
import org.osgi.mailboxes.api.Message;

/**
 * Dummy implementation of POP3 Mailbox
 * @author dmitry
 *
 */
public class PopMailbox implements Mailbox {

	public PopMailbox(String host, int port, String user, String password) {
		
	}
	
	@Override
	public long[] getAllMessages() {
		return new long[0];
	}

	@Override
	public long[] getMesagesSince(long id) {
		return new long[0];
	}

	@Override
	public Message[] getMessages(long[] ids) {
		return new Message[0];
	}
}
