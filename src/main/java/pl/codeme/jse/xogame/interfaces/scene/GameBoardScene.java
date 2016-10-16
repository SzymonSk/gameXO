package pl.codeme.jse.xogame.interfaces.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.engine.GameOX;
import pl.codeme.jse.xogame.engine.GameOXException;
import pl.codeme.jse.xogame.engine.GameState.State;
import pl.codeme.jse.xogame.interfaces.events.GameBoardFieldEvent;
import pl.codeme.jse.xogame.interfaces.events.MenuEvent;

public class GameBoardScene extends GameScene {

    public static TextArea logs;

    static {
        if(logs == null) {
            logs = new TextArea();
        }
    }

    @Override
    protected void build() {
        EventHandler<ActionEvent> event = new MenuEvent(gameAction, stage);
        Button newGame = new Button("New");
        newGame.setOnAction(event);
        Button save = new Button("Save");
        save.setOnAction(event);
        Button load = new Button("Load");
        load.setOnAction(event);
        Button exit = new Button("Exit");
        exit.setOnAction(event);

        HBox menu = new HBox(newGame, save, load, exit);
        root.add(menu, 0, 0);

        try {
            root.add(getBoard(), 0, 1); // dodanie planszy gry
        } catch(GameOXException e) {
            e.printStackTrace();
        }

        // dodanie okienka logu
        root.add(logs, 0, 2);
        GameBoardScene.logs.appendText(gameAction.getMessage() + "\n");
    }

    /**
     * Metoda buduje planszę gry
     * 
     *
     * @return Node z planszą gry
     */
    private GridPane getBoard() throws GameOXException {
        GridPane board = new GridPane(); // podstawa planszy gry
        Button endTure = new Button("Next player");
        endTure.setDisable(true);
        endTure.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                gameAction.setCoords((Coordinate)endTure.getUserData());
                stage.hide();
            }

        });

        // dodanie pól planszy
        for(int y = 0; y < GameOX.number.size(); y++) {
            for(int x = 0; x < GameOX.literal.size(); x++) {
                Coordinate key = gameAction.getGameBoard().getKey(x, y);
                char sign = gameAction.getGameBoard().get(key);

                if(sign == ' ' && gameAction.getStatus() == State.RUN) {
                    ObservableList<Character> fieldList = FXCollections.observableArrayList();
                    fieldList.add(' ');
                    fieldList.add(gameAction.getCurrentPlayer().getSign());
                    ComboBox<Character> field = new ComboBox<>();
                    field.setItems(fieldList);
                    field.setUserData(key);
                    field.getSelectionModel().selectFirst();
                    field.setOnAction(new GameBoardFieldEvent(endTure));
                    board.add(field, x, y);
                } else {
                    board.add(new Label(String.valueOf(sign)), x, y);
                }
            }
        }

        board.add(endTure, GameOX.literal.size() - 1, GameOX.number.size());

        return board;
    }

}
