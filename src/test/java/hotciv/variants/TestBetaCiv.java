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
        game = new GameImpl(new BetaCivAgeStrategy(), new BetaCivWinnerStrategy());
    }

    @Test
    public void shouldIncrementAgeWith100From4000BCTo100BC() {
        // Given a game with BetaCivAgeStrategy
        // When a round ends
        // Then age should increment with 100 when the age is between 4000BC and 100BC
        assertThat(game.getAge(), is(-4000));
        doXEndOfTurn(2);
        assertThat(game.getAge(), is(-3900));
        doXEndOfTurn(6);
        assertThat(game.getAge(), is(-3600));
        doXEndOfTurn(70);
        assertThat(game.getAge(),is(-100));
    }



    @Test
    public void shouldBeAge1BCAfter100BC() {
        // Given a game with BetaCivAgeStrategy
        // When round ends at age 100BC
        // Then age should increment with 99
        doXEndOfTurn(78);
        assertThat(game.getAge(), is(-100));
        doXEndOfTurn(2);
        assertThat(game.getAge(),is(-1));
    }

    @Test
    public void shouldBeAge1ACAfter1BC() {
        // Given a game with BetaCivAgeStrategy
        // When round ends at age 1BC
        // Then age should increment with 2
        doXEndOfTurn(80);
        assertThat(game.getAge(),is(-1));
        doXEndOfTurn(2);
        assertThat(game.getAge(),is(1));
    }

    @Test
    public void shouldBeAge50ACAfter1AC() {
        // Given a game with BetaCivAgeStrategy
        // When round ends at age 1AC
        // Then age should increment with 49
        doXEndOfTurn(82);
        assertThat(game.getAge(),is(1));
        doXEndOfTurn(2);
        assertThat(game.getAge(),is(50));
    }

    @Test
    public void shouldIncrementAgeWith50YearsBetween50ADAnd1750AD() {
        // Given a game with BetaCivAgeStrategy
        // When the age is between 50 AD and 1750
        // Then age should increment with 50 years
        doXEndOfTurn(84);
        assertThat(game.getAge(),is(50));
        doXEndOfTurn(2);
        assertThat(game.getAge(), is(100));
        doXEndOfTurn(66);
        assertThat(game.getAge(), is(1750));
    }

    @Test
    public void shouldIncrementAgeWith25YearsBetween1750And1900() {
        // Given a game with BetaCivAgeStrategy
        // When the age is between 1750 AD and 1900 AD
        // Then age should increment with 25 years
        doXEndOfTurn(152);
        assertThat(game.getAge(), is(1750));
        doXEndOfTurn(12);
        assertThat(game.getAge(),is(1900));
    }

    @Test
    public void shouldIncrementAgeWith5YearsBetween1900And1970() {
        // Given a game with BetaCivAgeStrategy
        // When the age is between 1900 and 1970
        // Then age should increment with 5 years
        doXEndOfTurn(164);
        assertThat(game.getAge(), is(1900));
        doXEndOfTurn(28);
        assertThat(game.getAge(),is(1970));
    }

    @Test
    public void shouldIncrementAgeWith1YearFrom1970AndOnwards() {
        // Given a game with BetaCivAgeStrategy
        // When the age is between 1970 and onwards
        // Then age should increment with 1 years
        doXEndOfTurn(192);
        assertThat(game.getAge(), is(1970));
        doXEndOfTurn(28);
        assertThat(game.getAge(),is(1984));
        doXEndOfTurn(16);
        assertThat(game.getAge(), is(1992));
    }

    @Test
    public void shouldBeRedPlayerWhoWinsWhenOwnAllCities() {
        // Given a game with BetaCivAgeStrategy
        // When player red owns all cities
        // Then player red should win game
        Position redCityPos = new Position(1,1);
        Position blueCityPos = new Position(4,1);
        Position archerPos = new Position(2,0);

        assertThat(game.getCityAt(redCityPos).getOwner(),is(Player.RED));
        game.moveUnit(archerPos, new Position(3,1));
        doXEndOfTurn(2);
        game.moveUnit(new Position(3,1),blueCityPos);
        assertThat(game.getCityAt(blueCityPos).getOwner(),is(Player.RED));
        assertThat(game.getWinner(),is(Player.RED));
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
