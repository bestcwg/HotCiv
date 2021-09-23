package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGammaCiv {
    private Game game;
    Position archer;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new AlphaCivWinnerStrategy(), new GammaCivPerformUnitActionStrategy());
        archer = new Position(2,0);
    }

    @Test
    public void shouldBeDoubleDefenceStatsForRedArcherInFortifyAt2_0() {

        assertThat(game.getUnitAt(archer).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(archer);
        assertThat(game.getUnitAt(archer).getDefensiveStrength(),is(6));
    }

    @Test
    public void shouldNotBeAbleToMoveFortifiedUnits() {
        game.performUnitActionAt(archer);
        assertThat(game.moveUnit(archer, new Position(3,0)), is(false));
    }
}
