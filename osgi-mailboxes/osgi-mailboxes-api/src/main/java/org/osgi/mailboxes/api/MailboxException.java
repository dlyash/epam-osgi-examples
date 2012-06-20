package org.osgi.mailboxes.api;

public class MailboxException extends Exception {
	private static final long serialVersionUID = 1L;

	public MailboxException(String message) {
		super(message);
	}

	public MailboxException(Throwable cause) {
		super(cause);
	}
}
