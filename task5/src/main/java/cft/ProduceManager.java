package cft;

import java.util.ArrayList;
import java.util.List;

public class ProduceManager {

    private final int producersCount;
    private final int time;
    private final Storage storage;
    private final List<Producer> producers;

    public ProduceManager(int producersCount, int time, Storage storage) {
        this.producersCount = producersCount;
        this.time = time;
        this.storage = storage;
        this.producers = new ArrayList<>();
        initProducers();
    }

    private void initProducers() {
        for (int i = 0; i < producersCount; i++) {
            this.producers.add(new Producer(this.time, this.storage, i));
        }
    }

    public void startProducers() {
        for (Producer p : producers) {
            Thread produceThreads = new Thread(p);
            produceThreads.start();
        }
    }
}
