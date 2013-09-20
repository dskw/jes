package example;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import de.dskw.java.eventsys.Event;
import de.dskw.java.eventsys.IEventQueueHolder;

public class DebugEvent extends Event {
    
	private static final ThreadLocal<Random> random = new ThreadLocal<Random>() {

		@Override
		protected Random initialValue() {
			return new Random();
		}
	};
	
    public static AtomicInteger num = new AtomicInteger();
    
    public DebugEvent(IEventQueueHolder holder) {
        super(holder, Demo.rand.nextInt(Demo.eventDelay) * 1000 + 1000);
    }

    private void calcPi() {
        double pI = 4;
        boolean plus = false;
        int factor = (int)(20 * random.get().nextDouble());
        if (random.get().nextDouble() > 0.9)
            factor *= 200;
        for (int i = 3; i < 10000 * factor; i += 2) {
            if (plus) {
                pI += 4.0 / i;
            } else {
                pI -= 4.0 / i;
            }
            plus = !plus;
        }
        pI = pI * 1;
    }

    @Override
    public void execute() throws Exception {
        calcPi();
        
        num.incrementAndGet();
        if (random.get().nextBoolean()) {
        	this.endTime = Long.valueOf(random.get().nextInt(Demo.eventDelay) * 1000 + 1000);
            getHolder().addEvent(this);
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        DebugEvent event = new DebugEvent(new IEventQueueHolder() {
            @Override
            public void addEvent(Event e) {
            }
            @Override
            public PriorityBlockingQueue<Event> getQueue() {
                return null;
            }
        });
        try {
            long time = System.currentTimeMillis();
            event.execute();
            time = System.currentTimeMillis() - time;
            System.out.println(time);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
    }

}
