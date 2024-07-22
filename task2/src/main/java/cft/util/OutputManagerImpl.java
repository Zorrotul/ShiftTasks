package cft.util;

import cft.configuration.OutputInfoType;
import cft.errors.FileWriterException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Optional;

public class OutputManagerImpl implements OutputManager {
    private final OutputInfoType outputInfoType;
    private final ArrayList<StandardOpenOption> newFileWriteOptions;

    public OutputManagerImpl(OutputInfoType outputInfoType) {
        this.outputInfoType = outputInfoType;
        this.newFileWriteOptions = new ArrayList<>();
        this.newFileWriteOptions.add(StandardOpenOption.CREATE);
        this.newFileWriteOptions.add(StandardOpenOption.WRITE);
    }

    public void displayFigureInfo(String info) {
        if (this.outputInfoType.equals(OutputInfoType.CONSOLE)) {
            System.out.println(info);
        } else if (this.outputInfoType.equals(OutputInfoType.FILE)) {
            writeLineIntoFile(Path.of("output.txt"), info);
        }
    }

    @Override
    public void writeLineIntoFile(Path path, String line) {
        try (BufferedWriter bw = createFileAndGetWriter(path)) {
            bw.write(line + System.lineSeparator());
        } catch (IOException e) {
            throw new FileWriterException(String.format("Can`t create file of this path: %s", path), e);
        }
    }

    private BufferedWriter createFileAndGetWriter(Path path) throws IOException {
        StandardOpenOption[] newFileWriteOptionsArray = newFileWriteOptions.toArray(new StandardOpenOption[newFileWriteOptions.size()]);
        Optional.ofNullable(path.getParent()).ifPresent(s -> {
            try {
                Files.createDirectories(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return Files.newBufferedWriter(path, StandardCharsets.UTF_8, newFileWriteOptionsArray);
    }
}