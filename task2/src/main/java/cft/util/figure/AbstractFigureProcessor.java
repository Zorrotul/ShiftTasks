package cft.util.figure;

import java.math.BigDecimal;

public abstract class AbstractFigureProcessor {

    public AbstractFigure processFigure(BigDecimal[] doubles) {
        validateParameters(doubles);
        return getAbstractFigure(doubles);
    }

    abstract void validateParameters(BigDecimal[] doubles);

    abstract AbstractFigure getAbstractFigure(BigDecimal[] doubles);

    public abstract FigureType getFigureType();

}
