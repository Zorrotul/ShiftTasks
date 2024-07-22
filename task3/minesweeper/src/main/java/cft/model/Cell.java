package cft.model;

import cft.common.CellContent;
import cft.common.CellState;
import cft.model.error.CellException;

public class Cell {
    private CellState cellState;
    private CellContent content;
    private final int x;
    private final int y;
    private int nearBombsCount;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        cellState = CellState.CLOSED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public CellState getCellState() {
        return cellState;
    }

    public CellContent getContent() {
        return content;
    }

    public void setContent(CellContent content) {
        this.content = content;
    }

    public int getNearBombsCount() {
        return nearBombsCount;
    }

    public void setContentOfNearBombsCount(int nearBombsCount) {
        this.nearBombsCount = nearBombsCount;

        switch (nearBombsCount) {
            case 0 -> {
                content = CellContent.EMPTY;
            }
            case 1 -> {
                content = CellContent.NUM_1;
            }
            case 2 -> {
                content = CellContent.NUM_2;
            }
            case 3 -> {
                content = CellContent.NUM_3;
            }
            case 4 -> {
                content = CellContent.NUM_4;
            }
            case 5 -> {
                content = CellContent.NUM_5;
            }
            case 6 -> {
                content = CellContent.NUM_6;
            }
            case 7 -> {
                content = CellContent.NUM_7;
            }
            case 8 -> {
                content = CellContent.NUM_8;
            }
            default -> throw new CellException(String.format("Near bombs counter out of bounds: %s", nearBombsCount));

        }
    }
}
