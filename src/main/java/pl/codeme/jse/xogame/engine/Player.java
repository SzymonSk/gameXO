package pl.codeme.jse.xogame.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Klasa reprezentująa gracza
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class Player {

    private static Logger logger = LogManager.getLogger(Player.class);

    /**
     * Imię gracza
     */
    private String name;

    /**
     * Znak gracza którym będzie prowadził rozgrywkę
     */
    private char   sign;

    /**
     * Konstruktor klasy
     * 
     * @param name Imię gracza
     * @param sign Znak gracza
     */
    public Player(String name, char sign) {
        logger.info("Player: {} - {}", name, sign);
        this.name = name;
        this.sign = sign;
    }

    /**
     * @return Imię gracza
     */
    public String getName() {
        return name;
    }

    /**
     * @return Znak gracza
     */
    public char getSign() {
        return sign;
    }

}
