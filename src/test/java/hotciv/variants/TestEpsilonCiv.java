package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.WinnerStrategy;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.betaCiv.BetaCivAgeStrategy;
import hotciv.variants.betaCiv.BetaCivWinnerStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import org.junit.jupiter.api.*;

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
        newPos = new Position(3,1);
        game.moveUnit(archerPos, newPos);
        game.endOfTurn();
        game.moveUnit(legionPos, newPos);
        assertThat(game.getUnitAt(newPos).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(newPos).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeUnitWithDefenceHigherThatOtherUnitAttackThatWins() {
        newPos = new Position(3,3);
        game.moveUnit(settlerPos, newPos);
        assertThat(game.getUnitAt(newPos), is(notNullValue()));
        doXEndOfTurn(2);
        game.moveUnit(newPos, legionPos);
        assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(legionPos).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeUnitDefendingStrenghtMultipliedByThreeWhenDefendingFromCity() {
        game.moveUnit(archerPos,redCityPos);
        assertThat(new EpsilonCivAttackingStrategy().getDefendingUnitStrenght(redCityPos, game),is(9));
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
