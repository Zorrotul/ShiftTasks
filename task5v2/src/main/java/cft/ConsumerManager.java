package cft;

import java.util.ArrayList;
import java.util.List;

public class ConsumerManager {
    private final int consumersCount;
    private final int time;
    private final Storage storage;
    private final List<Consumer> consumers;

    public ConsumerManager(int consumersCount, int time, Storage storage) {
        this.consumersCount = consumersCount;
        this.time = time;
        this.storage = storage;
        this.consumers = new ArrayList<>();
        initConsumers();
    }

    private void initConsumers() {
        for (int i = 0; i < consumersCount; i++) {
            this.consumers.add(new Consumer(this.time, this.storage, i));
        }
    }

    public void startConsumers() {
        for (Consumer c : consumers) {
            Thread consumersThreads = new Thread(c);
            consumersThreads.start();
        }
    }
}
