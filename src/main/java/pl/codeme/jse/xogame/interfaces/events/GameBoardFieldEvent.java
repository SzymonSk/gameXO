package pl.codeme.jse.xogame.interfaces.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.interfaces.scene.GameBoardScene;

public class GameBoardFieldEvent implements EventHandler<ActionEvent> {

    private Button endTure;

    public GameBoardFieldEvent(Button endTure) {
        this.endTure = endTure;
    }

    @Override
    public void handle(ActionEvent event) {
        @SuppressWarnings("unchecked")
        ComboBox<Character> field = (ComboBox<Character>)event.getSource();
        GameBoardScene.logs.appendText(((Coordinate)field.getUserData()).toString() + "\n");
        GridPane board = (GridPane)field.getParent();
        boolean changed = false;
        for(Node node : board.getChildren()) {
            if(node instanceof ComboBox) {
                @SuppressWarnings("unchecked")
                ComboBox<Character> child = (ComboBox<Character>)node;
                if(!child.equals(field)) {
                    if(field.getSelectionModel().getSelectedItem() == ' ') {
                        child.setDisable(false);
                        endTure.setDisable(true);
                        endTure.setUserData(null);
                        changed = true;
                    } else {
                        child.setDisable(true);
                        endTure.setDisable(false);
                        endTure.setUserData(field.getUserData());
                        changed = true;
                    }
                }
            }
        }
        if(!changed && field.getSelectionModel().getSelectedItem() != ' ') {
            endTure.setDisable(false);
            endTure.setUserData(field.getUserData());
        }
    }

}
