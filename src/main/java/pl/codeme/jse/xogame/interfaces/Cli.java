package pl.codeme.jse.xogame.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.codeme.jse.xogame.engine.Action;
import pl.codeme.jse.xogame.engine.GameOX;
import pl.codeme.jse.xogame.engine.GameOXException;
import pl.codeme.jse.xogame.engine.GameStateInterface;

/**
 * Interfejs gracza obsługiwany przez linię komend
 * 
 * @author pawel.apanasewicz@codeme.pl
 * @author szymonskura@gmail.com
 */
public class Cli implements UserInterface {

    /**
     * Statyczny uchwyt do silnika gry
     */
    private static final GameOX GAME = GameOX.getInstance(); // pobranie instancji silnika gry

    /**
     * Konstruktor klasy interfejsu gracza
     */
    public Cli() {
        GAME.setUI(this);
        start(GAME.send(null)); // wywołanie metody start rozpoczynającej rozgrywkę
    }

    @Override
    public String getType() {
        return "CLI";
    }

    /**
     * Metoda wyrysowująca planszę gry
     */
    private void drowGameBoard(GameStateInterface gameState) throws GameOXException {
        for(int x = 0; x < GameOX.number.size() + 1; x++) {
            for(int y = 0; y < GameOX.literal.size() + 1; y++) {
                if(x == 0 && y < 3) {
                    System.out.print("   " + GameOX.literal.get(y));
                }
                if(y == 0 && x > 0) {
                    System.out.print(GameOX.number.get(x - 1) + " ");
                }
                if(y > 0 && x > 0) {
                    String coord = GameOX.literal.get(y - 1) + GameOX.number.get(x - 1); // TODO Sprawdzić czemu treba przestawiać x z y
                    if(y == GameOX.literal.size()) {
                        System.out.print(" " + gameState.get(coord) + " \n");
                        if(x < GameOX.number.size()) {
                            System.out.print("  ---+---+---");
                        }
                    } else {
                        System.out.print(" " + gameState.get(coord) + " |");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Metoda komunikacyjna z użytkownikiem przez linię komend
     * 
     * @param action Akcja wysłana przez silnik gry
     * 
     * @return Akcja do wysłania do silnika gry
     */
    private String question(Action action) throws GameOXException {
    	if(action.getGameBoard() != null) {
    		drowGameBoard(action.getGameBoard());
    	}
        System.out.print(action.getMessage()); // wypisanie komunikatu dla gracza od silnika gry
        String msg = "";
        try {
            // odczytanie odpowiedzi od gracza z konsoli
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            msg = reader.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return msg;
    }

    /**
     * Metoda rozpoczynająca rozgrywkę
     * 
     * @param action Akcja odebrana od silnika gry
     */
    private void start(Action action) {
        while(true) { // pętla gry
        	try {
                String answer = question(action); // wysłanie komunikatu do gracza i odebranie odpowiedzi
                action.setMessage(answer); // wpisanie odpowiedzi gracza wiadomości
                action = Cli.GAME.send(action); // wysłanie wiadomości do silnika gry i odebranie odpowiedzi
        	} catch(GameOXException e) {
        		e.printStackTrace();
        		break;
        	}
        }
    }

}
