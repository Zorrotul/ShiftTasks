package cft;

import cft.configuration.ConfigCreator;
import cft.configuration.Configuration;
import cft.errors.ConfigurationException;
import cft.errors.FileReaderException;
import cft.errors.FileWriterException;
import cft.errors.InputFileException;
import cft.util.FigureReaderFromFile;
import cft.util.InputFigureFileProcessor;
import cft.util.InputFileProcessor;
import cft.util.OutputManagerImpl;
import cft.util.figure.AbstractFigure;
import cft.util.figure.FigureData;
import cft.util.figure.FigureInitializer;

import java.util.List;

import static cft.util.FigureLogger.logger;

public class Main {
    public static void main(String[] args) {

        try {
            Configuration configuration = ConfigCreator.create(args);
            List<String> figureFromFileData = FigureReaderFromFile.init(configuration.inputFileName());
            InputFileProcessor inputFileManager = new InputFigureFileProcessor();
            FigureData figureData = FigureInitializer.initializeFigure(figureFromFileData);
            AbstractFigure figure = inputFileManager.initFigure(figureData);
            OutputManagerImpl outputManager = new OutputManagerImpl(configuration.outputInfoType());
            outputManager.displayFigureInfo(figure.getInfo());
        } catch (ConfigurationException | FileReaderException | FileWriterException | InputFileException |
                 NumberFormatException e) {
            logger.error(e.getMessage(), e);
            System.exit(2);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(2);
        }
    }
}