package cft.view;

import cft.common.CellContent;
import cft.common.CellState;
import cft.common.GameType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MinerGameView {

    private final MainWindow mainWindow;
    private final LoseWindow loseWindow;
    private final WinWindow winWindow;
    private final RecordsWindow recordsWindow;

    public MinerGameView() {
        mainWindow = new MainWindow();
        loseWindow = new LoseWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);
        recordsWindow = new RecordsWindow(mainWindow);

        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
    }

    public JFrame getOwnerWindow() {
        return mainWindow;
    }

    public void setHighScoresMenuAction(ActionListener listener) {
        mainWindow.setHighScoresMenuAction(listener);
    }

    public void setSettingsMenuAction(ActionListener listener) {
        mainWindow.setSettingsMenuAction(listener);
    }

    public void setCellListener(CellEventListener listener) {
        mainWindow.setCellListener(listener);
    }

    public void showWindow() {
        mainWindow.setVisible(true);
        mainWindow.setTimerValue(0);
    }

    public void onGameInit(GameType gameType) {
        mainWindow.createGameField(gameType.getWidth(), gameType.getHigh());
    }

    public void onBombsCountRefresh(int bombsCount) {
        mainWindow.setBombsCount(bombsCount);
    }

    public void onCellRefresh(int x, int y, CellState cellState, CellContent cellContent) {
        mainWindow.setCellImage(x, y, getGameImage(cellState, cellContent));

    }

    public void setButtonsNewGameListener(ActionListener newGameListener) {
        mainWindow.setNewGameMenuAction(newGameListener);
        loseWindow.setNewGameListener(newGameListener);
        winWindow.setNewGameListener(newGameListener);
    }

    private GameImage getGameImage(CellState cellState, CellContent cellContent) {
        switch (cellState) {
            case CLOSED -> {
                return GameImage.CLOSED;
            }
            case OPENED -> {
                switch (cellContent) {
                    case EMPTY -> {
                        return GameImage.EMPTY;
                    }
                    case NUM_1 -> {
                        return GameImage.NUM_1;
                    }
                    case NUM_2 -> {
                        return GameImage.NUM_2;
                    }
                    case NUM_3 -> {
                        return GameImage.NUM_3;
                    }
                    case NUM_4 -> {
                        return GameImage.NUM_4;
                    }
                    case NUM_5 -> {
                        return GameImage.NUM_5;
                    }
                    case NUM_6 -> {
                        return GameImage.NUM_6;
                    }
                    case NUM_7 -> {
                        return GameImage.NUM_7;
                    }
                    case NUM_8 -> {
                        return GameImage.NUM_8;
                    }
                    case BOMB -> {
                        return GameImage.BOMB_ICON;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + cellContent);
                }
            }
            case FLAGGED -> {
                return GameImage.MARKED;
            }
            default -> throw new IllegalStateException("Unexpected value: " + cellState);
        }
    }

    public void onLoseGame() {
        loseWindow.showWindow();
    }

    public void onWinGame() {
        mainWindow.setBombsCount(0);
        winWindow.showWindow();
    }

    public void setRecordNameListener(RecordNameListener nameListener) {
        recordsWindow.setNameListener(nameListener);
    }

    public void onTimeUpdate(int t) {
        mainWindow.setTimerValue(t);
    }

    public void showNewRecordWindow() {
        recordsWindow.showWindow();
    }
}
