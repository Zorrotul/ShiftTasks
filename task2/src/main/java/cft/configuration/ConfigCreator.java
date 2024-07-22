package cft.configuration;

import cft.errors.ConfigurationException;

public final class ConfigCreator {

    public static Configuration create(String[] args) {
        String inputFileName = "";
        OutputInfoType outputInfoType = OutputInfoType.CONSOLE;
        int indexOfInputFilename = 0;

        for (String arg : args) {
            if (arg.equals(OutputInfoType.FILE.getCode())) {
                outputInfoType = OutputInfoType.FILE;
                indexOfInputFilename++;
            } else if (arg.equals(OutputInfoType.CONSOLE.getCode())) {
                indexOfInputFilename++;
            } else if (args.length - indexOfInputFilename > 1) {
                throw new ConfigurationException(String.format(
                        "Invalid quantity input arguments, expected %s, but got = %s", 1 + indexOfInputFilename, args.length));
            }
            inputFileName = args[indexOfInputFilename];
        }

        return new Configuration(inputFileName, outputInfoType);
    }
}
