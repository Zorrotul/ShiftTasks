package cft.listeners;

import cft.common.GameType;

public interface NewRecordRefreshListener {
    void onNewRecordRefresh(GameType gameType, String name, int time);
}
