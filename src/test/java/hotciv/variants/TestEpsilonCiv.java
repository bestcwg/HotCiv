package hotciv.variants;

import hotciv.framework.*;
import hotciv.standard.*;
import hotciv.standard.factories.EpsilonCivFactory;
import hotciv.standard.strategies.RollStrategy;
import hotciv.utility.FixedRollStrategy;
import hotciv.utility.RandomRollStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import org.junit.jupiter.api.*;
import java.time.temporal.ValueRange;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private Game game;
    private Position archerPos;
    private Position redCityPos;
    private RollStrategy fixedRoll;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new EpsilonCivFactory(new FixedRollStrategy()));
        fixedRoll = new FixedRollStrategy();
        archerPos = new Position(2,0); // The archers' owner is red
        redCityPos = new Position(1,1);

    }

    @Test
    public void shouldBeUnitWithAttackHigherThatOtherUnitDefenceThatWins() {
        // Given a game
        // When a unit with higher attacking strength attacks a unit with lower defence
        GameImpl gameImpl = (GameImpl) game;
        Position newPlains = new Position(8,8);
        Position newPlains2 = new Position(8,9);
        gameImpl.getUnits().put(newPlains, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(newPlains2, new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        game.moveUnit(newPlains,newPlains2);

        // Then the unit attacking should win
        assertThat(game.getUnitAt(newPlains2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPlains2).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(newPlains), is(nullValue()));
    }

    @Test
    public void shouldBeUnitWithDefenceHigherThatOtherUnitAttackThatWins() {
        // Given a game
        // When a unit with higher defence is attacked by a unit with lower attack
        GameImpl gameImpl = (GameImpl) game;
        Position newPlains = new Position(8,8);
        Position newPlains2 = new Position(8,9);
        gameImpl.getUnits().put(newPlains, new UnitImpl(Player.RED, GameConstants.SETTLER));
        gameImpl.getUnits().put(newPlains2, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        game.moveUnit(newPlains,newPlains2);

        // Then the defending unit should win
        assertThat(game.getUnitAt(newPlains2).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPlains2).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(newPlains), is(nullValue()));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy3WhenInCity() {
        // Given a game
        // When a unit is stationed in a city
        // Then its attack and defence should be multiplied by 3
        game.moveUnit(archerPos,redCityPos);
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(redCityPos, game),is(3 * 3));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(redCityPos, game),is(2 * 3));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy2WhenInForest() {
        // Given a game
        // When a unit is stationed in a forest
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position forest = new Position(8,8);
        tiles.put(forest, new TileImpl(GameConstants.FOREST));
        gameImpl.getUnits().put(forest, new UnitImpl(Player.RED, GameConstants.ARCHER));
        // Then its attack and defence should be multiplied by 2
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(forest, game), is(3 * 2));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(forest, game), is(2 * 2));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy2WhenOnHills() {
        // Given a game
        // When a unit is stationed on a hill
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position hill = new Position(8,8);
        tiles.put(hill, new TileImpl(GameConstants.HILLS));
        gameImpl.getUnits().put(hill, new UnitImpl(Player.RED, GameConstants.ARCHER));
        // Then its attack and defence should be multiplied by 2
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(hill, game), is(3 * 2));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(hill, game), is(2 * 2));
    }

    @Test
    public void shouldMultiplyUnitStrengthBy1WhenOnPlains() {
        // Given a game
        // When a unit is stationed on a plain
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, Tile> tiles = ((GameImpl) game).getWorldMap();
        Position plains = new Position(8,8);
        tiles.put(plains, new TileImpl(GameConstants.PLAINS));
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));
        // Then its attack and defence should be multiplied by 1
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is(2 * 1));
    }

    @Test
    public void shouldIncrementUnitStrengthBy1WhenAFriendlyUnitIsAdjacent() {
        // Given a game
        // When a unit is supported by adjacent friendly units
        GameImpl gameImpl = (GameImpl) game;
        Position plains = new Position(8,8);
        // Then its strengths should be increased by 1
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly1 = new Position(8,9);
        gameImpl.getUnits().put(friendly1, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is((3+1) * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is((2+1) * 1));

        Position friendly2 = new Position(9,9);
        gameImpl.getUnits().put(friendly2, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is((3+2) * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is((2+2) * 1));
    }

    @Test
    public void shouldNotIncrementUnitStrengthBy1WhenAEnemyUnitIsAdjacent() {
        // Given a game
        // When a unit is adjacent to an enemy unit
        GameImpl gameImpl = (GameImpl) game;
        Position plains = new Position(8,8);
        gameImpl.getUnits().put(plains, new UnitImpl(Player.RED, GameConstants.ARCHER));
        // Then it should not have increased strengths
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly1 = new Position(8,9);
        gameImpl.getUnits().put(friendly1, new UnitImpl(Player.BLUE, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is(3 * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is(2 * 1));

        Position friendly2 = new Position(9,9);
        gameImpl.getUnits().put(friendly2, new UnitImpl(Player.RED, GameConstants.ARCHER));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalDefensiveStrength(plains, game), is((3+1) * 1));
        assertThat(new EpsilonCivAttackingStrategy(fixedRoll).getTotalAttackingStrength(plains, game), is((2+1) * 1));
    }

    @Test
    public void shouldBeRedPlayerWhoWinsAfter3WonBattles() {
        // Given a game
        // When a player wins 3 battles
        GameImpl gameImpl = (GameImpl) game;
        Position redUnit = new Position(8,8);
        Position blueUnit1 = new Position(8,9);
        Position blueUnit2 = new Position(8,10);
        Position blueUnit3 = new Position(8,11);

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
    public void shouldBeBluePlayerWhoWinsAfter3WonBattles() {
        // Given a game
        // When a player wins 3 battles
        GameImpl gameImpl = (GameImpl) game;
        Position blueUnit = new Position(8,8);
        Position redUnit1 = new Position(8,9);
        Position redUnit2 = new Position(8,10);
        Position redUnit3 = new Position(8,11);

        gameImpl.getUnits().put(blueUnit, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit1, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit2, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit3, new UnitImpl(Player.RED, GameConstants.LEGION));

        // Then that player should win
        game.endOfTurn();
        game.moveUnit(blueUnit,redUnit1);
        doXEndOfTurn(2);
        game.moveUnit(redUnit1,redUnit2);
        doXEndOfTurn(2);
        game.moveUnit(redUnit2,redUnit3);
        assertThat(game.getWinner(),is(Player.BLUE));
    }

    @Test
    public void shouldNotWinAfterAttackingAndLoosing3rdBattle() {
        // Given a game
        // When attacking and losing the 3rd battle
        GameImpl gameImpl = (GameImpl) game;
        HashMap<Position, City> cities = ((GameImpl) game).getCities();
        cities.put(new Position(8,11), new CityImpl(Player.RED));


        Position blueUnit = new Position(8,8);
        Position redUnit1 = new Position(8,9);
        Position redUnit2 = new Position(8,10);
        Position redUnit3 = new Position(8,11);

        gameImpl.getUnits().put(blueUnit, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit1, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit2, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit3, new UnitImpl(Player.RED, GameConstants.LEGION));

        // Then that player should not win
        game.endOfTurn();
        game.moveUnit(blueUnit,redUnit1);
        doXEndOfTurn(2);
        game.moveUnit(redUnit1,redUnit2);
        doXEndOfTurn(2);
        game.moveUnit(redUnit2,redUnit3);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldNotWinAfterDefendingAndWinning3rdBattle() {
        // Given a game
        // When a player has won two battles, and are attacked a wins
        GameImpl gameImpl = (GameImpl) game;
        Position blueUnit = new Position(8,8);
        Position redUnit1 = new Position(8,9);
        Position redUnit2 = new Position(8,10);
        Position redUnit3 = new Position(8,11);

        gameImpl.getUnits().put(blueUnit, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit1, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit2, new UnitImpl(Player.RED, GameConstants.LEGION));
        gameImpl.getUnits().put(redUnit3, new UnitImpl(Player.RED, GameConstants.SETTLER));

        // Then that player should not win
        game.endOfTurn();
        game.moveUnit(blueUnit,redUnit1);
        doXEndOfTurn(2);
        game.moveUnit(redUnit1,redUnit2);
        game.endOfTurn();
        game.moveUnit(redUnit3,redUnit2);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeRandomInRealGame() {
        // Given a game
        // When random numbers are added to strengths
        // Then the numbers should be between 1-6
        RollStrategy random = new RandomRollStrategy();
        int die = random.roll();
        assertThat(ValueRange.of(1, 6).isValidIntValue(die), is(true));
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
