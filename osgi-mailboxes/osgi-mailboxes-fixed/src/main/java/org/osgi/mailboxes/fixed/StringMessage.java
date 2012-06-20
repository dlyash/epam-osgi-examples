package org.osgi.mailboxes.fixed;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.osgi.mailboxes.api.Message;

public class StringMessage implements Message {
	private final long id;
	private final String subject;
	private final String text;
	
	public StringMessage(long id, String subject, String test) {
		this.id = id;
		this.subject = subject;
		this.text = test;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getSummary() {
		return subject;
	}

	@Override
	public String getMimeType() {
		return "text/plain";
	}

	@Override
	public InputStream getContent() {
		return new ByteArrayInputStream(text.getBytes());
	}

}
