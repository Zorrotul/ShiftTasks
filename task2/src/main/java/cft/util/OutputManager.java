package cft.util;

import java.nio.file.Path;

public interface OutputManager {
    void displayFigureInfo(String info);

    void writeLineIntoFile(Path path, String line);
}
