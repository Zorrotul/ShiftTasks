import cft.util.InputFigureFileProcessor;
import cft.util.figure.AbstractFigure;
import cft.util.figure.FigureData;
import cft.util.figure.FigureType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputFigureFileProcessorTest {
    static final BigDecimal[] CIRCLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(2)};
    static final BigDecimal[] RECTANGLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(2), BigDecimal.valueOf(4)};
    static final BigDecimal[] TRIANGLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5)};

    static final FigureData FIGURE_CIRCLE_DATA = new FigureData(FigureType.CIRCLE, CIRCLE_VALUES);
    static final FigureData FIGURE_RECTANGLE_DATA = new FigureData(FigureType.RECTANGLE, RECTANGLE_VALUES);
    static final FigureData FIGURE_TRIANGLE_DATA = new FigureData(FigureType.TRIANGLE, TRIANGLE_VALUES);

    @Test
    void initCircle() {
        InputFigureFileProcessor inputFigureFileProcessor = new InputFigureFileProcessor();
        AbstractFigure figure = inputFigureFileProcessor.initFigure(FIGURE_CIRCLE_DATA);

        assertEquals(FigureType.CIRCLE, figure.getFigureType());
    }

    @Test
    void initRectangle() {
        InputFigureFileProcessor inputFigureFileProcessor = new InputFigureFileProcessor();
        AbstractFigure figure = inputFigureFileProcessor.initFigure(FIGURE_RECTANGLE_DATA);

        assertEquals(FigureType.RECTANGLE, figure.getFigureType());
    }

    @Test
    void initTriangle() {
        InputFigureFileProcessor inputFigureFileProcessor = new InputFigureFileProcessor();
        AbstractFigure figure = inputFigureFileProcessor.initFigure(FIGURE_TRIANGLE_DATA);

        assertEquals(FigureType.TRIANGLE, figure.getFigureType());
    }

}