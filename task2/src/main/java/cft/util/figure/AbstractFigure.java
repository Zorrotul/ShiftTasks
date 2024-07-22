package cft.util.figure;

public abstract class AbstractFigure implements FigureCharacteristicsCalculator {

    abstract String getSpecialInfo();
    
    public String getInfo() {

        StringBuilder sb = new StringBuilder();
        sb.append("Figure type: ").append(getFigureType().toString()).append(System.lineSeparator());
        sb.append("Square: ").append(calculateSquare()).append(" sq. mm").append(System.lineSeparator());
        sb.append("Perimeter: ").append(calculatePerimeter()).append(" mm").append(System.lineSeparator());
        sb.append(getSpecialInfo());
        return sb.toString();
    }

}
