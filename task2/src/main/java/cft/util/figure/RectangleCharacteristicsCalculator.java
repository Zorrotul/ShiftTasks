package cft.util.figure;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RectangleCharacteristicsCalculator extends AbstractFigure {
    private final BigDecimal oneSide;
    private final BigDecimal secondSide;

    public RectangleCharacteristicsCalculator(BigDecimal[] sides) {
        this.oneSide = sides[0].setScale(2, RoundingMode.HALF_UP);
        this.secondSide = sides[1].setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculatePerimeter() {
        return oneSide.multiply(BigDecimal.valueOf(2)).add(secondSide.multiply(BigDecimal.valueOf(2)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateSquare() {
        return oneSide.multiply(secondSide)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.RECTANGLE;
    }

    @Override
    public String getSpecialInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("One side: ").append(getOneSide()).append(" mm").append(System.lineSeparator());
        sb.append("Second side: ").append(getSecondSide()).append(" mm").append(System.lineSeparator());
        sb.append("Diagonal: ").append(calculateDiagonal()).append(" mm").append(System.lineSeparator());
        return sb.toString();
    }

    private BigDecimal calculateDiagonal() {
        return (oneSide.pow(2)).add(secondSide.pow(2))
                .sqrt(MathContext.DECIMAL128)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getOneSide() {
        return oneSide;
    }

    private BigDecimal getSecondSide() {
        return secondSide;
    }

}
