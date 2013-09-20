package de.dskw.java.eventsys;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    private static EventComparator inst;
    
    public static EventComparator getInstance() {
        if (inst == null)
            inst = new EventComparator();
        return inst;
    }
    
    @Override
    public int compare(Event o1, Event o2) {
        if (o1.getEndTime() == o2.getEndTime())
            return 0;
        if (o1.getEndTime() > o2.getEndTime())
            return 1;
        return -1;
    }

}
