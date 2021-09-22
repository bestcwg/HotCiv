package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.AlphaCivWinnerStrategy;
import hotciv.variants.AlphaCivAgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBetaCiv {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new BetaCivAgeStrategy(), new AlphaCivWinnerStrategy());
    }

    @Test
    public void shouldIncrementAgeWith100From4000BCTo100BC() {
        // Given a game with BetaCivAgeStrategy
        // When a round ends
        // The age should increment with 100 when the age is between 4000BC and 100BC
        assertThat(game.getAge(), is(-4000));
        doXEndOfTurn(2);
        assertThat(game.getAge(), is(-3900));
        doXEndOfTurn(6);
        assertThat(game.getAge(), is(-3600));
        doXEndOfTurn(70);
        assertThat(game.getAge(),is(-100));
    }

    /**
     * A helper method for passing turns to avoid code duplication,
     * and ease of use in test driven development
     * @param x is the amount of turns that should be ended
     */
    public void doXEndOfTurn(int x) {
        for (int i = 1; i <= x; i++) {
            game.endOfTurn();
        }
    }
}
