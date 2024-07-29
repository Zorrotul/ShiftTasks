package cft;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {

    public static void printMessage(String message, MessageType type) {

        switch (type) {
            case CONSOLE -> {
                System.out.printf(message + System.lineSeparator());
            }
            case FILE -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"))) {
                    writer.write(message + System.lineSeparator());
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
