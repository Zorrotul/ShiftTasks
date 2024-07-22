import cft.errors.InputFileException;
import cft.errors.NumberParserException;
import cft.util.figure.FigureData;
import cft.util.figure.FigureInitializer;
import cft.util.figure.FigureType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureInitializerTest {

    static final BigDecimal[] CIRCLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(2)};
    static final BigDecimal[] RECTANGLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(2), BigDecimal.valueOf(4)};
    static final BigDecimal[] TRIANGLE_VALUES = new BigDecimal[]{BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5)};

    @Test
    void initCircleFigure() {
        FigureData figure = FigureInitializer.initializeFigure(List.of("CIRCLE","2"));

        assertEquals(FigureType.CIRCLE, figure.figureType());
        assertEquals(Arrays.toString(CIRCLE_VALUES), Arrays.toString(figure.doubleValues()));
    }

    @Test
    void initRectangleFigure() {
        FigureData figure = FigureInitializer.initializeFigure(List.of("RECTANGLE","2 4"));

        assertEquals(FigureType.RECTANGLE, figure.figureType());
        assertEquals(Arrays.toString(RECTANGLE_VALUES), Arrays.toString(figure.doubleValues()));
    }

    @Test
    void initTriangleFigure() {
        FigureData figure = FigureInitializer.initializeFigure(List.of("TRIANGLE","3 4 5"));

        assertEquals(FigureType.TRIANGLE, figure.figureType());
        assertEquals(Arrays.toString(TRIANGLE_VALUES), Arrays.toString(figure.doubleValues()));
    }

    @Test
    void initInvalidFigureName() {

        InputFileException inputFileException = Assertions.assertThrows(
                InputFileException.class,
                () -> FigureInitializer.initializeFigure(List.of("RHOMBUS", "2"))
        );
        assertEquals("Not supported figure type: RHOMBUS", inputFileException.getMessage());
    }

    @Test
    void initNullName() {

        InputFileException inputFileException = Assertions.assertThrows(
                InputFileException.class,
                () -> FigureInitializer.initializeFigure(List.of("", "2"))
        );
        assertEquals("Not supported figure type: ", inputFileException.getMessage());
    }

    @Test
    void initInvalidNegativeValues() {

        NumberParserException numberParserException = Assertions.assertThrows(
                NumberParserException.class,
                () -> FigureInitializer.initializeFigure(List.of("CIRCLE", "-2"))
        );
        assertEquals("Figure with negative or zero values [-2] does not exist", numberParserException.getMessage());
    }
}
