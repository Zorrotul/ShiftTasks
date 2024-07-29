package cft;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import static cft.ConvergentSeriesLogger.logger;

public class ThreadManager {
    private final BigDecimal n;
    private int quantity;
    private final ExecutorService executorService;
    private final List<Task> tasks;
    private BigDecimal result = BigDecimal.ZERO;

    public ThreadManager(BigDecimal n, int quantity) {
        this.n = n;
        this.quantity = quantity;
        this.tasks = new ArrayList<>();
        this.executorService = createThreadPool(quantity);
    }

    private ExecutorService createThreadPool(int quantity) {
        if (n.compareTo(BigDecimal.valueOf(1000)) < 0) {
            this.quantity = 1;
        }
        return Executors.newFixedThreadPool(quantity);
    }

    public BigDecimal executeTasks() {
        for (int i = 0; i < quantity; i++) {

            Task currentTask = new Task(
                    BigDecimal.valueOf(i).multiply(n).divide(BigDecimal.valueOf(quantity), 0, RoundingMode.UP),
                    (BigDecimal.valueOf(i + 1).multiply(n).divide(BigDecimal.valueOf(quantity), 0, RoundingMode.UP))
                            .min(n));
            tasks.add(currentTask);

            Future<BigDecimal> future = executorService.submit(currentTask);
            try {
                BigDecimal currentResult = future.get();
                result = result.add(currentResult, MathContext.UNLIMITED);
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage());
            }
//            executorService.execute(() -> {
//                BigDecimal currentResult = tasks.get(taskNumber).call();
//                result = result.add(currentResult, MathContext.UNLIMITED);
//            });

        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Calculated result with accuracy ({}) = {}", n.toString(), result);
        return result;

    }

}

