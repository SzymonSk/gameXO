package pl.codeme.jse.xogame.interfaces.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.codeme.jse.xogame.engine.Action;
import pl.codeme.jse.xogame.engine.GameState.State;

public class MenuEvent implements EventHandler<ActionEvent> {

    private Action gameAction;
    private Stage stage;

    public MenuEvent(Action gameAction, Stage stage) {
        this.gameAction = gameAction;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent event) {
        Button button = (Button)event.getSource();
        gameAction.setMessage(button.getText());
        gameAction.setStatus(State.RUN);
        stage.hide();
    }

}
