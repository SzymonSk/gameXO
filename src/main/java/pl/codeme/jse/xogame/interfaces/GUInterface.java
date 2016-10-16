package pl.codeme.jse.xogame.interfaces;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.codeme.jse.xogame.engine.Action;
import pl.codeme.jse.xogame.engine.GameOX;
import pl.codeme.jse.xogame.interfaces.scene.GameBoardScene;
import pl.codeme.jse.xogame.interfaces.scene.PlayersScene;

public class GUInterface extends Application implements UserInterface {

    private static GameOX game = GameOX.getInstance();

    @Override
    public String getType() {
        return "GUI";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game.setUI(this);
        Action gameAction = game.send(null); // rozpoczęcir gry
        while(true) {
            if(gameAction.getGameBoard() != null) {
                GameBoardScene boardScene = new GameBoardScene();
                boardScene.setGameAction(gameAction);
                boardScene.show();
            } else {
                PlayersScene playersScene = new PlayersScene();
                playersScene.setGameAction(gameAction);
                playersScene.show();
            }
            gameAction = game.send(gameAction);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
