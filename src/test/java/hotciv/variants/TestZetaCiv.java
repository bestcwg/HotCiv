package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.betaCiv.BetaCivWinnerStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import hotciv.variants.zetaCiv.ZetaCivWinnerStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestZetaCiv {
    private Game game;


    /**
     * Fixture for zetaCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new ZetaCivWinnerStrategy(new BetaCivWinnerStrategy(), new EpsilonCivWinnerStrategy()), new AlphaCivPerformUnitActionStrategy(), new AlphaCivWorldLayoutStrategy(), new String[]{}, new AlphaCivAttackingStrategy());
    }

    @Test
    public void shouldBeRedWhoWinsIfControllingAllCitiesBeforeRound20() {
        // Given a game with BetaCivAgeStrategy
        // When player red owns all cities
        Position blueCityPos = new Position(4,1);
        Position archerPos = new Position(2,0);
        // Then player red should win game
        assertThat(game.getCityAt(blueCityPos).getOwner(),is(Player.BLUE));
        game.moveUnit(archerPos, new Position(3,1));
        doXEndOfTurn(2);
        game.moveUnit(new Position(3,1),blueCityPos);
        assertThat(game.getCityAt(blueCityPos).getOwner(),is(Player.RED));
        assertThat(game.getWinner(),is(Player.RED));
    }

    @Test
    public void shouldNotWinAfterControllingAllCitiesAfterRound20() {
        // Given a game with BetaCivAgeStrategy
        // When player red owns all cities
        Position blueCityPos = new Position(4,1);
        Position archerPos = new Position(2,0);
        // Then player red should win game
        assertThat(game.getCityAt(blueCityPos).getOwner(),is(Player.BLUE));
        game.moveUnit(archerPos, new Position(3,1));
        doXEndOfTurn(40);
        game.moveUnit(new Position(3,1),blueCityPos);
        assertThat(game.getCityAt(blueCityPos).getOwner(),is(Player.RED));
        assertThat(game.getWinner(),is(nullValue()));
    }

    @Test
    public void shouldNotWinByWinning3BattlesBeforeRound20() {
        GameImpl gameImpl = (GameImpl) game;
        Position redUnit = new Position(8,8);
        Position blueUnit1 = new Position(8,9);
        Position blueUnit2 = new Position(8,10);
        Position blueUnit3 = new Position(8,11);

        gameImpl.getUnits().put(redUnit, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit1, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit3, new UnitImpl(Player.BLUE, GameConstants.LEGION));

        game.moveUnit(redUnit,blueUnit1);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit1,blueUnit2);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit2,blueUnit3);
        assertThat(game.getWinner(),is(nullValue()));
    }

    @Test
    public void shouldBeWinningAfter3BattleWonAfterRound20() {
        GameImpl gameImpl = (GameImpl) game;
        doXEndOfTurn(40);
        Position redUnit = new Position(8,8);
        Position blueUnit1 = new Position(8,9);
        Position blueUnit2 = new Position(8,10);
        Position blueUnit3 = new Position(8,11);

        gameImpl.getUnits().put(redUnit, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit1, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit3, new UnitImpl(Player.BLUE, GameConstants.LEGION));

        game.moveUnit(redUnit,blueUnit1);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit1,blueUnit2);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit2,blueUnit3);
        assertThat(game.getWinner(), is(Player.RED));
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

