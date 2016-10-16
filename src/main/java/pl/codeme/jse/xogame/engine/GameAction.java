package pl.codeme.jse.xogame.engine;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.engine.GameState.State;

/**
 * Akcje obsługiwane przez grę
 * 
 * @author pawel.apanasewicz@codeme.pl
 *	@author szymonskura@gmail.com
 */
public enum GameAction implements Action {
    PLAYERS,
    PLAY,
    EXIT,
    SAVE,
    LOAD,
    NEW;

    /**
     * pole komunikatu akcji
     */
    private String msg;
    private GameStateInterface gameState;
    private Player currentPlayer;
    private Coordinate coords;
    private State gameStatus;

    /**
     * Ustawienie stanu gry
     * 
     * @param gameState Stan gry (zaznacczenia na planszy)
     */
    public void setGameBoard(GameStateInterface gameState) {
        this.gameState = gameState;
    }

    @Override
    public GameStateInterface getGameBoard() {
        return gameState;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    @Override
    public Coordinate getCoords() {
        return coords;
    }

    @Override
    public void setStatus(State status) {
        this.gameStatus = status;
    }

    @Override
    public State getStatus() {
        return gameStatus;
    }

}
