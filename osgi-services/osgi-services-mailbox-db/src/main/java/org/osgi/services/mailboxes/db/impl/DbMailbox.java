package org.osgi.services.mailboxes.db.impl;


import javax.sql.DataSource;

import org.osgi.mailboxes.api.Mailbox;
import org.osgi.mailboxes.api.Message;

public class DbMailbox implements Mailbox {
	@SuppressWarnings("unused")
	private final DataSource dataSource;
	
	public DbMailbox(DataSource dataSource) {
		this.dataSource = dataSource;
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
