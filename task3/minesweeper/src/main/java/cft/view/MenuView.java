package cft.view;

import cft.common.GameType;

import javax.swing.*;

public class MenuView {
    private final SettingsWindow settingsWindow;
    private final HighScoresWindow highScoresWindow;


    public MenuView(JFrame ownerWindow) {
        this.settingsWindow = new SettingsWindow(ownerWindow);
        this.highScoresWindow = new HighScoresWindow(ownerWindow);
    }

    public void setGameTypeListener(GameTypeListener gameTypeListener) {
        settingsWindow.setGameTypeListener(gameTypeListener);
    }

    public void showSettingsWindow() {
        settingsWindow.setVisible(true);
    }

    public void showHighScoresWindow() {

        highScoresWindow.setVisible(true);
    }

    public void onNewRecordRefresh(GameType gameType, String name, int time) {
        switch (gameType) {
            case NOVICE -> {
                highScoresWindow.setNoviceRecord(name, time);
            }
            case MEDIUM -> {
                highScoresWindow.setMediumRecord(name, time);
            }
            case EXPERT -> {
                highScoresWindow.setExpertRecord(name, time);
            }
        }
    }
}
