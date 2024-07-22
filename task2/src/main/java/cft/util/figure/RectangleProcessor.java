package cft.util.figure;

import cft.errors.InputFileException;

import java.math.BigDecimal;
import java.util.Optional;

public class RectangleProcessor extends AbstractFigureProcessor {

    public void validateParameters(BigDecimal[] doubles) {
        Optional.ofNullable(doubles)
                .filter(d -> d.length != 2)
                .ifPresent(d -> {
                            throw new InputFileException(String.format(
                                    "For rectangle figure type expected 2 parameter, but got %s", d.length));
                        }
                );
    }

    @Override
    AbstractFigure getAbstractFigure(BigDecimal[] doubles) {
        return new RectangleCharacteristicsCalculator(doubles);
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.RECTANGLE;
    }
}
