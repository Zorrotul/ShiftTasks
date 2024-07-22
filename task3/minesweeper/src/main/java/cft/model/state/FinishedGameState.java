package cft.model.state;

public class FinishedGameState implements GameState{
    private final GameContext context;

    public FinishedGameState(GameContext context) {
        this.context = context;
    }

    @Override
    public void tryOpenCell(int x, int y) {
    }

    @Override
    public void tryToggleFlag(int x, int y) {
    }

    @Override
    public void tryOpenNearbyCells(int x, int y) {
    }
}
