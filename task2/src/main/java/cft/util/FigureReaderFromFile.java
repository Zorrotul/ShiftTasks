package cft.util;

import cft.errors.FileReaderException;
import cft.errors.InputFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class FigureReaderFromFile {

    public static List<String> init(String inputFilename) {

        try {
            Path path = Path.of(inputFilename);
            return Files.readAllLines(path);
        } catch (
                NoSuchFileException e) {
            throw new InputFileException(String.format(
                    "No such file = %s", inputFilename), e);
        } catch (
                InvalidPathException e) {
            throw new InputFileException(String.format(
                    "Invalid input filename = %s", inputFilename), e);
        } catch (
                IOException e) {
            throw new FileReaderException(String.format(
                    "Oops something wrong with file: %s", e.getMessage()), e);
        }
    }
}
