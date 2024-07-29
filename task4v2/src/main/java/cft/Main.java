package cft;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static cft.ConvergentSeriesLogger.logger;

public class Main {
    public static void main(String[] args) {

        System.out.print("Enter value N: ");

        BigDecimal n = null;
        try (Scanner scanner = new Scanner(System.in)) {
            n = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            logger.error("Input value does not match the Decimal regular expression, or is out of range", e);
        } catch (NoSuchElementException e) {
            logger.error("The input value is exhausted", e);
        } catch (IllegalStateException e) {
            logger.error("Cant scan value: scanner was closed", e);
        }
        int numProcessors = Runtime.getRuntime().availableProcessors();
        ThreadManager threadManager = new ThreadManager(n, numProcessors);
        BigDecimal result = threadManager.executeTasks();
        Printer.printMessage(String.format("Result: %s", result), MessageType.FILE);
    }
}