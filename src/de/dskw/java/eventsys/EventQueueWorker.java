package de.dskw.java.eventsys;

import java.util.AbstractQueue;



public class EventQueueWorker implements Runnable {

    private AbstractQueue<Event> queue;
    
    public EventQueueWorker(AbstractQueue<Event> queue) {
        this.queue = queue;
    }
    
    @Override
    public void run() {
        while (!queue.isEmpty() && queue.peek().isDue()) {
            try {
                queue.poll().execute();
                EventSystem.num.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
