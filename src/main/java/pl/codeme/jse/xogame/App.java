package pl.codeme.jse.xogame;

import pl.codeme.jse.xogame.interfaces.Cli;

/**
 * Klasa uruchamiająca grę
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class App {

    /**
     * Metoda startująca interfejs linii komend
     * 
     * @param args Parametry z linii komend
     */
    public static void main(String[] args) {
        new Cli();
    }

}
