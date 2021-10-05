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
    private Position legionPos;
    private Position settlerPos;
    private Position redCityPos;
    private Position blueCityPos;
    private Position p;
    private Position newPos;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new EpsilonCivWinnerStrategy(), new AlphaCivPerformUnitActionStrategy(), new AlphaCivWorldLayoutStrategy(), new String[] {}, new EpsilonCivAttackingStrategy());
        archerPos = new Position(2,0); // The archers' owner is red
        legionPos = new Position(3,2); // The Legions' owner is blue
        settlerPos = new Position(4,3); // The settler' owner is red
        redCityPos = new Position(1,1);
        blueCityPos = new Position(4,1);
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
