package cft.util.figure;


import cft.errors.InputFileException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;


public class TriangleCharacteristicsCalculator extends AbstractFigure {
    private final BigDecimal firstSide;
    private final BigDecimal secondSide;
    private final BigDecimal thirdSide;
    private final DecimalFormat df = new DecimalFormat("#.##");

    public TriangleCharacteristicsCalculator(BigDecimal[] sides) {
        validateTriangleArgs(sides);
        this.firstSide = sides[0].setScale(2, RoundingMode.HALF_UP);
        this.secondSide = sides[1].setScale(2, RoundingMode.HALF_UP);
        this.thirdSide = sides[2].setScale(2, RoundingMode.HALF_UP);

    }

    @Override
    public BigDecimal calculatePerimeter() {
        return firstSide.add(secondSide).add(thirdSide).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateSquare() {
        BigDecimal halfPerimeter = calculatePerimeter().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        return halfPerimeter
                .multiply(halfPerimeter.subtract(firstSide))
                .multiply(halfPerimeter.subtract(secondSide))
                .multiply(halfPerimeter.subtract(thirdSide))
                .sqrt(MathContext.DECIMAL128)
                .setScale(2, RoundingMode.HALF_UP);

    }

    private double calculateAngleAgainstThirdSide() {
        BigDecimal cos = (firstSide.pow(2).add(secondSide.pow(2).subtract(thirdSide.pow(2))))
                .divide(firstSide.multiply(secondSide.multiply(BigDecimal.valueOf(2))), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        return Math.toDegrees(Math.acos(Double.parseDouble(cos.toString())));
    }

    private double calculateAngleAgainstSecondSide() {
        BigDecimal cos = (firstSide.pow(2).add(thirdSide.pow(2).subtract(secondSide.pow(2))))
                .divide(firstSide.multiply(thirdSide.multiply(BigDecimal.valueOf(2))), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        return Math.toDegrees(Math.acos(Double.parseDouble(cos.toString())));

    }

    private double calculateAngleAgainstFirstSide() {
        BigDecimal cos = (secondSide.pow(2).add(thirdSide.pow(2).subtract(firstSide.pow(2))))
                .divide(secondSide.multiply(thirdSide.multiply(BigDecimal.valueOf(2))), RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
        return Math.toDegrees(Math.acos(Double.parseDouble(cos.toString())));

    }

    @Override
    public FigureType getFigureType() {
        return FigureType.TRIANGLE;
    }

    @Override
    public String getSpecialInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("First side: ").append(getFirstSide()).append(" mm").append(System.lineSeparator());
        sb.append("First side angle: ").append(df.format(calculateAngleAgainstFirstSide()).replace(',', '.')).append(" degree").append(System.lineSeparator());
        sb.append("Second side: ").append(getSecondSide()).append(" mm").append(System.lineSeparator());
        sb.append("Second side angle: ").append(df.format(calculateAngleAgainstSecondSide()).replace(',', '.')).append(" degree").append(System.lineSeparator());
        sb.append("Third side: ").append(getThirdSide()).append(" mm").append(System.lineSeparator());
        sb.append("Third side angle: ").append(df.format(calculateAngleAgainstThirdSide()).replace(',', '.')).append(" degree").append(System.lineSeparator());
        return sb.toString();
    }

    private BigDecimal getFirstSide() {
        return firstSide;
    }

    private BigDecimal getSecondSide() {
        return secondSide;
    }

    private BigDecimal getThirdSide() {
        return thirdSide;
    }

    private void validateTriangleArgs(BigDecimal[] sides) {
        BigDecimal maxSide = sides[0].max(sides[1].max(sides[2]));
        if ((sides[0].add(sides[1].add(sides[2])).subtract(maxSide)).compareTo(maxSide) <= 0) {
            throw new InputFileException(String.format(
                    "There is no triangle with such sides: %s", Arrays.toString(sides)));
        }
    }
}
