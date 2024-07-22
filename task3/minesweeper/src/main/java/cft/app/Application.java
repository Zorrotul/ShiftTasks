package cft.app;

import cft.controller.MinerController;
import cft.model.HighScoreManager;
import cft.model.MinerModel;
import cft.model.Timer;
import cft.view.MenuView;
import cft.view.MinerGameView;

public class Application {
    public static void main(String[] args) {

        MinerModel model = new MinerModel();
        MinerGameView minerView = new MinerGameView();
        Timer timer = new Timer();
        HighScoreManager highScoreManager = new HighScoreManager();
        MinerController controller = new MinerController(model, timer, highScoreManager);
        MenuView menuView = new MenuView(minerView.getOwnerWindow());

        menuView.setGameTypeListener(controller::onGameTypeSettingsChanged);

        timer.addTimeListener(minerView::onTimeUpdate);
        timer.addStopTimerListener(highScoreManager::onHighScoreTimer);

        highScoreManager.addNewRecordListener(minerView::showNewRecordWindow);
        highScoreManager.addNewRecordRefreshListener(menuView::onNewRecordRefresh);

        model.addOnGameInit(highScoreManager::onGameInit);
        model.addOnGameInit(controller::onGameInit);
        model.addOnGameInit(minerView::onGameInit);
        model.addOnGameStartedListener(controller::onGameStarted);
        model.addWinGameListener(controller::onGameWinEnded);
        model.addLooseGameListener(controller::onGameLoseEnded);


        minerView.setSettingsMenuAction(e -> menuView.showSettingsWindow());
        minerView.setHighScoresMenuAction(e -> menuView.showHighScoresWindow());
        minerView.setCellListener(controller::onCellClick);
        minerView.setButtonsNewGameListener(controller::startNewGameClick);
        minerView.setRecordNameListener(controller::onNewRecord);

        model.addRefreshCellListener(minerView::onCellRefresh);
        model.addBombsCountListener(minerView::onBombsCountRefresh);
        model.addWinGameListener(minerView::onWinGame);
        model.addLooseGameListener(minerView::onLoseGame);
        model.initNewGame();

        minerView.showWindow();

    }
}
