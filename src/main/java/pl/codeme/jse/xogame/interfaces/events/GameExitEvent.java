package pl.codeme.jse.xogame.interfaces.events;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class GameExitEvent implements EventHandler<WindowEvent> {

    @Override
    public void handle(WindowEvent event) {
        System.exit(0);
    }

}
