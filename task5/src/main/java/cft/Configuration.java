package cft;

import cft.error.ConfigurationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static cft.TaskLogger.getLogger;


public class Configuration {

    final int producerCount;
    final int consumerCount;
    final int producerTime;
    final int consumerTime;
    final int storageSize;

    public Configuration(int producerCount, int consumerCount, int producerTime, int consumerTime, int storageSize) {
        this.producerCount = producerCount;
        this.consumerCount = consumerCount;
        this.producerTime = producerTime;
        this.consumerTime = consumerTime;
        this.storageSize = storageSize;
    }

    public static Configuration setConfiguration(URL filename) {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(filename.getFile())) {
            property.load(fis);
            return new Configuration(Integer.parseInt(property.getProperty("producerCount")),
                    Integer.parseInt(property.getProperty("consumerCount")),
                    Integer.parseInt(property.getProperty("producerTime")),
                    Integer.parseInt(property.getProperty("consumerTime")),
                    Integer.parseInt(property.getProperty("storageSize")));
        } catch (NumberFormatException e) {
            getLogger().error("Invalid parsing int");
            throw new ConfigurationException("Invalid parsing int " + e.getMessage(), e);
        } catch (IOException e) {
            getLogger().error("Properties file is missing!");
        }
        return setDefaultConfiguration();
    }

    private static Configuration setDefaultConfiguration() {
        return new Configuration(1, 1, 1000, 1000, 10);
    }
}
