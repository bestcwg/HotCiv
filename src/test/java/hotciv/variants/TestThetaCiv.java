package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.factories.ThetaCivFactory;
import hotciv.standard.factories.ZetaCivFactory;
import hotciv.standard.strategies.WinnerStrategy;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.betaCiv.BetaCivWinnerStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import hotciv.variants.zetaCiv.ZetaCivWinnerStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestThetaCiv {
    private Game game;

    /**
     * Fixture for zetaCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
    }


    @Test
    public void shouldDevourAllEnemiesInNeighborhoodTiles() {

        GameImpl gameImpl = (GameImpl) game;
        Position redUnit = new Position(8, 8);
        Position blueUnit1 = new Position(8, 9);
        Position blueUnit2 = new Position(8, 7);
        Position blueUnit3 = new Position(7, 8);
        Position blueUnit4 = new Position(9, 8);
        Position blueUnit5 = new Position(9, 9);

        gameImpl.getUnits().put(redUnit, new UnitImpl(Player.RED, GameConstants.SANDWORM));
        gameImpl.getUnits().put(blueUnit1, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit3, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit4, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit5, new UnitImpl(Player.BLUE, GameConstants.LEGION));


        game.performUnitActionAt(redUnit);
        assertThat(game.getUnitAt(redUnit).getTypeString(), is(GameConstants.SANDWORM));
        assertThat(game.getUnitAt(blueUnit1), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit2), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit3), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit4), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit5), is(nullValue()));
    }

    @Test
    public void shouldNotDevourFriendliesInNeighborhoodTiles() {

        GameImpl gameImpl = (GameImpl) game;
        Position redUnit = new Position(8, 8);
        Position blueUnit1 = new Position(8, 9);
        Position blueUnit2 = new Position(8, 7);
        Position redUnit2 = new Position(7, 8);
        Position redUnit3 = new Position(9, 8);
        Position blueUnit3 = new Position(9, 9);

        gameImpl.getUnits().put(redUnit, new UnitImpl(Player.RED, GameConstants.SANDWORM));
        gameImpl.getUnits().put(blueUnit1, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit3, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit2, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit3, new UnitImpl(Player.RED, GameConstants.LEGION));


        game.performUnitActionAt(redUnit);
        assertThat(game.getUnitAt(redUnit).getTypeString(), is(GameConstants.SANDWORM));
        assertThat(game.getUnitAt(blueUnit1), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit2), is(nullValue()));
        assertThat(game.getUnitAt(blueUnit3), is(nullValue()));
        assertThat(game.getUnitAt(redUnit2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(redUnit3).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldBeAbleToFortifyAsArcher() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When perform unit action on archer
        Position archer = new Position(3,8);
        assertThat(game.getUnitAt(archer).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(archer);
        // Then archer should double defence stats
        assertThat(game.getUnitAt(archer).getDefensiveStrength(),is(6));
        assertThat(game.moveUnit(archer, new Position(3,9)), is(false));
    }

    @Test
    public void shouldBePlacedACityIfSettlerPerformUnitAction() {
        Position settlerPos = new Position(5,5);
        game.performUnitActionAt(settlerPos);
        assertThat(game.getCityAt(settlerPos),is(notNullValue()));
        assertThat(game.getUnitAt(settlerPos), is(nullValue()));
    }

    @Test
    public void shouldBeDesertsAroundTheWorld() {
        Position desert1 = new Position(8,12);
        Position desert2 = new Position(15,8);
        Position desert3 = new Position(9,9);
        Position desert4 = new Position(9,12);
        assertThat(game.getTileAt(desert1).getTypeString(), is(GameConstants.DESERT));
        assertThat(game.getTileAt(desert2).getTypeString(), is(GameConstants.DESERT));
        assertThat(game.getTileAt(desert3).getTypeString(), is(GameConstants.DESERT));
        assertThat(game.getTileAt(desert4).getTypeString(), is(GameConstants.DESERT));
    }

    @Test
    public void shouldBeSandwormAt9_6() {
        // Given a game
        // When having a world
        // Then should be a sandworm at (9,6)
        assertThat(game.getUnitAt(new Position(9,6)).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void shouldBeBluePlayersSandwormAt9_6() {
        // Given a game
        // When having a sandworm at (9,6)
        // Then should be blue players sandworm
        assertThat(game.getUnitAt(new Position(9,6)).getOwner(),is(Player.BLUE));
    }

    /**
     * A helper method for passing turns to avoid code duplication,
     * and ease of use in test driven development
     *
     * @param x is the amount of turns that should be ended
     */
    public void doXEndOfTurn(int x) {
        for (int i = 1; i <= x; i++) {
            game.endOfTurn();
        }
    }
}