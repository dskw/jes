package de.dskw.java.eventsys;

import java.lang.ref.SoftReference;

public abstract class Event implements Comparable<Event> {

	protected long endTime;
	private SoftReference<IEventQueueHolder> holder;

	public Event(IEventQueueHolder holder) {
		this(holder, 0);
	}

	public Event(IEventQueueHolder holder, long delay) {
		this.holder = new SoftReference<IEventQueueHolder>(holder);
		endTime = System.currentTimeMillis() + delay;
	}

	public long getEndTime() {
		return endTime;
	}

	protected IEventQueueHolder getHolder() {
		return holder.get();
	}

	public void execute() throws Exception {
	}

	@Override
	public int compareTo(Event o) {
		//return Long.compare(endTime, o.endTime);
		// makes no difference as the jvm optimizes the behavior in runtime
		return Long.valueOf(this.endTime).compareTo(o.endTime);
	}

	public boolean isDue() {
		return System.currentTimeMillis() - endTime >= 0;
	}
}
