package example;

import de.dskw.java.eventsys.EventSystem;

public class ZoneSystem extends EventSystem {

    public ZoneSystem(int threadPoolSize) {
        super(threadPoolSize);
    }
    
    public void spawnZones(int num) {
        for (int i = 0; i < num; i++) {
            Zone z = new Zone();
            z.spawnRandomEvents(Demo.events);
            add(z);
        }
    }

}
