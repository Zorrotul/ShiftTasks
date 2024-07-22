package cft.util.figure;

import java.math.BigDecimal;

public interface FigureCharacteristicsCalculator {

    FigureType getFigureType();

    BigDecimal calculatePerimeter();

    BigDecimal calculateSquare();
}
