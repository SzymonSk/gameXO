package pl.codeme.jse.xogame.engine;

import java.util.Iterator;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;

public abstract class Saver {

    public abstract void save(SaveGame game) throws GameOXException;
    public abstract SaveGame load(String name) throws GameOXException;

    public static class SaveGame {

        private String name;
        private GameBoard board;
        private Character currantPlayer;
        protected String json;

        public SaveGame(String json) {
            this.json = json;
        }

        public SaveGame(String name, GameBoard board, Character currantPlayer) {
            this.name = name;
            this.board = board;
            this.currantPlayer = currantPlayer;

            // przygotowanie obiektowej reprezentacji zapisu gry w formacie json
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            jsonBuilder.add("currentPlayer", String.valueOf(currantPlayer));
            JsonArrayBuilder gameStateJson = Json.createArrayBuilder();
            // pobranie kluczy mapy ze stanem gry
            Iterator<Coordinate> keys = board.getBoardCoordinate();
            while(keys.hasNext()) { // pobranie i dodanie do json-a stanu gry
                Coordinate key = keys.next();
                JsonObjectBuilder boardField = Json.createObjectBuilder();
                boardField.add("x", key.getX());
                boardField.add("y", key.getY());
                boardField.add("s", String.valueOf(board.getGameState().get(key)));
                //gameState.add(boardField);
            }
            jsonBuilder.add("gameState", gameStateJson);
        }

    }

}
