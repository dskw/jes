package de.dskw.java.eventsys;

import java.util.concurrent.PriorityBlockingQueue;


public interface IEventQueueHolder {
    PriorityBlockingQueue<Event> getQueue();
    void addEvent(Event e);
}
