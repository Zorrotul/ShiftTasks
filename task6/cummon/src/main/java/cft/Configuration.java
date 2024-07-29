package cft;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private int serverPort;
    private final String serverAddress;

    public Configuration(int port, String serverAddress) {
        this.serverAddress = serverAddress;
        this.serverPort = port;
    }

    public static Configuration getConfigFromProperties(String filePath) {

        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            property.load(fis);
            return new Configuration(
                    Integer.parseInt(property.getProperty("serverPort")),
                    property.getProperty("serverAddress"));
        } catch (IOException e) {
            //throw new MyException();
        } catch (NumberFormatException e) {
            //TODO logger
            //throw new MyException();
        }
        return null;
    }

}
