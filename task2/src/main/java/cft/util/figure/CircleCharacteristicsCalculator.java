package cft.util.figure;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CircleCharacteristicsCalculator extends AbstractFigure {
    private final BigDecimal radius;

    public CircleCharacteristicsCalculator(BigDecimal[] radius) {
        this.radius = radius[0].setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculatePerimeter() {
        return radius.multiply(BigDecimal.valueOf(2 * Math.PI))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateSquare() {
        return radius.multiply(radius.multiply(BigDecimal.valueOf(Math.PI)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.CIRCLE;
    }

    private BigDecimal getDiameter() {
        return radius.multiply(BigDecimal.valueOf(2));
    }

    @Override
    public String getSpecialInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Radius: ").append(getRadius()).append(" mm").append(System.lineSeparator());
        sb.append("Diameter: ").append(getDiameter()).append(" mm").append(System.lineSeparator());
        return sb.toString();
    }

    private BigDecimal getRadius() {
        return radius;
    }
}