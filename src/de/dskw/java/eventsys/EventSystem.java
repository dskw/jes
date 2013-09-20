package de.dskw.java.eventsys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import example.DebugEvent;
import example.Demo;


public class EventSystem implements Runnable {

    public static AtomicInteger num = new AtomicInteger(0);
    
    private int interval;
    private List<IEventQueueHolder> holders;
    private boolean keepRunning = true;
    private final ExecutorService executor;
    
    public EventSystem(int threadPoolSize, int interval) {
        holders = new ArrayList<IEventQueueHolder>(Demo.zones);
        this.interval = interval - 100;
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }
    
    public EventSystem(int threadPoolSize) {
        this(threadPoolSize, 1000);
    }
    
    public void add(IEventQueueHolder holder) {
        holders.add(holder);
    }
    
    public void shutdown() {
        keepRunning = false;
        // wait for the executor to finish
        executor.shutdown();
    }
    
    @Override
    public void run() {
        // use an unmodified list to prevent ConcurrentModification exceptions
        List<IEventQueueHolder> list = Collections.unmodifiableList(holders);

        Collection<Callable<Void>> runnables = new LinkedList<Callable<Void>>();
        for (int i = 0; i < list.size(); i++) {
            IEventQueueHolder h = list.get(i);
            runnables.add(new EventQueueWorker(h.getQueue()));
        }

    	while (keepRunning) {
            long time = System.nanoTime();
            
            try {
            	List<Future<Void>> futures = executor.invokeAll(runnables);
            	for (Future<Void> future : futures)
            	{
            		future.get();
            	}
            } catch (Exception e) {
            	e.printStackTrace();
            }
            
            if (DebugEvent.num.get() >= Demo.zones * Demo.events) {
                shutdown();
                return;
            }
            
            time = System.nanoTime() - time;
            System.out.println((num.getAndSet(0)/1000) + "k events in " + TimeUnit.NANOSECONDS.toMillis(time) + "ms");
            //if (time < interval) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                }
            //}
        }
    }

}
