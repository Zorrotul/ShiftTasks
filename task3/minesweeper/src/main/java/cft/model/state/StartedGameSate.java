package cft.model.state;

public class StartedGameSate implements GameState {
    private final GameContext context;

    public StartedGameSate(GameContext context) {
        this.context = context;
    }

    @Override
    public void tryOpenCell(int x, int y) {
        context.tryOpenCell(x,y);
    }

    @Override
    public void tryToggleFlag(int x, int y) {
        context.tryToggleCellFlag(x, y);
    }

    @Override
    public void tryOpenNearbyCells(int x, int y) {
        context.tryOpenNearbyCells(x,y);
    }
}
