package cft.util.figure;

import cft.errors.InputFileException;
import cft.errors.NumberParserException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record FigureInitializer(FigureType figureType, BigDecimal[] doubleValues) {

    public static FigureData initializeFigure(List<String> figureData) {
        FigureType figureType = null;
        BigDecimal[] doubleValues = null;
        try {
            figureType = FigureType.valueOf(figureData.get(0));
            doubleValues = parseStringToDouble(figureData.get(1));
            validateDouble(doubleValues);
        } catch (IllegalArgumentException e) {
            throw new InputFileException(String.format("Not supported figure type: %s", figureData.get(0)), e);
        }
        return new FigureData(figureType, doubleValues);
    }

    private static BigDecimal[] parseStringToDouble(String string) {
        try {
            return Arrays.stream(
                            string.split(" "))
                    .map(BigDecimal::new)
                    .toArray(BigDecimal[]::new);
        } catch (NumberFormatException e) {
            throw new NumberParserException(String.format("Not all values in this line = %s is double", string), e);
        }
    }

    private static void validateDouble(BigDecimal[] doubleValues) {
        if (Stream.of(doubleValues).anyMatch(s -> s.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new NumberParserException(String.format("Figure with negative or zero values %s does not exist", Arrays.toString(doubleValues)));
        }
    }

}
