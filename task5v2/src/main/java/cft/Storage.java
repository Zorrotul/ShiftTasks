package cft;

import cft.error.ThreadException;

import java.time.LocalDateTime;
import java.util.*;

import static cft.TaskLogger.getLogger;


public class Storage {
    private final int size;
    private final Deque<Resource> resources;

    public Storage(int size) {
        this.size = size;
        resources = new ArrayDeque<>();
    }

    public synchronized void addResource(Resource resource, int currentProducerId) {

        while (resources.size()>=size) {
            getLogger().info("Time:[{}],Storage is fuel, Producer: [{}], [Waiting], storage size: [{}]",
                    LocalDateTime.now(),
                    currentProducerId,
                    resources.size());
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ThreadException(e.getMessage(), e);
            }
        }
        resources.add(resource);
        getLogger().info("Time:[{}], New resource added, Producer: [{}], [Produced], Resource[{}], storage size: [{}]",
                LocalDateTime.now(),
                currentProducerId,
                resource.id(),
                resources.size());
        notifyAll();
    }


    public synchronized Resource takeResource(int currentConsumerId) {

        while (resources.isEmpty()) {
            getLogger().info("Time:[{}],Storage is empty, Consumer: [{}], [Waiting], storage size: [{}]",
                    LocalDateTime.now(),
                    currentConsumerId,
                    resources.size());
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ThreadException(e.getMessage(), e);
            }
        }
        Resource resource = resources.poll();
        getLogger().info("Time:[{}], resource was consumed, Consumer: [{}], [Consumed], Resource[{}], storage size: [{}]",
                LocalDateTime.now(),
                currentConsumerId,
                resource.id(),
                resources.size());
        notifyAll();
        return resource;
    }

}
