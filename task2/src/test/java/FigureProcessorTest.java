import cft.errors.InputFileException;
import cft.util.figure.AbstractFigureProcessor;
import cft.util.figure.CircleProcessor;
import cft.util.figure.RectangleProcessor;
import cft.util.figure.TriangleProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureProcessorTest {

    static final BigDecimal[] CIRCLE_WRONG_DOUBLES = new BigDecimal[]{
            BigDecimal.valueOf(2), BigDecimal.valueOf(1)};
    static final BigDecimal[] RECTANGLE_WRONG_DOUBLES = new BigDecimal[]{
            BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5)};
    static final BigDecimal[] TRIANGLE_WRONG_DOUBLES = new BigDecimal[]{
            BigDecimal.valueOf(3), BigDecimal.valueOf(4)};


    @Test
    void initInvalidCircleValues() {
        AbstractFigureProcessor processor = new CircleProcessor();

        InputFileException inputFileException = Assertions.assertThrows(
                InputFileException.class,
                () -> processor.processFigure(CIRCLE_WRONG_DOUBLES)
        );

        assertEquals("For circle figure type expected 1 parameter, but got 2", inputFileException.getMessage());
    }

    @Test
    void initInvalidRectangleValues() {
        AbstractFigureProcessor processor = new RectangleProcessor();

        InputFileException inputFileException = Assertions.assertThrows(
                InputFileException.class,
                () -> processor.processFigure(RECTANGLE_WRONG_DOUBLES)
        );

        assertEquals("For rectangle figure type expected 2 parameter, but got 3", inputFileException.getMessage());
    }

    @Test
    void initInvalidTriangleValues() {
        AbstractFigureProcessor processor = new TriangleProcessor();

        InputFileException inputFileException = Assertions.assertThrows(
                InputFileException.class,
                () -> processor.processFigure(TRIANGLE_WRONG_DOUBLES)
        );

        assertEquals("For triangle figure type expected 3 parameter, but got 2", inputFileException.getMessage());
    }
}