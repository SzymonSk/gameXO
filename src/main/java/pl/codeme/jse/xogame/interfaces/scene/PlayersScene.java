package pl.codeme.jse.xogame.interfaces.scene;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public final class PlayersScene extends GameScene {

    @Override
    protected void build() {
        stage.setTitle("Gra OX - Players");
        Label question = new Label(gameAction.getMessage());
        root.add(question, 0, 0);
        TextField name = new TextField();
        root.add(name, 0, 1);
        Button next = new Button("next");
        next.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                gameAction.setMessage(name.getText());
                stage.hide();
            }

        });
        root.add(next, 0, 2);
    }

}
