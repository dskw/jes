package de.dskw.java.eventsys;

import java.util.concurrent.PriorityBlockingQueue;

public class EventQueue extends PriorityBlockingQueue<Event> {
    private static final long serialVersionUID = 1L;

    public EventQueue() {
        super(2000, EventComparator.getInstance());
    }
}
