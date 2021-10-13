package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.factories.GammaCivFactory;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.standard.strategies.RollStrategy;
import hotciv.utility.FixedRollStrategy;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivWinnerStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSemiCiv {
    private Game game;
    private RollStrategy fixedRoll;
    private Position settlerPos;

    @BeforeEach
    void setUp() {
        fixedRoll = new FixedRollStrategy();
        fixedRoll.setRoll(2);
        game = new GameImpl(new SemiCivFactory(fixedRoll));
        settlerPos = new Position(5, 5);
    }

    @Test
    public void shouldUseBetaCivAgeStrategy() {
        assertThat(game.getAge(), is(-4000));
        doXEndOfTurn(2);
        assertThat(game.getAge(), is(-3900));
        doXEndOfTurn(6);
        assertThat(game.getAge(), is(-3600));
        doXEndOfTurn(70);
        assertThat(game.getAge(),is(-100));
        doXEndOfTurn(2);
        assertThat(game.getAge(),is(-1));
        doXEndOfTurn(2);
        assertThat(game.getAge(),is(1));
        doXEndOfTurn(152-82);
        assertThat(game.getAge(), is(1750));
        doXEndOfTurn(12);
        assertThat(game.getAge(),is(1900));
    }

    @Test
    public void shouldUseGammaCivUnitStrategy() {
        game.performUnitActionAt(settlerPos);
        assertThat(game.getCityAt(settlerPos),is(notNullValue()));
        assertThat(game.getUnitAt(settlerPos), is(nullValue()));
    }

    @Test
    public void shouldUseDeltaWorldLayoutStrategy() {
        assertThat(game.getTileAt(new Position(3,3)).getTypeString(),is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(3,4)).getTypeString(),is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(0,3)).getTypeString(),is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(4,6)).getTypeString(),is(GameConstants.PLAINS));
        Position unitPos = new Position(3,8);
        assertThat(game.getUnitAt(unitPos).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(unitPos).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldUseEpsilonWinnerStrategy() {
        // Given a game
        // When a player wins 3 battles
        GameImpl gameImpl = (GameImpl) game;
        Position redUnit = new Position(15,8);
        Position blueUnit1 = new Position(15,9);
        Position blueUnit2 = new Position(15,10);
        Position blueUnit3 = new Position(15,11);

        gameImpl.getUnits().put(redUnit, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit1, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(blueUnit3, new UnitImpl(Player.BLUE, GameConstants.LEGION));

        // Then that player should win
        game.moveUnit(redUnit,blueUnit1);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit1,blueUnit2);
        doXEndOfTurn(2);
        game.moveUnit(blueUnit2,blueUnit3);
        assertThat(game.getWinner(),is(Player.RED));
    }

    @Test
    public void shouldUseEpsilonAttackingStrategy() {
        // Given a game
        // When a unit with higher attacking strength attacks a unit with lower defence
        GameImpl gameImpl = (GameImpl) game;
        Position newPlains = new Position(15,8);
        Position newPlains2 = new Position(15,9);
        gameImpl.getUnits().put(newPlains, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(newPlains2, new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(newPlains,newPlains2);

        // Then the unit attacking should win
        assertThat(game.getUnitAt(newPlains2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPlains2).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(newPlains), is(nullValue()));
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