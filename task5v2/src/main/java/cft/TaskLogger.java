package cft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  final class TaskLogger {

    private static final Logger logger = LoggerFactory.getLogger(TaskLogger.class);

    private TaskLogger(){
    }

    public static Logger getLogger() {
        return logger;
    }
}


