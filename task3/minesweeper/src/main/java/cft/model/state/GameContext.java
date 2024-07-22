package cft.model.state;

public interface GameContext {

    void setState(GameState state);

    void startGame(int startX, int startY);

    void tryToggleCellFlag(int x, int y);

    void tryOpenCell(int x, int y);

    void tryOpenNearbyCells(int x, int y);
}
