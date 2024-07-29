package cft;

import cft.error.ThreadException;

import java.net.URL;

import static cft.TaskLogger.getLogger;

public class Main {
    public static void main(String[] args) {

        String fileName = "config.properties";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }

        try {
            Configuration cfg = Configuration.setConfiguration(url);
            Storage storage = new Storage(cfg.storageSize);
            ProduceManager produceManager = new ProduceManager(cfg.producerCount, cfg.producerTime, storage);
            produceManager.startProducers();
            ConsumerManager consumerManager = new ConsumerManager(cfg.consumerCount, cfg.consumerTime, storage);
            consumerManager.startConsumers();
        } catch (ThreadException e) {
            getLogger().error("Thread {}, was interrupted", Thread.currentThread().getId());
        }
    }
}