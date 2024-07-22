package cft;

public class StringPrinter {
    private final int numberOflElements;
    private final int firstColumnWidth;
    private final int columnWidth;
    private final StringBuilder rowSeparator;

    public StringPrinter(int numberOflElements) {
        this.numberOflElements = numberOflElements;
        this.firstColumnWidth = String.valueOf(numberOflElements).length();
        this.columnWidth = String.valueOf(numberOflElements * numberOflElements).length();
        this.rowSeparator = initRowSeparator();
    }


    public void printTable() {
        for (int i = 0; i < numberOflElements + 1; i++) {
            System.out.println(initLine(i));
            System.out.println(rowSeparator);
        }
    }


    private StringBuilder initLine(int lineNumber) {
        StringBuilder lineBuilder = new StringBuilder();
        int printValue;
        if (lineNumber != 0) {
            addSpacesToString(lineBuilder, firstColumnWidth, lineNumber);
            lineBuilder.append(lineNumber);
        } else {
            addSpacesToString(lineBuilder, firstColumnWidth, lineNumber);
            lineBuilder.append(" ");
            lineNumber = 1;
        }

        for (int i = 1; i < numberOflElements + 1; i++) {
            lineBuilder.append("|");
            printValue = i * lineNumber;
            addSpacesToString(lineBuilder, columnWidth, printValue);
            lineBuilder.append(printValue);
        }
        return lineBuilder;
    }


    private StringBuilder initRowSeparator() {
        StringBuilder rowSeparatorBuilder = new StringBuilder();
        rowSeparatorBuilder.append("-".repeat(firstColumnWidth));

        for (int i = 0; i < numberOflElements; i++) {
            rowSeparatorBuilder.append("+");
            rowSeparatorBuilder.append("-".repeat(columnWidth));
        }
        return rowSeparatorBuilder;
    }


    private void addSpacesToString(StringBuilder sb, int width, int value) {
        int numberOfSpaces = width - String.valueOf(value).length();
        sb.append(" ".repeat(numberOfSpaces));
    }
}
