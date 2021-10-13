package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
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
    private Position blueSandwormPos;
    private Position redCityPos;
    private Position blueCityPos;

    /**
     * Fixture for zetaCiv testing.
     */
    @BeforeEach
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
        blueSandwormPos = new Position(9,6);
        redCityPos = new Position(8,12);
        blueCityPos = new Position(4,5);
    }


    @Test
    public void shouldDevourAllEnemiesInNeighborhoodTiles() {
        // Given a game of Theta Civ
        // When a sandworm uses its ability
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

        // Then it should kill all surrounding enemies
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
        // Given a game of ThetaCiv
        // When a sandworm uses it action
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

        // Then it should not kill surrounding friendly units
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
        // Given a game with ThetaCiv
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
        // Given a game of ThetaCiv
        // When a settler uses its action
        Position settlerPos = new Position(5,5);
        game.performUnitActionAt(settlerPos);
        // Then a new city should be created
        assertThat(game.getCityAt(settlerPos),is(notNullValue()));
        assertThat(game.getUnitAt(settlerPos), is(nullValue()));
    }

    @Test
    public void shouldBeDesertsAroundTheWorld() {
        // Given a game of ThetaCiv
        // When the world is created
        Position desert1 = new Position(8,12);
        Position desert2 = new Position(15,8);
        Position desert3 = new Position(9,9);
        Position desert4 = new Position(9,12);
        // Then there should be desert tiles
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
        assertThat(game.getUnitAt(blueSandwormPos).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void shouldBeBluePlayersSandwormAt9_6() {
        // Given a game
        // When having a sandworm at (9,6)
        // Then should be blue players sandworm
        assertThat(game.getUnitAt(blueSandwormPos).getOwner(),is(Player.BLUE));
    }


    @Test
    public void shouldBeInvalidMovementOnPlainTilesForSandworm() {
        // Given a game
        // When moving blue players sandworm on plain tile
        // Then the sandworm should not move
        game.endOfTurn();
        assertThat(game.moveUnit(blueSandwormPos, new Position(9,7)),is(false));
    }

    @Test
    public void shouldBeInvalidMovementOnHillTilesForSandworm() {
        // Given a game
        // When moving blue players sandworm on hill tile
        // Then the sandworm should not move
        GameImpl gameImpl = (GameImpl) game;
        gameImpl.getWorldMap().put(new Position(9,7),new TileImpl( GameConstants.HILLS));
        game.endOfTurn();
        assertThat(game.moveUnit(blueSandwormPos, new Position(9,7)),is(false));
    }

    @Test
    public void shouldBeInvalidMovementOnForestTilesForSandworm() {
        // Given a game
        // When moving blue players sandworm on forest tile
        // Then the sandworm should not move
        GameImpl gameImpl = (GameImpl) game;
        gameImpl.getWorldMap().put(new Position(9,7),new TileImpl( GameConstants.FOREST));
        game.endOfTurn();
        assertThat(game.moveUnit(blueSandwormPos, new Position(9,7)),is(false));
    }


    @Test
    public void shouldBeValidMovementOnDesertTilesForSandworm() {
        // Given a game
        // When moving blue players sandworm on desert tile
        // Then the sandworm should move
        game.endOfTurn();
        assertThat(game.moveUnit(blueSandwormPos, new Position(9,5)),is(true));
    }


    @Test
    public void shouldBeAbleToMoveOnlyTwoTimesEachTurnForSandworm() {
        // Given a game
        // When moving a sandworm two times in the same turn
        game.endOfTurn();
        game.moveUnit(blueSandwormPos, new Position(9,5));
        game.moveUnit(new Position(9,5), new Position(9,4));
        // Then the sandworm should not be able to move the third time
        assertThat(game.getUnitAt(new Position(9,4)).getTypeString(),is(GameConstants.SANDWORM));
        assertThat(game.moveUnit(new Position(9,4), new Position(9,3)), is(false));
    }


    @Test
    public void shouldBeAbleToOnlyProduceSandwormOnDesertTile() {
        // Given a game
        // When producing a sandworm in a city
        game.changeProductionInCityAt(redCityPos, GameConstants.SANDWORM);
        // Then it should spawn in a desert
        assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.SANDWORM));
        doXEndOfTurn(12);
        assertThat(game.getUnitAt(redCityPos).getTypeString(),is(GameConstants.SANDWORM));
        doXEndOfTurn(10);
        assertThat(game.getUnitAt(new Position(7,12)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(8,13)).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void shouldNotBeAbleToSpawnASandwormOnANonDesertCity() {
        // Given a game
        // When producing a sandworm in a city
        doXEndOfTurn(1);
        // Then it should not spawn on a non desert city
        game.changeProductionInCityAt(blueCityPos, GameConstants.SANDWORM);
        assertThat(game.getCityAt(blueCityPos).getProduction(), is(GameConstants.SANDWORM));
        doXEndOfTurn(12);
        assertThat(game.getUnitAt(blueCityPos),is(nullValue()));
    }

    @Test
    public void shouldBe30TreasuryToProduceSandworm() {
        // Given a game
        // When producing a sandworm in a city
        game.changeProductionInCityAt(redCityPos, GameConstants.SANDWORM);
        // Then it should cost 30 production
        assertThat(game.getCityAt(redCityPos).getTreasury(), is(0));
        assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.SANDWORM));
        doXEndOfTurn(8);
        assertThat(game.getCityAt(redCityPos).getTreasury(), is(24));
        doXEndOfTurn(4);
        assertThat(game.getUnitAt(redCityPos).getTypeString(),is(GameConstants.SANDWORM));
        assertThat(game.getCityAt(redCityPos).getTreasury(), is(6));
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