package hotciv.variants;

import hotciv.framework.*;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private Game game;
    private Position archerPos;
    private Position redCityPos;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new EpsilonCivWinnerStrategy(), new AlphaCivPerformUnitActionStrategy(), new AlphaCivWorldLayoutStrategy(), new String[] {}, new EpsilonCivAttackingStrategy());
        archerPos = new Position(2,0); // The archers' owner is red
        redCityPos = new Position(1,1);

    }

    @Test
    public void shouldBeUnitWithAttackHigherThatOtherUnitDefenceThatWins() {
        GameImpl gameImpl = (GameImpl) game;
        Position newPlains = new Position(8,8);
        Position newPlains2 = new Position(8,9);
        gameImpl.getUnits().put(newPlains, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(newPlains2, new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(newPlains,newPlains2);


        assertThat(game.getUnitAt(newPlains2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPlains2).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(newPlains), is(nullValue()));
    }

    @Test
    public void shouldBeUnitWithDefenceHigherThatOtherUnitAttackThatWins() {
        GameImpl gameImpl = (GameImpl) game;
        Position newPlains = new Position(8,8);
        Position newPlains2 = new Position(8,9);
        gameImpl.getUnits().put(newPlains, new UnitImpl(Player.RED, GameConstants.SETTLER));
        gameImpl.getUnits().put(newPlains2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(newPlains,newPlains2);

        assertThat(game.getUnitAt(newPlains2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPlains2).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(newPlains), is(nullValue()));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy3WhenInCity() {
        game.moveUnit(archerPos,redCityPos);
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(redCityPos, game),is(3 * 3));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(redCityPos, game),is(2 * 3));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy2WhenInForest() {
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position forest = new Position(8,8);
        tiles.put(forest, new TileImpl(GameConstants.FOREST));
        gameImpl.getUnits().put(forest, new UnitImpl(Player.RED, GameConstants.ARCHER));
        
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(forest, game), is(3 * 2));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(forest, game), is(2 * 2));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy2WhenOnHills() {
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position hill = new Position(8,8);
        tiles.put(hill, new TileImpl(GameConstants.HILLS));
        gameImpl.getUnits().put(hill, new UnitImpl(Player.RED, GameConstants.ARCHER));
        
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(hill, game), is(3 * 2));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(hill, game), is(2 * 2));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy1WhenOnPlains() {
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position plains = new Position(8,8);
        tiles.put(plains, new TileImpl(GameConstants.PLAINS));
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));

        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is(2 * 1));
    }

    @Test
    public void shouldIncrementUnitStrengthBy1WhenAFriendlyUnitIsAdjacent() {
        GameImpl gameImpl = (GameImpl) game;
        Position plains = new Position(8,8);
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly1 = new Position(8,9);
        gameImpl.getUnits().put(friendly1, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is((3+1) * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is((2+1) * 1));

        Position friendly2 = new Position(9,9);
        gameImpl.getUnits().put(friendly2, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is((3+2) * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is((2+2) * 1));
    }

    @Test
    public void shouldNotIncrementUnitStrengthBy1WhenAEnemyUnitIsAdjacent() {
        GameImpl gameImpl = (GameImpl) game;
        Position plains = new Position(8,8);
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly1 = new Position(8,9);
        gameImpl.getUnits().put(friendly1, new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly2 = new Position(9,9);
        gameImpl.getUnits().put(friendly2, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy().getTotalDefensiveStrength(plains, game), is((3+1) * 1));
        assertThat(new EpsilonCivAttackingStrategy().getTotalAttackingStrength(plains, game), is((2+1) * 1));
    }

    @Test
    public void shouldBeRedPlayerWhoWinsAfter3WonBattles() {
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
        assertThat(game.getWinner(),is(Player.RED));
    }

    @Test
    public void shouldBeBluePlayerWhoWinsAfter3WonBattles() {
        GameImpl gameImpl = (GameImpl) game;
        Position blueUnit = new Position(8,8);
        Position redUnit1 = new Position(8,9);
        Position redUnit2 = new Position(8,10);
        Position redUnit3 = new Position(8,11);

        gameImpl.getUnits().put(blueUnit, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit1, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit2, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit3, new UnitImpl(Player.RED, GameConstants.LEGION));

        game.endOfTurn();
        game.moveUnit(blueUnit,redUnit1);
        doXEndOfTurn(2);
        game.moveUnit(redUnit1,redUnit2);
        doXEndOfTurn(2);
        game.moveUnit(redUnit2,redUnit3);
        assertThat(game.getWinner(),is(Player.BLUE));
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
