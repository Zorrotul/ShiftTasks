package cft.util;

import cft.errors.InputFileException;
import cft.util.figure.*;

import java.util.ArrayList;
import java.util.List;


public class InputFigureFileProcessor implements InputFileProcessor {

    private final List<AbstractFigureProcessor> abstractFigureProcessor;


    public InputFigureFileProcessor() {
        this.abstractFigureProcessor = new ArrayList<>();
        this.abstractFigureProcessor.add(new CircleProcessor());
        this.abstractFigureProcessor.add(new RectangleProcessor());
        this.abstractFigureProcessor.add(new TriangleProcessor());
    }

    @Override
    public AbstractFigure initFigure(FigureData figure) {
        return abstractFigureProcessor.stream()
                .filter(processor -> processor.getFigureType().equals(figure.figureType()))
                .findFirst()
                .map(processor -> processor.processFigure(figure.doubleValues()))
                .orElseThrow(() -> new InputFileException(String.format(
                        "%s figure type is not supported", figure.figureType().name())));
    }
}

