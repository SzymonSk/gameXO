package pl.codeme.jse.xogame.engine;

import pl.codeme.jse.xogame.engine.GameBoard.Coordinate;

/**
 * Interfejs reprezentujący stan gry przekazywany interfejsowi użytkownika cli
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public interface GameStateInterface {

    /**
     * Metoda pobiera znak z planszy
     * 
     * @param coord Koordynat znaku na planszy w postaci ciagu znaków np. "C2"
     * 
     * @return Znak z podanej pozycji na planszy
     * 
     * @throws GameOXException
     */
    public char get(String coord) throws GameOXException;

    /**
     * Metoda pobiera znak z planszy
     * 
     * @param coord Koordynaty pozycji znaków
     * 
     * @return nak z podanej pozycji na planszy
     */
    public char get(Coordinate coord);

    /**
     * Pobranie klucza z mapy ze stanem gry
     * 
     * @param x pozycja szerokosci (od 0)
     * @param y pozycja wysokości (od 0)
     * 
     * @return Klucz pozycji
     * @throws GameOXException
     */
    public Coordinate getKey(int x, int y)  throws GameOXException;

}
