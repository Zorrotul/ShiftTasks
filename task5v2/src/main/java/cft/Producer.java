package cft;

import cft.error.ThreadException;

import static cft.TaskLogger.getLogger;

public class Producer implements Runnable {
    private final int time;
    private final Storage storage;
    private final int id;


    public Producer(int time, Storage storage, int id) {
        this.time = time;
        this.storage = storage;
        this.id = id;
    }

    public void run() {
        getLogger().info("Producer started");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(time);
                    storage.addResource(ResourceManager.createNewResource(), id);
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
