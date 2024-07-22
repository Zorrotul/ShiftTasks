package cft;

import cft.errors.NotWithinRangeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter an integer from 1 to 32");
        String line;
        int numberOfElements;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("can't read input parameter", e);
        }

        try {
            numberOfElements = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("%s is not integer", line), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        if (numberOfElements < 1 || numberOfElements > 32) {
            throw new NotWithinRangeException(String.format("%s is not included in the range from 1 to 32", numberOfElements));
        } else {
            StringPrinter stringPrinter = new StringPrinter(numberOfElements);
            stringPrinter.printTable();
        }

    }
}