package example;

import java.security.SecureRandom;


public class Demo {

    public static final int threads = 4;
    public static final int zones = 5000 * 2;
    public static final int events = 3000;
    public static final int eventDelay = 60 * 2; // in seconds
    public static SecureRandom rand = new SecureRandom();
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Setup:");
        System.out.println("  thread-pool: " + threads);
        System.out.println("  zones: " + zones);
        System.out.println("  events per zone: " + events);
        System.out.println("  max. delay: " + eventDelay + " seconds");
        System.out.println("creating data...");
        ZoneSystem zs = new ZoneSystem(threads);
        zs.spawnZones(zones);
        System.out.println("running...");
        long start = System.currentTimeMillis();
        
        zs.run();
        
        System.out.println("done in " + ((System.currentTimeMillis() - start) / 1000.0) + " msec");
    }

}
