package cft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLogger {
    private static Logger logger = LoggerFactory.getLogger(ServerLogger.class);

    public static Logger getLogger(){
        return logger;
    }
}
