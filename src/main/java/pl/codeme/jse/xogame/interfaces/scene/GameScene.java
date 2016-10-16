package pl.codeme.jse.xogame.interfaces.scene;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.codeme.jse.xogame.engine.Action;
import pl.codeme.jse.xogame.interfaces.events.GameExitEvent;

public abstract class GameScene extends Scene {

    protected GridPane root;
    protected Stage stage;
    protected Action gameAction;

    public GameScene() {
        super(new GridPane()); // wywołanie konstruktora z klasy Scene
        root = (GridPane)this.getRoot(); // pobranie GridPane przekazanego do konstruktora z lini wyżej
        this.stage = new Stage(); // ustawienie nowego stage
        stage.setOnCloseRequest(new GameExitEvent());
    }

    public void setGameAction(Action gameAction) {
        this.gameAction = gameAction;
    }

    protected abstract void build();

    public void show() {
        build();
        stage.setScene(this);

        stage.showAndWait();
    }

}
