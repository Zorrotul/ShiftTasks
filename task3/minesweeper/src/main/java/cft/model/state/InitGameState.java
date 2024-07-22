package cft.model.state;

public class InitGameState implements GameState{
    private final GameContext context;

    public InitGameState(GameContext context) {
        this.context = context;
    }

    @Override
    public void tryOpenCell(int x, int y) {
        context.startGame(x,y);

        StartedGameSate newGameState = new StartedGameSate(context);
        context.setState(newGameState);

        newGameState.tryOpenCell(x,y);
    }

    @Override
    public void tryToggleFlag(int x, int y) {
        context.tryToggleCellFlag(x,y);
    }

    @Override
    public void tryOpenNearbyCells(int x, int y) {
    }
}
