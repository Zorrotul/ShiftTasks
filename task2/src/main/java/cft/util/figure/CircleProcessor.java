package cft.util.figure;

import cft.errors.InputFileException;

import java.math.BigDecimal;
import java.util.Optional;

public class CircleProcessor extends AbstractFigureProcessor {

    public void validateParameters(BigDecimal[] doubles) {
        Optional.ofNullable(doubles)
                .filter(d -> d.length != 1)
                .ifPresent(d -> {
                            throw new InputFileException(String.format(
                                    "For circle figure type expected 1 parameter, but got %s", d.length));
                        }
                );
    }

    @Override
    AbstractFigure getAbstractFigure(BigDecimal[] doubles) {
        return new CircleCharacteristicsCalculator(doubles);
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.CIRCLE;
    }
}
