package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.stub.StubGameObserver;
import hotciv.utility.FixedRollStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestObserver {
    private Game game;
    private GameObserver gameObserver;
    private StubGameObserver stubGameObserver;

    @BeforeEach
    public void setUp() {
        game = new GameImpl(new SemiCivFactory(new FixedRollStrategy()));
        gameObserver = new StubGameObserver();
        game.addObserver(gameObserver);
        stubGameObserver = (StubGameObserver) gameObserver;
    }

    @Test
    public void shouldBeTurnEndForObserverAfterEndOfTurn() {
        game.endOfTurn();
        assertThat(stubGameObserver.getLastMethodCalled(), is("turn end"));
    }

    @Test
    public void shouldBeWorldChanged() {
        Position unitPos = new Position(5,5);
        game.moveUnit(unitPos, new Position(5,6));
        assertThat(stubGameObserver.getLastMethodCalled(), is("world changed"));
    }

    @Test
    public void shouldBeTileFocusChanged() {
        game.setTileFocus(new Position(5,5));
        assertThat(stubGameObserver.getLastMethodCalled(), is("tile focus changed"));
    }
}
