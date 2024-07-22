package cft.util.figure;

import cft.errors.InputFileException;

import java.math.BigDecimal;
import java.util.Optional;

public class TriangleProcessor extends AbstractFigureProcessor {

    public void validateParameters(BigDecimal[] doubles) {
        Optional.ofNullable(doubles)
                .filter(d -> d.length != 3)
                .ifPresent(d -> {
                            throw new InputFileException(String.format(
                                    "For triangle figure type expected 3 parameter, but got %s", d.length));
                        }
                );
    }

    @Override
    AbstractFigure getAbstractFigure(BigDecimal[] doubles) {
        return new TriangleCharacteristicsCalculator(doubles);
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.TRIANGLE;
    }
}
