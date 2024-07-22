package cft.model;

import cft.common.GameType;
import cft.listeners.StopTimerListener;
import cft.listeners.TimerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Timer {

    private boolean isStarted = false;
    private int time;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final List<TimerListener> timerListeners = new ArrayList<>();
    private final List<StopTimerListener> stopTimerListeners = new ArrayList<>();
    private ScheduledFuture<?> timerTask;
    private GameType gameType;


    public void startTimer() {
        if (!isStarted) {
            timerTask = scheduler.scheduleAtFixedRate(() -> {
                fireTimerListener(time);
                isStarted = true;
                time++;
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void addTimeListener(TimerListener listener) {
        this.timerListeners.add(listener);
    }

    public void addStopTimerListener(StopTimerListener listener) {
        this.stopTimerListeners.add(listener);
    }

    private void fireTimerListener(int time) {
        for (TimerListener timerListener : timerListeners) {
            timerListener.updateTime(time);
        }
    }

    private void fireStopTimerListener(int time, GameType gameType) {
        for (StopTimerListener stopTimerListener : stopTimerListeners) {
            stopTimerListener.onStopTimer(time, gameType);
        }
    }


    public void onGameStarted() {
        startTimer();
    }

    public void onGameInit(GameType gameType) {
        this.gameType = gameType;
        time = 0;
        if (isStarted && timerTask != null) {
            timerTask.cancel(false);
            isStarted = false;
        }
    }

    public void onGameWinEnded() {
        if (isStarted && timerTask != null) {
            timerTask.cancel(false);
            isStarted = false;
            fireStopTimerListener(time, gameType);
        }
    }

    public void onGameLoseEnded() {
        if (isStarted && timerTask != null) {
            timerTask.cancel(false);
            isStarted = false;
        }
    }
}
