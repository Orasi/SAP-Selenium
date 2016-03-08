package com.orasi.exception.automation;

import com.orasi.exception.AutomationException;

@SuppressWarnings("serial")
public class NoKeyFoundException extends AutomationException {
    public NoKeyFoundException(String message) {
	super(message);
    }
}
