package cft.util.figure;

public enum FigureType {
    CIRCLE("CIRCLE"),
    TRIANGLE("TRIANGLE"),
    RECTANGLE("RECTANGLE");

    private final String code;

    FigureType(String code) {
        this.code = code;
    }
}
