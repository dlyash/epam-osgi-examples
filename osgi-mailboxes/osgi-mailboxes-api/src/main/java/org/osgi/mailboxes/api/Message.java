package org.osgi.mailboxes.api;

import java.io.InputStream;

public interface Message {
	long getId();
	
	String getSummary();
	
	String getMimeType();
	
	InputStream getContent();
}
