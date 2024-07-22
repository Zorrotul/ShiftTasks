package cft.controller;

import cft.common.GameType;
import cft.model.HighScoreManager;
import cft.model.MinerModel;
import cft.model.Timer;
import cft.view.ButtonType;

import java.awt.event.ActionEvent;

public class MinerController {
    private final MinerModel model;
    private final Timer timer;
    private final HighScoreManager highScoreManager;

    public MinerController(MinerModel model, Timer timer, HighScoreManager highScoreManager) {
        this.model = model;
        this.timer = timer;
        this.highScoreManager = highScoreManager;
    }

    public void startNewGameClick(ActionEvent actionEvent) {
        model.initNewGame();
    }

    public void onCellClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> {
                model.tryOpenCell(x, y);
            }
            case RIGHT_BUTTON -> {
                model.tryToggleFlag(x, y);
            }
            case MIDDLE_BUTTON -> {
                model.tryOpenNearbyCells(x, y);
            }
            default -> throw new IllegalStateException("Unexpected value: " + buttonType);
        }
    }

    public void onGameTypeSettingsChanged(GameType gameType) {
        model.initNewGame(gameType);
    }

    public void onGameInit(GameType gameType) {
        timer.onGameInit(gameType);
    }

    public void onGameStarted() {
        timer.onGameStarted();
    }

    public void onGameWinEnded() {
        timer.onGameWinEnded();
    }

    public void onNewRecord(String name) {
        highScoreManager.onNewRecordNickname(name);
    }

    public void onGameLoseEnded() {
        timer.onGameLoseEnded();
    }
}
