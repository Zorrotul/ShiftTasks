package cft.model.state;

public interface GameState {

    void tryOpenCell(int x, int y);

    void tryToggleFlag(int x, int y);

    void tryOpenNearbyCells(int x, int y);
}
