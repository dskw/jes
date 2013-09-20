package de.dskw.java.pattern;

public interface Observer {
	
	void update(Notifier notifier, String property, Object oldValue, Object newValue);
	
}
