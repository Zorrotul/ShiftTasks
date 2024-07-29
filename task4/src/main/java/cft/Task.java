package cft;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.Callable;

import static cft.ConvergentSeriesLogger.logger;

public class Task implements Callable<BigDecimal> {
    private final BigDecimal startOfInterval;
    private final BigDecimal endOfInterval;

    public Task(BigDecimal startOfInterval, BigDecimal endOfInterval) {

        this.startOfInterval = startOfInterval;
        this.endOfInterval = endOfInterval;
    }

    public BigDecimal call() {
        BigDecimal res = BigDecimal.ZERO;
        for (BigDecimal i = startOfInterval; i.compareTo(endOfInterval) < 0; i = i.add(BigDecimal.ONE)) {
            res = res.add(BigDecimal.ONE.divide(twoInBigDecimalPow(i)), MathContext.UNLIMITED);
        }
        logger.info("Thread calculate value: {}", res.toString());
        return res;
    }

    private BigDecimal twoInBigDecimalPow(BigDecimal pov) {
        BigDecimal res = BigDecimal.ONE;
        for (BigDecimal i = BigDecimal.ZERO; i.compareTo(pov) < 0; i = i.add(BigDecimal.ONE)) {
            res = res.multiply(BigDecimal.valueOf(2), MathContext.UNLIMITED);
        }
        return res;
    }

}
