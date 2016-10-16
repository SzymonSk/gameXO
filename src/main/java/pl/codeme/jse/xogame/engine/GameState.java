package pl.codeme.jse.xogame.engine;

import static pl.codeme.jse.xogame.engine.GameOXException.Error.APP_WRONG_COORDINATE_ERROR;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;

/**
 * Klasa reprezentująca stan gry
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class GameState implements GameStateInterface {

    /**
     * Warianty stanu gry (wygrana, remis, dalsza gra)
     * 
     * @author pawel.apanasewicz@codeme.pl
     *
     */
    public enum State { WIN, PAT, RUN };

    /**
     * Stan planszy gry
     */
    private Map<Coordinate, Character> boardState;

    /**
     * Znak który reprezentuje nie wypełnione pole
     */
    private char defaultSign;

    private int sizeX;
    private int sizeY;

    /**
     * Konstruktor klasy
     * 
     * @param sizeX Szerokość planszy
     * @param sizeY Wysokość planszy
     * @param defaultSign Znak reprezentujący puste pole
     */
    public GameState(int sizeX, int sizeY, char defaultSign) {
        this.defaultSign = defaultSign;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Przygotowanie miejsca na przechowywanie stanu gry
        clear();
    }

    public void clear() {
        boardState = new HashMap<>();
        for(int iy = 0; iy < sizeY; iy++) {
            for(int ix = 0; ix < sizeX; ix++) {
                boardState.put(new Coordinate(ix, iy), defaultSign);
            }
        }
    }

    public Iterator<Coordinate> getCoordinate() {
        return boardState.keySet().iterator();
    }

    /**
     * Ustawienie znaku na planszy
     * 
     * @param coord Koordynat pozycji w postaci łańcucha znaków np. "A2"
     * @param sign Wstawiany znak
     * 
     * @return Wariant stanu gry po wstawieniu znaku
     * @throws GameOXException
     */
    public State set(String coord, char sign) throws GameOXException {
        return set(getKeyByString(coord), sign);
    }

    /**
     * Ustawienie znaku na planszy
     * 
     * @param coord Koordynat pozycji
     * @param sign Wstawiany znak
     * 
     * @return Wariant stanu gry po wstawieniu znaku
     * @throws GameOXException
     */
    public State set(Coordinate coord, char sign) throws GameOXException {
       boardState.put(coord, sign);
       return checkGameState();
    }

    /**
     * @return Czy pplansza jest zapełniona
     */
    private boolean isNotEmpty() {
        Iterator<Coordinate> keys = boardState.keySet().iterator();
        while(keys.hasNext()) {
            char sign = boardState.get(keys.next());
            if(sign == defaultSign) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sprawdzenie stanu rozgrywki
     * 
     * @return Wariant stanu gry po wstawieniu znaku
     * @throws GameOXException
     */
    private State checkGameState() throws GameOXException {
        // sprawdzanie wygranej
        if(
            (!boardState.get(getKey(0, 0)).equals(defaultSign) && boardState.get(getKey(0, 1)).equals(boardState.get(getKey(0, 0))) && boardState.get(getKey(0, 2)).equals(boardState.get(getKey(0, 0)))) ||
            (!boardState.get(getKey(1, 0)).equals(defaultSign) && boardState.get(getKey(1, 1)).equals(boardState.get(getKey(1, 0))) && boardState.get(getKey(1, 2)).equals(boardState.get(getKey(1, 0)))) ||
            (!boardState.get(getKey(2, 0)).equals(defaultSign) && boardState.get(getKey(2, 1)).equals(boardState.get(getKey(2, 0))) && boardState.get(getKey(2, 2)).equals(boardState.get(getKey(2, 0)))) ||
            (!boardState.get(getKey(0, 0)).equals(defaultSign) && boardState.get(getKey(1, 0)).equals(boardState.get(getKey(0, 0))) && boardState.get(getKey(2, 0)).equals(boardState.get(getKey(0, 0)))) ||
            (!boardState.get(getKey(0, 1)).equals(defaultSign) && boardState.get(getKey(1, 1)).equals(boardState.get(getKey(0, 1))) && boardState.get(getKey(2, 1)).equals(boardState.get(getKey(0, 1)))) ||
            (!boardState.get(getKey(0, 2)).equals(defaultSign) && boardState.get(getKey(1, 2)).equals(boardState.get(getKey(0, 2))) && boardState.get(getKey(2, 2)).equals(boardState.get(getKey(0, 2)))) ||
            (!boardState.get(getKey(0, 0)).equals(defaultSign) && boardState.get(getKey(1, 1)).equals(boardState.get(getKey(0, 0))) && boardState.get(getKey(2, 2)).equals(boardState.get(getKey(0, 0)))) ||
            (!boardState.get(getKey(0, 2)).equals(defaultSign) && boardState.get(getKey(1, 1)).equals(boardState.get(getKey(1, 1))) && boardState.get(getKey(2, 0)).equals(boardState.get(getKey(0, 2))))
        ) {
            return State.WIN;
        }

        // sprawdzenie czy remis
        if(isNotEmpty()) {
            return State.PAT;
        }

        // jeśli powyższe warianty się nie sprawdziły można grać dalej
        return State.RUN;
    }

    @Override
    public Coordinate getKey(int x, int y) throws GameOXException {
        Coordinate coordKey = new Coordinate(x, y);
        // pobranie kluczy z mapy
        Iterator<Coordinate> keys = boardState.keySet().iterator();
        while(keys.hasNext()) {
            Coordinate key = keys.next();
            // jeżeli klucz jest równy z podanymi koordynatami zwracamy go
            if(coordKey.equals(key)) {
                return key;
            }
        }

        throw new GameOXException(APP_WRONG_COORDINATE_ERROR);
    }

    /**
     * Pobranie klucza z mapy podająć koordynaty w postaci łańcucha znaków np "A1"
     * 
     * @param strCoord Koordynaty
     * 
     * @return Odpowiadający podanym koordynatom klucz z mapy
     * @throws GameOXException
     */
    private Coordinate getKeyByString(String strCoord) throws GameOXException {
        String sx = strCoord.substring(0, 1);
        String sy = strCoord.substring(1);
        Coordinate coordKey = new Coordinate(sx, sy);
        Iterator<Coordinate> keys = boardState.keySet().iterator(); // pobranie kluczy z mapy
        while(keys.hasNext()) {
            Coordinate key = keys.next();
            // jeżeli klucz jest równy z podanymi koordynatami zwracamy go
            if(coordKey.equals(key)) {
                return key;
            }
        }

        throw new GameOXException(APP_WRONG_COORDINATE_ERROR);
    }

    @Override
    public char get(String coord) throws GameOXException {
        return boardState.get(getKeyByString(coord));
    }

    @Override
    public char get(Coordinate coord) {
        return boardState.get(coord);
    }

	public Set<Coordinate> getCoordinate(Map<Coordinate, Character> boardState) {
		// TODO Auto-generated method stub
		return boardState.keySet();
	}

}
