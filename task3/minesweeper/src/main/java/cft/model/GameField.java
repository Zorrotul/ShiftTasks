package cft.model;

import cft.common.CellContent;
import cft.common.CellState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {
    private Cell[][] cells;
    private int flagCounter = 0;
    private int bombsCount;
    private int width;
    private int high;

    public void createNewField(int width, int high, int bombsCount) {
        this.flagCounter = 0;
        this.bombsCount = bombsCount;
        this.width = width;
        this.high = high;
        cells = new Cell[width][high];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public void fillBombs(int startX, int startY) {
        tryToFillBombs();

        while (cells[startX][startY].getContent() == CellContent.BOMB) {
            createNewField(width, high, bombsCount);
            tryToFillBombs();
        }
        fillNearCellsBombCount();
    }

    public void tryToggleFlag(int x, int y) {

        switch (cells[x][y].getCellState()) {
            case CLOSED -> {
                cells[x][y].setCellState(CellState.FLAGGED);
                flagCounter++;
            }
            case OPENED -> {
            }
            case FLAGGED -> {
                cells[x][y].setCellState(CellState.CLOSED);
                flagCounter--;
            }
        }
    }

    public List<Cell> tryOpenCellsAndReturnThem(int x, int y) {
        List<Cell> cellList = new ArrayList<>();

        if (cells[x][y].getCellState() == CellState.CLOSED) {
            if (cells[x][y].getContent() != CellContent.EMPTY) {
                cellList.add(openAndReturnCell(x, y));
            } else {
                cellList.addAll(getCellsNearbyEmptyCell(x, y));
            }
        }
        return cellList;
    }

    public List<Cell> getNearbyCellsThatCanBeOpened(int x, int y) {
        if ((cells[x][y].getCellState() != CellState.OPENED)) {
        } else if (countNearFlags(x, y) == cells[x][y].getNearBombsCount()) {
            return getCellsThatMustBeOpened(x, y);
        }
        return new ArrayList<>();
    }


    private Cell openAndReturnCell(int x, int y) {
        cells[x][y].setCellState(CellState.OPENED);
        return cells[x][y];
    }

    public boolean checkToWinGame() {
        int notBombCellsCount = width * high - bombsCount;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                if (cells[x][y].getCellState() == CellState.OPENED) {
                    notBombCellsCount--;
                }
            }
        }
        return notBombCellsCount <= 0;
    }

    private void tryToFillBombs() {
        Random random = new Random();
        int cellsCount = width * high;
        int bombsCount = this.bombsCount;

        int randomNumber;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                randomNumber = random.nextInt(cellsCount);
                if (randomNumber < bombsCount) {
                    bombsCount--;
                    cells[x][y].setContent(CellContent.BOMB);
                } else {
                    cells[x][y].setContent(CellContent.EMPTY);
                }
                cellsCount--;
            }
        }
    }

    private void fillNearCellsBombCount() {

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                if (cells[x][y].getContent() != CellContent.BOMB) {
                    cells[x][y].setContentOfNearBombsCount(countNearBombs(x, y));
                }
            }
        }
    }

    private int countNearBombs(int x, int y) {
        int nearBombsCount = 0;

        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++) {
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, high); j++) {
                if (i != x || j != y) {
                    if (cells[i][j].getContent() == CellContent.BOMB) {
                        nearBombsCount++;
                    }
                }

            }
        }
        return nearBombsCount;
    }

    private List<Cell> getCellsNearbyEmptyCell(int x, int y) {
        List<Cell> cellList = new ArrayList<>();

        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++) {
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, high); j++) {
                if (cells[i][j].getCellState() == CellState.CLOSED) {
                    cellList.add(openAndReturnCell(i, j));
                    if (cells[i][j].getContent() == CellContent.EMPTY) {
                        cellList.addAll(getCellsNearbyEmptyCell(i, j));
                    }
                }
            }
        }

        return cellList;
    }

    private int countNearFlags(int x, int y) {
        int nearFlagsCount = 0;

        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++) {
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, high); j++) {
                if (i != x || j != y) {
                    if (cells[i][j].getCellState() == CellState.FLAGGED) {
                        nearFlagsCount++;
                    }
                }

            }
        }
        return nearFlagsCount;
    }

    private List<Cell> getCellsThatMustBeOpened(int x, int y) {
        List<Cell> mustBeOpenedCells = new ArrayList<>();
        for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, width); i++) {
            for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, high); j++) {
                mustBeOpenedCells.addAll(tryOpenCellsAndReturnThem(i, j));
            }
        }
        return mustBeOpenedCells;
    }

    boolean checkCellsForMines(List<Cell> cellList) {
        for (Cell cell : cellList) {
            if (cell.getContent() == CellContent.BOMB) {
                return true;
            }
        }
        return false;
    }

    public List<Cell> openAllMinesAndGet() {
        List<Cell> cellList = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                if (cells[x][y].getContent() == CellContent.BOMB) {
                    cells[x][y].setCellState(CellState.OPENED);
                    cellList.add(cells[x][y]);
                }
            }
        }
        return cellList;
    }

    public List<Cell> markAllMines() {
        List<Cell> cellList = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < high; y++) {
                if (cells[x][y].getContent() == CellContent.BOMB) {
                    cells[x][y].setCellState(CellState.FLAGGED);
                    cellList.add(cells[x][y]);
                }
            }
        }
        return cellList;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public int getBombsLeftCount() {
        return Math.max(0, bombsCount - flagCounter);
    }
}