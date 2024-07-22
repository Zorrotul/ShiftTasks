package cft.model;

import cft.common.GameType;
import cft.listeners.*;
import cft.model.state.FinishedGameState;
import cft.model.state.GameContext;
import cft.model.state.GameState;
import cft.model.state.InitGameState;

import java.util.ArrayList;
import java.util.List;

public class MinerModel {
    private final GameContext gameContext = new GameContextImpl();
    private GameType gameType = GameType.NOVICE;
    private final GameField gameField = new GameField();
    private GameState gameState = new InitGameState(gameContext);

    private final List<OnGameInitListener> gameInitListeners = new ArrayList<>();
    private final List<BombsCountListener> bombsCountListeners = new ArrayList<>();
    private final List<RefreshCellListener> refreshCellListeners = new ArrayList<>();
    private final List<WinGameListener> winGameListeners = new ArrayList<>();
    private final List<LoseGameWindowListener> looseGameListeners = new ArrayList<>();
    private final List<OnGameStartedListener> gameStartedListeners = new ArrayList<>();

    public MinerModel() {
    }

    public void initNewGame() {
        initNewGame(gameType);
    }

    public void initNewGame(GameType gameType) {
        this.gameType = gameType;
        gameField.createNewField(gameType.getWidth(), gameType.getHigh(), gameType.getMinesCount());
        gameState = new InitGameState(gameContext);

        fireOnGameInit(gameType);
        fireOnBombsCountRefresh(gameType.getMinesCount());
    }

    public void tryOpenCell(int x, int y) {
        gameState.tryOpenCell(x, y);
    }

    public void tryToggleFlag(int x, int y) {
        gameState.tryToggleFlag(x, y);
    }

    public void tryOpenNearbyCells(int x, int y) {
        gameState.tryOpenNearbyCells(x, y);
    }

    public void addOnGameInit(OnGameInitListener listener) {
        gameInitListeners.add(listener);
    }

    public void addRefreshCellListener(RefreshCellListener listener) {
        refreshCellListeners.add(listener);
    }

    public void addBombsCountListener(BombsCountListener listener) {
        bombsCountListeners.add(listener);
    }

    public void addWinGameListener(WinGameListener listener) {
        winGameListeners.add(listener);
    }

    public void addLooseGameListener(LoseGameWindowListener listener) {
        looseGameListeners.add(listener);
    }

    public void addOnGameStartedListener(OnGameStartedListener listener) {
        gameStartedListeners.add(listener);
    }


    private void fireOnGameInit(GameType gameType) {
        for (OnGameInitListener listener : gameInitListeners) {
            listener.onGameInit(gameType);
        }
    }

    private void fireOnBombsCountRefresh(int bombsCountLeft) {
        for (BombsCountListener listener : bombsCountListeners) {
            listener.setBombsLeftCount(bombsCountLeft);
        }
    }

    private void fireOnRefreshCell(Cell cell) {
        for (RefreshCellListener listener : refreshCellListeners) {
            listener.onRefreshCell(cell.getX(), cell.getY(), cell.getCellState(), cell.getContent());
        }
    }

    private void fireOnLooseGame() {
        for (LoseGameWindowListener listener : looseGameListeners) {
            listener.showLooseGameWindow();
        }
    }

    private void fireOnWinGame() {
        for (WinGameListener listener : winGameListeners) {
            listener.showWinGameWindow();
        }
    }

    private void fireOnGameStarted() {
        for (OnGameStartedListener listener : gameStartedListeners) {
            listener.onGameStarted();
        }
    }


    private class GameContextImpl implements GameContext {

        @Override
        public void setState(GameState state) {
            gameState = state;
        }

        @Override
        public void startGame(int startX, int startY) {
            gameField.fillBombs(startX, startY);
            fireOnGameStarted();
        }

        @Override
        public void tryToggleCellFlag(int x, int y) {
            gameField.tryToggleFlag(x, y);
            Cell cell = gameField.getCell(x, y);
            fireOnRefreshCell(cell);
            fireOnBombsCountRefresh(gameField.getBombsLeftCount());
        }

        @Override
        public void tryOpenCell(int x, int y) {
            List<Cell> cellList = gameField.tryOpenCellsAndReturnThem(x, y);
            fireAboutOpenSells(cellList);
            winLoseChecks(cellList);
        }

        @Override
        public void tryOpenNearbyCells(int x, int y) {
            List<Cell> cellList = gameField.getNearbyCellsThatCanBeOpened(x, y);
            fireAboutOpenSells(cellList);
            winLoseChecks(cellList);
        }

        private void fireAboutOpenSells(List<Cell> cellsList) {
            for (Cell cell : cellsList) {
                fireOnRefreshCell(cell);
            }
        }

        private void winLoseChecks(List<Cell> cellList) {
            if (gameField.checkCellsForMines(cellList)) {
                setState(new FinishedGameState(gameContext));
                fireAboutOpenSells(gameField.openAllMinesAndGet());
                fireOnLooseGame();
            } else if (gameField.checkToWinGame()) {
                setState(new FinishedGameState(gameContext));
                fireAboutOpenSells(gameField.markAllMines());
                fireOnWinGame();
            }
        }
    }
}
