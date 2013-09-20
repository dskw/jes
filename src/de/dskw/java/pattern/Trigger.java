package de.dskw.java.pattern;

public abstract class Trigger {

	private Triggerable triggerable;
	
	public Trigger(Triggerable triggerable) {
		setTriggerable(triggerable);
	}
	
	public Triggerable getTriggerable() {
		return this.triggerable;
	}
	
	public void setTriggerable(Triggerable value) {
		this.triggerable = value;
	}
	
	public boolean check() {
		return false;
	}
	
	protected void triggerTriggerable() {
		this.getTriggerable().trigger(this);
	}
	
}
