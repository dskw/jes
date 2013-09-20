package de.dskw.java.eventsys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import example.DebugEvent;
import example.Demo;


public class EventSystem implements Runnable {

    public static AtomicInteger num = new AtomicInteger(0);
    
    private int interval;
    private int threadPoolSize;
    private List<IEventQueueHolder> holders;
    private boolean keepRunning = true;
    
    public EventSystem(int threadPoolSize, int interval) {
        holders = new ArrayList<IEventQueueHolder>(Demo.zones);
        this.threadPoolSize = threadPoolSize;
        this.interval = interval - 100;
    }
    
    public EventSystem(int threadPoolSize) {
        this(threadPoolSize, 1000);
    }
    
    public void add(IEventQueueHolder holder) {
        holders.add(holder);
    }
    
    public void shutdown() {
        keepRunning = false;
    }
    
    @Override
    public void run() {
        while (keepRunning) {
            long time = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
            // use an unmodified list to prevent ConcurrentModification exceptions
            List<IEventQueueHolder> list = Collections.unmodifiableList(holders);
            
            for (int i = 0; i < list.size(); i++) {
                IEventQueueHolder h = list.get(i);
                executor.execute(new EventQueueWorker(h.getQueue()));
            }
            
            // wait for the executor to finish
            executor.shutdown();
            
            while (!executor.isTerminated()) {}
            
            if (DebugEvent.num.get() >= Demo.zones * Demo.events) {
                shutdown();
                return;
            }
            
            time = System.currentTimeMillis() - time;
            System.out.println((num.getAndSet(0)/1000) + "k events in " + time + "ms");
            if (time < interval) {
                try {
                    Thread.sleep(interval - time);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
