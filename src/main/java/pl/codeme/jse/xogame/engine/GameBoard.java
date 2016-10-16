package pl.codeme.jse.xogame.engine;

import static pl.codeme.jse.xogame.engine.GameOXException.Error.APP_NOT_EMPTY_FIELD_ERROR;

import java.util.Iterator;
import java.util.Map;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;
import pl.codeme.jse.xogame.engine.GameState.State;

/**
 * Klasa planszy gry
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class GameBoard {

    /**
     * Znak oznaczjący puste pole planszy
     */
    private static char defaultSign = ' ';

    /**
     * Opakowana mapa ze stanem gry
     */
    private GameState gameState;

    /**
     * Konstruktor klasy
     */
    public GameBoard() {
        gameState = new GameState(GameOX.literal.size(), GameOX.number.size(), ' ');
    }

    /**
     * Pobranie stanu gry
     * 
     * @return Stan gry (zaznaczenia na planszy)
     */
    public GameStateInterface getGameState() {
        return gameState;
    }

    /**
     * Pobranie kordynatów planszy
     * 
     * @return Kolekcja kluczy (koordynatów) stanu gry
     */
    public Iterator<Coordinate> getBoardCoordinate() {
        return gameState.getCoordinate();
    }

    /**
     * Wyczyszczenie planszy gry
     */
    public void clearGameBoard() {
        gameState.clear();
    }

    /**
     * Wstawienie znaku na planszy
     * 
     * @param strCoord Koordynat pozycji na mapie w postaci łańcucha znaków np "A2"
     * @param playerSign Znak który ma byś wstawiony na planszy
     * 
     * @return Stan gry
     * @throws GameOXException
     */
    public State setField(String strCoord, char playerSign) throws GameOXException {
        char sign = gameState.get(strCoord);
        if(sign == GameBoard.defaultSign) {
            return gameState.set(strCoord, playerSign);
        } else {
            throw new GameOXException(APP_NOT_EMPTY_FIELD_ERROR);
        }
    }

    public State setField(Coordinate coords, char playerSign) throws GameOXException {
        return gameState.set(coords, playerSign);
    }

    /**
     * Klasa koordynatów planszy gry
     * 
     * @author pawel.apanasewicz@codeme.pl
     *
     */
    public static class Coordinate {

        /**
         * wartość pozycji x
         */
        private int x;

        /**
         * Wartość pozycji y
         */
        private int y;

        /**
         * Konstruktor klasy koordynatów
         * 
         * @param x wartość pozycji x
         * @param y wartość pozycji y
         * @throws GameOXException
         */
        public Coordinate(String x, String y) throws GameOXException {
            try {
                // pobranie liczbowej wartości koordynatu x na podstawie listy liter
                for(int ix = 0; ix < GameOX.literal.size(); ix++) {
                    if(GameOX.literal.get(ix).equals(x)) {
                        this.x = ix;
                        break;
                    }
                }
                for(int iy = 0; iy < GameOX.number.size(); iy++) {
                    if(GameOX.number.get(iy).equals(y)) {
                        this.y = iy;
                        break;
                    }
                }
            } catch(Exception e) {
                throw new GameOXException(GameOXException.Error.ERROR_EXCEPTION, e);
            }
        }

        /**
         * Konstruktor klasy
         * 
         * @param x liczbowa wartość x
         * @param y liczbowa wartość y
         */
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Coordinate coord) {
            return (coord.getX() == x && coord.getY() == y);
        }

        /**
         * @return wartość koordynatu x
         */
        public int getX() {
            return x;
        }

        /**
         * @return wartość koordynatu y
         */
        public int getY() {
            return y;
        }

        public String toString() {
            return "[ x: " + x + " y: " + y + " ]" + this.getClass().getName() + "\n";
        }
    }

	public void setField(Map<Coordinate, Character> boardState) {
		// TODO Auto-generated method stub
		return ;
	}

}
