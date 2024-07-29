package cft;

import java.util.ArrayList;
import java.util.List;

import static cft.TaskLogger.getLogger;


public class Consumer implements Runnable {
    private final int time;
    private final Storage storage;
    private final int id;
    private final List<Resource> resources;


    public Consumer(int time, Storage storage, int id) {
        this.time = time;
        this.storage = storage;
        this.id = id;
        resources = new ArrayList<>();
    }

    public void run() {
        getLogger().info("Consumer started");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(time);
                    resources.add(storage.takeResource(id));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}