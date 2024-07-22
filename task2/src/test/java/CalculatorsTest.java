import cft.util.figure.CircleCharacteristicsCalculator;
import cft.util.figure.RectangleCharacteristicsCalculator;
import cft.util.figure.TriangleCharacteristicsCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorsTest {

    static final BigDecimal[] RADIUS = new BigDecimal[]{BigDecimal.ONE};
    static final BigDecimal[] RECTANGLE_SIDES = new BigDecimal[]{BigDecimal.ONE, BigDecimal.valueOf(2)};
    static final BigDecimal[] TRIANGLE_SIDES = new BigDecimal[]{BigDecimal.valueOf(3), BigDecimal.valueOf(3), BigDecimal.valueOf(3)};


    @Test
    void initCirclePerimeter() {
        CircleCharacteristicsCalculator circle = new CircleCharacteristicsCalculator(RADIUS);

        BigDecimal perimeter = circle.calculatePerimeter();

        assertEquals("6.28", perimeter.toString());
    }

    @Test
    void initCircleSquare() {
        CircleCharacteristicsCalculator circle = new CircleCharacteristicsCalculator(RADIUS);

        BigDecimal square = circle.calculateSquare();

        assertEquals("3.14", square.toString());
    }


    @Test
    void initRectanglePerimeter() {
        RectangleCharacteristicsCalculator rectangle = new RectangleCharacteristicsCalculator(RECTANGLE_SIDES);

        BigDecimal perimeter = rectangle.calculatePerimeter();

        assertEquals("6.00", perimeter.toString());
    }

    @Test
    void initRectangleSquare() {
        RectangleCharacteristicsCalculator rectangle = new RectangleCharacteristicsCalculator(RECTANGLE_SIDES);

        BigDecimal value = rectangle.calculateSquare();

        assertEquals("2.00", value.toString());
    }

    @Test
    void initTrianglePerimeter() {
        TriangleCharacteristicsCalculator triangle = new TriangleCharacteristicsCalculator(TRIANGLE_SIDES);

        BigDecimal value = triangle.calculatePerimeter();

        assertEquals("9.00", value.toString());
    }

    @Test
    void initTriangleSquare() {
        TriangleCharacteristicsCalculator triangle = new TriangleCharacteristicsCalculator(TRIANGLE_SIDES);

        BigDecimal value = triangle.calculateSquare();

        assertEquals("3.90", value.toString());
    }


    @Test
    void specialInfoCircle() {
        CircleCharacteristicsCalculator circle = new CircleCharacteristicsCalculator(RADIUS);
        StringBuilder sb = new StringBuilder();
        sb.append("Radius: ").append("1.00").append(" mm").append(System.lineSeparator());
        sb.append("Diameter: ").append("2.00").append(" mm").append(System.lineSeparator());

        String value = circle.getSpecialInfo();

        assertEquals(sb.toString(), value);
    }

    @Test
    void infoCircle() {
        CircleCharacteristicsCalculator circle = new CircleCharacteristicsCalculator(RADIUS);
        StringBuilder sb = new StringBuilder();
        sb.append("Figure type: ").append("CIRCLE").append(System.lineSeparator());
        sb.append("Square: ").append("3.14").append(" sq. mm").append(System.lineSeparator());
        sb.append("Perimeter: ").append("6.28").append(" mm").append(System.lineSeparator());
        sb.append("Radius: ").append("1.00").append(" mm").append(System.lineSeparator());
        sb.append("Diameter: ").append("2.00").append(" mm").append(System.lineSeparator());

        String value = circle.getInfo();

        assertEquals(sb.toString(), value);
    }

    @Test
    void specialInfoRectangle() {
        RectangleCharacteristicsCalculator rectangle = new RectangleCharacteristicsCalculator(RECTANGLE_SIDES);
        StringBuilder sb = new StringBuilder();
        sb.append("One side: ").append("1.00").append(" mm").append(System.lineSeparator());
        sb.append("Second side: ").append("2.00").append(" mm").append(System.lineSeparator());
        sb.append("Diagonal: ").append("2.24").append(" mm").append(System.lineSeparator());

        String value = rectangle.getSpecialInfo();

        assertEquals(sb.toString(), value);
    }

    @Test
    void infoRectangle() {
        RectangleCharacteristicsCalculator rectangle = new RectangleCharacteristicsCalculator(RECTANGLE_SIDES);
        StringBuilder sb = new StringBuilder();
        sb.append("Figure type: ").append("RECTANGLE").append(System.lineSeparator());
        sb.append("Square: ").append("2.00").append(" sq. mm").append(System.lineSeparator());
        sb.append("Perimeter: ").append("6.00").append(" mm").append(System.lineSeparator());
        sb.append("One side: ").append("1.00").append(" mm").append(System.lineSeparator());
        sb.append("Second side: ").append("2.00").append(" mm").append(System.lineSeparator());
        sb.append("Diagonal: ").append("2.24").append(" mm").append(System.lineSeparator());


        String value = rectangle.getInfo();

        assertEquals(sb.toString(), value);
    }

    @Test
    void specialInfoTriangle() {
        TriangleCharacteristicsCalculator rectangle = new TriangleCharacteristicsCalculator(TRIANGLE_SIDES);
        StringBuilder sb = new StringBuilder();
        sb.append("First side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("First side angle: ").append("60").append(" degree").append(System.lineSeparator());
        sb.append("Second side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("Second side angle: ").append("60").append(" degree").append(System.lineSeparator());
        sb.append("Third side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("Third side angle: ").append("60").append(" degree").append(System.lineSeparator());

        String value = rectangle.getSpecialInfo();

        assertEquals(sb.toString(), value);
    }

    @Test
    void infoTriangle() {
        TriangleCharacteristicsCalculator rectangle = new TriangleCharacteristicsCalculator(TRIANGLE_SIDES);
        StringBuilder sb = new StringBuilder();
        sb.append("Figure type: ").append("TRIANGLE").append(System.lineSeparator());
        sb.append("Square: ").append("3.90").append(" sq. mm").append(System.lineSeparator());
        sb.append("Perimeter: ").append("9.00").append(" mm").append(System.lineSeparator());
        sb.append("First side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("First side angle: ").append("60").append(" degree").append(System.lineSeparator());
        sb.append("Second side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("Second side angle: ").append("60").append(" degree").append(System.lineSeparator());
        sb.append("Third side: ").append("3.00").append(" mm").append(System.lineSeparator());
        sb.append("Third side angle: ").append("60").append(" degree").append(System.lineSeparator());

        String value = rectangle.getInfo();

        assertEquals(sb.toString(), value);
    }

}
