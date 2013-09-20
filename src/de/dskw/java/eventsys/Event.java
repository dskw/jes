package de.dskw.java.eventsys;

import java.lang.ref.WeakReference;


public abstract class Event implements Comparable<Event> {
    
    private long endTime;
    private WeakReference<IEventQueueHolder> holder;
    
    public Event(IEventQueueHolder holder) {
        this(holder, 0);
    }
    
    public Event(IEventQueueHolder holder, long delay) {
        this.holder = new WeakReference<IEventQueueHolder>(holder);
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
        if (this.endTime == o.endTime)
            return 0;
        if (this.endTime > o.endTime)
            return 1;
        return -1;
    }

    public boolean isDue() {
        return System.currentTimeMillis() - endTime >= 0;
    }
}
