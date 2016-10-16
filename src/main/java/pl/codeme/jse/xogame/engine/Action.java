package pl.codeme.jse.xogame.engine;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.engine.GameState.State;

/**
 * Interfejs akcji gry
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public interface Action {

    public GameStateInterface getGameBoard();

    /**
     * @return Komunikat akcji
     */
    public String getMessage();

    /**
     * Ustawienie komunikatu akcji
     * 
     * @param msg Komunikat akcji
     */
    public void setMessage(String msg);

    public Player getCurrentPlayer();

    public void setCoords(Coordinate coords);

    public Coordinate getCoords();

    public void setStatus(State status);

    public State getStatus();

}
