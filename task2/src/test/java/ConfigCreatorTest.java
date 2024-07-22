import cft.configuration.ConfigCreator;
import cft.configuration.Configuration;
import cft.configuration.OutputInfoType;
import cft.errors.ConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigCreatorTest {

    static final String FILE_NAME = "circleFile.txt";


    @Test
    void initConfigWithOutputToFile() {
        String[] args = {"-f", FILE_NAME};

        Configuration configuration = ConfigCreator.create(args);

        assertEquals(OutputInfoType.FILE, configuration.outputInfoType());
        assertEquals(FILE_NAME, configuration.inputFileName());
    }

    @Test
    void initConfigWithOutputToConsole() {
        String[] args = {"-c", FILE_NAME};

        Configuration configuration = ConfigCreator.create(args);

        assertEquals(OutputInfoType.CONSOLE, configuration.outputInfoType());
        assertEquals(FILE_NAME, configuration.inputFileName());
    }

    @Test
    void initConfigWithoutOutput() {
        String[] args = {FILE_NAME};

        Configuration configuration = ConfigCreator.create(args);

        assertEquals(OutputInfoType.CONSOLE, configuration.outputInfoType());
        assertEquals(FILE_NAME, configuration.inputFileName());
    }

    @Test
    void negativeConfigCreating() {
        String[] args = {FILE_NAME, FILE_NAME};

        ConfigurationException configurationException = Assertions.assertThrows(
                ConfigurationException.class,
                () -> ConfigCreator.create(args)
        );
        assertEquals("Invalid quantity input arguments, expected 1, but got = 2", configurationException.getMessage());
    }

    @Test
    void negativeConfigCreating2() {
        String[] args = {"-f", FILE_NAME, FILE_NAME};

        ConfigurationException configurationException = Assertions.assertThrows(
                ConfigurationException.class,
                () -> ConfigCreator.create(args)
        );
        assertEquals("Invalid quantity input arguments, expected 2, but got = 3", configurationException.getMessage());
    }

}
