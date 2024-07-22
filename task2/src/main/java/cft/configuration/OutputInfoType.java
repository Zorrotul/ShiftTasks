package cft.configuration;

public enum OutputInfoType {
    CONSOLE("-c"),
    FILE("-f");

    private final String code;

    OutputInfoType(String code) {
        this.code  = code;
    }

    public String getCode() {
        return code;
    }


}
