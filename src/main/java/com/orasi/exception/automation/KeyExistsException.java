package com.orasi.exception.automation;

import com.orasi.exception.AutomationException;

@SuppressWarnings("serial")
public class KeyExistsException extends AutomationException{
    public KeyExistsException(String message) {
	super(message);
    }
}
