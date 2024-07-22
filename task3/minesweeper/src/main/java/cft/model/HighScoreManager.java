package cft.model;

import cft.common.GameType;
import cft.listeners.NewRecordListener;
import cft.listeners.NewRecordRefreshListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {

    private String nickname;
    private HighScore novice;
    private HighScore medium;
    private HighScore expert;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File noviceFile = new File("NoviceHighScore.json");
    private final File mediumFile = new File("MediumHighScore.json");
    private final File expertFile = new File("ExpertHighScore.json");
    private GameType gameType;
    private int time;

    private final List<NewRecordListener> newRecordListeners = new ArrayList<>();
    private final List<NewRecordRefreshListener> newRecordRefreshListeners = new ArrayList<>();

    public HighScoreManager() {
        uploadRecordsFromFiles();
    }


    public void tryToFireNewRecord(GameType gameType, int time) {
        this.gameType = gameType;
        this.time = time;
        nickname = null;

        switch (gameType) {
            case NOVICE -> {
                if (time < novice.getValue()) {
                    fireOnNewRecord();
                }
            }
            case MEDIUM -> {
                if (time < medium.getValue()) {
                    fireOnNewRecord();
                }
            }
            case EXPERT -> {
                if (time < expert.getValue()) {
                    fireOnNewRecord();
                }
            }
        }
    }


    public void saveRecord(File file, HighScore highScore) {
        try {
            objectMapper.writeValue(file, highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HighScore readRecord(File file) {
        HighScore highScore = null;
        try {
            highScore = objectMapper.readValue(file, HighScore.class);
        } catch (IOException e) {
            highScore = new HighScore();
        }
        if (highScore != null) {
            return highScore;
        } else {
            return new HighScore();
        }
    }

    public void addNewRecordListener(NewRecordListener listener) {
        newRecordListeners.add(listener);
    }

    public void addNewRecordRefreshListener(NewRecordRefreshListener listener) {
        newRecordRefreshListeners.add(listener);
    }

    private void fireOnNewRecord() {
        for (NewRecordListener newRecordListener : newRecordListeners) {
            newRecordListener.onBrokeHighScore();
        }
    }

    private void fireOnNewRecordRefresh() {
        for (NewRecordRefreshListener newRecordRefreshListener : newRecordRefreshListeners) {
            newRecordRefreshListener.onNewRecordRefresh(gameType, nickname, time);
        }
    }

    public void onNewRecordNickname(String name) {
        nickname = name;
        switch (gameType) {
            case NOVICE -> {
                novice = new HighScore(nickname, time);
                saveRecord(noviceFile, novice);
                fireOnNewRecordRefresh();
            }
            case MEDIUM -> {
                medium = new HighScore(nickname, time);
                saveRecord(mediumFile, novice);
                fireOnNewRecordRefresh();
            }
            case EXPERT -> {
                expert = new HighScore(nickname, time);
                saveRecord(expertFile, novice);
                fireOnNewRecordRefresh();
            }
        }
    }

    public void onHighScoreTimer(int time, GameType gameType) {
        tryToFireNewRecord(gameType, time);
    }

    public void onGameInit(GameType gameType) {
        this.gameType = gameType;
        initNotifyRecords();
        nickname = null;
    }

    private void initNotifyRecords() {
        for (NewRecordRefreshListener newRecordRefreshListener : newRecordRefreshListeners) {
            newRecordRefreshListener.onNewRecordRefresh(GameType.NOVICE, novice.getName(), novice.getValue());
            newRecordRefreshListener.onNewRecordRefresh(GameType.MEDIUM, medium.getName(), medium.getValue());
            newRecordRefreshListener.onNewRecordRefresh(GameType.EXPERT, expert.getName(), expert.getValue());
        }
    }

    private void uploadRecordsFromFiles(){
        novice = readRecord(noviceFile);
        medium = readRecord(mediumFile);
        expert = readRecord(expertFile);
    }
}
