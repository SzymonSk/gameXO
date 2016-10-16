package pl.codeme.jse.xogame.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.engine.GameState.State;
import pl.codeme.jse.xogame.interfaces.UserInterface;

/**
 * Silnik gry
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class GameOX {

	private static Logger logger = LogManager.getLogger(GameOX.class);

	/**
	 * Lista liter koordynatów planszy
	 */
	public static List<String> literal = Arrays.asList("A", "B", "C");

	/**
	 * Lista cyfr koordynatów planszy
	 */
	public static List<String> number = Arrays.asList("1", "2", "3");

	/**
	 * Instansja silnika gry (wzorzec singleton)
	 */
	private static GameOX game;

	/**
	 * Dostępne znaki w grze
	 */
	private static char[] signs = { 'O', 'X' };

	/**
	 * Metoda statyczna zwracająca instancję silnika gry (wzorzec singleton)
	 * 
	 * @return Instancja silnika gry
	 */
	public static GameOX getInstance() {
		if (game == null) {
			game = new GameOX();
		}

		return game;
	}

	private UserInterface ui;

	/**
	 * Lista graczy
	 */
	private Player[] players;

	/**
	 * Pozycja aktywnego gracza
	 */
	private int currentPlayerIndex;

	/**
	 * Ilość graczy
	 */
	private int playersCount;

	/**
	 * Plansza gry
	 */
	private GameBoard board;

	/**
	 * Konstruktor silnika gry
	 */
	private GameOX() {
		logger.info("Inicjalizacja gry OX");

		players = new Player[2]; // inicjalizacja listy graczy przygotowanie
									// miejsca dla dwóch graczy
		playersCount = 0; // na starcie jest 0 graczy
		board = new GameBoard();
	}

	public void setUI(UserInterface ui) {
		this.ui = ui;
		logger.info("Podłaczenie interfejsu {}", ui);
	}

	/**
	 * Ustawienie następnego gracza
	 */
	private void setNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == playersCount) {
			currentPlayerIndex = 0;
		}
	}

	/**
     * Metoda komunikacji z silnikiem gry
     * 
     * @param action Wiadomość przysłana do silnika gry
     * 
     * @return Wiadomość skierowana do interfejsu gracza
     */
    public Action send(Action action) {
        // obsługa słów akcji z podanego przez gracza tekstu
        if(playersCount == players.length) {
            if(action != null && action.getMessage().equalsIgnoreCase("save")) {
                action = GameAction.SAVE;
            } else if(action != null && action.getMessage().equalsIgnoreCase("load")) {
                action = GameAction.LOAD;
            } else if(action != null && action.getMessage().equalsIgnoreCase("new")) {
                action = GameAction.NEW;
            } else if(action != null && action.getMessage().equalsIgnoreCase("exit")) {
                action = GameAction.EXIT;
            }
        }

        Action returnAction = action;
        try {
            if(action == null) { // jeżeli przysłany został null dodajemy graczy
                returnAction = GameAction.PLAYERS; // ustawienie akcji dodawania graczy
            }
    
            GameAction gameAction = (GameAction)returnAction; // rzutowanie wiadomości na akcję gry
            String msg = "";
            switch(gameAction) { // obsługa akcji gry
                case EXIT: // wyjście z gry
                    System.exit(0);
                    break;
                case LOAD: // załadowanie zapisanej gry
                    board.clearGameBoard();
                    File loadFile = new File("c:/pliki/save.json");
                    // pobranie json-a z pliku
                    try(JsonReader reader = Json.createReader(new FileInputStream(loadFile))) {
                        JsonObject json = reader.readObject(); // zamiana json-a na reprezentację obiektową
                        logger.debug("Load {}", json.getString("currentPlayer"));
                        System.out.println(json.getString("currentPlayer"));
                        
                        JsonArray gameState = json.getJsonArray("gameState");
                        Iterator<Coordinate> keys = board.getBoardCoordinate();
                        Map<Coordinate, Character> boardState;
                        boardState = new HashMap<>();
                        while(keys.hasNext()){
                            JsonObject coord = gameState.getJsonObject(0);
                            Coordinate key = keys.next();
                            String x = String.valueOf(coord.getInt("x"));
                            char keyX = x.charAt(0);
                            String y = String.valueOf(coord.getInt("y"));
                            char keyY = y.charAt(0);
                            String s = coord.getString("s");
                            char keyS = s.charAt(0);
                            boardState.put(key, keyX);
                            boardState.put(key, keyY);
                            boardState.put(key, keyS);
                        }
                        board.setField(boardState);
                    }
                    
                    // wznowienie gry
                    returnAction = GameAction.PLAY;
                    returnAction.setMessage("Gra została wczytana!\nRuch " + players[currentPlayerIndex].getName() + "( " + players[currentPlayerIndex].getSign() + " ): ");
                    break;
                case NEW: // rozpoczęcie nowej gry
                    board.clearGameBoard();
                    currentPlayerIndex = 0;
                    returnAction = GameAction.PLAY;
                    returnAction.setMessage("Nowa gra!\nRuch " + players[currentPlayerIndex].getName() + "( " + players[currentPlayerIndex].getSign() + " ): ");
                    break;
                case PLAY: // rozgrywka
                    State state;
                    if(ui.getType().equals("GUI")) {
                        Coordinate coord = gameAction.getCoords();
                        state = board.setField(coord, players[currentPlayerIndex].getSign());
                        returnAction.setStatus(state);
                    } else {
                        String coord = gameAction.getMessage();
                        state = board.setField(coord, players[currentPlayerIndex].getSign());
                    }
                    switch(state) {
                        case PAT:
                            msg = "Brak możliwości ruchu REMIS!";
                            break;
                        case RUN:
                            setNextPlayer();
                            msg = "Ruch " + players[currentPlayerIndex].getName() + "( " + players[currentPlayerIndex].getSign() + " ): ";
                            break;
                        case WIN:
                            msg = "Wygrał gracz " + players[currentPlayerIndex].getName();
                            break;
                    }
                    returnAction.setMessage(msg);
                    break;
                case PLAYERS: // ustawianie graczy
                    msg = "Podaj imię gracza "; // ustawienie wiadomosci dla gracza
                    if(gameAction.getMessage() != null) {
                        // dodanie gracza do listy graczy
                        players[playersCount] = new Player(gameAction.getMessage(), GameOX.signs[playersCount]);
                        //logger.info("Gracz dodany {}", playersCount);
                        playersCount++;
                        msg += (playersCount + 1) + ": ";
                        returnAction.setMessage(msg); // ustawienie komunikatu w zwracanej akcji
                        if(playersCount == players.length) { // jeżeli nie podano wszystkich graczy
                            currentPlayerIndex = 0;
                            gameAction = GameAction.PLAY; // jeżeli wszyscy gracze są ustawieni to rozpoczynamy rozgrywkę
                            gameAction.setGameBoard(board.getGameState());
                            gameAction.setStatus(State.RUN);
                            returnAction = gameAction;
                            msg = "Ruch " + players[currentPlayerIndex].getName() + "( " + players[currentPlayerIndex].getSign() + " ): ";
                        }
                    } else {
                        msg += (playersCount + 1) + ": ";
                    }
                    returnAction.setMessage(msg);
                    break;
                case SAVE: // zapisanie gry
                    // uchwyt do pliku zapisu gry
                    File saveFile = new File("c:/pliki/save.json");
                    if(!saveFile.exists()) { // jeżeli plik nie istnieje to tworzymy go
                        saveFile.createNewFile();
                    }

                    // przygotowanie obiektowej reprezentacji zapisu gry w formacie json
                    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
                    jsonBuilder.add("currentPlayer", String.valueOf(players[currentPlayerIndex].getSign()));
                    JsonArrayBuilder gameState = Json.createArrayBuilder();
                    // pobranie kluczy mapy ze stanem gry
                    Iterator<Coordinate> keys = board.getBoardCoordinate();
                    while(keys.hasNext()) { // pobranie i dodanie do json-a stanu gry
                        Coordinate key = keys.next();
                        JsonObjectBuilder boardField = Json.createObjectBuilder();
                        boardField.add("x", key.getX());
                        boardField.add("y", key.getY());
                        boardField.add("s", String.valueOf(board.getGameState().get(key)));
                        gameState.add(boardField);
                    }
                    jsonBuilder.add("gameState", gameState);

                    // otwarcie pliku do zapisu
                    try(FileWriter writer = new FileWriter(saveFile)) {
                        writer.write(jsonBuilder.build().toString()); // zapis json-a do pliku
                    }

                    // wznowienie gry
                    returnAction = GameAction.PLAY;
                    returnAction.setMessage("Gra została zapisana!\nRuch " + players[currentPlayerIndex].getName() + "( " + players[currentPlayerIndex].getSign() + " ): ");
                    break;
                default:
                    break;
            }
        } catch(Exception e) {
            logger.error("Błąd: {}", e.getMessage());
            returnAction.setMessage(e.getMessage());
        }

        if(ui.getType().equals("GUI")) {
            ((GameAction)returnAction).setCurrentPlayer(players[currentPlayerIndex]);
        }

        return returnAction;
    }

}
