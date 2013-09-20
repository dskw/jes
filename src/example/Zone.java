package example;

import java.util.ArrayList;

import de.dskw.java.eventsys.Event;
import de.dskw.java.eventsys.EventQueue;
import de.dskw.java.eventsys.IEventQueueHolder;

public class Zone implements IEventQueueHolder {

    private EventQueue events;
    private ArrayList<Event> upcoming;
    
    public Zone() {
        events = new EventQueue();
        upcoming = new ArrayList<Event>();
    }
    
    public void spawnRandomEvents(int num) {
        for (int i = 0; i < num; i++) {
            events.add(new DebugEvent(this));
        }
    }

    @Override
    public EventQueue getQueue() {
        if (!upcoming.isEmpty()) {
            events.addAll(upcoming);
            upcoming.clear();
        }
        return events;
    }

    @Override
    public void addEvent(Event e) {
        upcoming.add(e);
    }
}
