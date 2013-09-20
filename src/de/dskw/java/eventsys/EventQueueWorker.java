package de.dskw.java.eventsys;

import java.util.AbstractQueue;
import java.util.concurrent.Callable;



public class EventQueueWorker implements Callable<Void> {

    private AbstractQueue<Event> queue;
    
    public EventQueueWorker(AbstractQueue<Event> queue) {
        this.queue = queue;
    }
    
    @Override
    public Void call() {
        while (!queue.isEmpty() && queue.peek().isDue()) {
            try {
                queue.poll().execute();
                EventSystem.num.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
