package pl.codeme.jse.xogame.engine;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.codeme.jse.xogame.interfaces.UserInterface;

public class GameOXTest {

    public static GameOX game;

    private Action action;

    @Before
    public void init() {
        GameOXTest.game = GameOX.getInstance();
        UserInterface ui = new UserInterface() {

            @Override
            public String getType() {
                return "CLI";
            }
        };
        GameOXTest.game.setUI(ui);
        action = GameOXTest.game.send(null);
        action.setMessage("Ala1");
        action = GameOXTest.game.send(action);
        action.setMessage("Ala2");
        action = GameOXTest.game.send(action);
    }

    @Test
    public void communicationTest() throws GameOXException {
        action.setMessage("B2");
        action = game.send(action);
        Assert.assertEquals('O', action.getGameBoard().get("B2"));
    }

    @After
    public void clear() {
        System.out.println("Koniec testu");
    }
}
