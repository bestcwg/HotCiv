package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWinnerStrategy;
import hotciv.variants.deltaCiv.DeltaCivWorldLayoutStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestDeltaCiv {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new AlphaCivWinnerStrategy(), new AlphaCivPerformUnitActionStrategy(), new DeltaCivWorldLayoutStrategy());
    }

    @Test
    public void shouldBeOceanTileAt0_0And11_0And15_15() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When world is made
        // Then there should be ocean at (0_0) and (11_0) and (15,15)
        assertThat(game.getTileAt(new Position(11,0)).getTypeString(),is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(15,15)).getTypeString(),is(GameConstants.OCEANS));
    }

    @Test
    public void shouldBeMountainAt3_3And4_3() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When world is made
        // Then there should be mountain at (3_3) and (4_3)
        assertThat(game.getTileAt(new Position(3,3)).getTypeString(),is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(3,4)).getTypeString(),is(GameConstants.MOUNTAINS));
    }

    @Test
    public void shouldBeHillAt1_3And1_4() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When world is made
        // Then there should be hill at (1_3) and (1_4)
        assertThat(game.getTileAt(new Position(1,3)).getTypeString(),is(GameConstants.HILLS));
        assertThat(game.getTileAt(new Position(1,4)).getTypeString(),is(GameConstants.HILLS));
    }

    @Test
    public void shouldBePlainAt0_3And4_6() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When world is made
        // Then there should be plain at (0_3) and (4_6)
        assertThat(game.getTileAt(new Position(0,3)).getTypeString(),is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(4,6)).getTypeString(),is(GameConstants.PLAINS));
    }

    @Test
    public void shouldBeRedCityAt8_12() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When a world is made
        // Then there should be a red city at 8,12
        assertThat(game.getCityAt(new Position(8,12)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(8,12)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldBeRedCityAt4_5() {
        // Given a game with DeltaCivWorldLayoutStrategy
        // When a world is made
        // Then there should be a blue city at 4,5
        assertThat(game.getCityAt(new Position(4,5)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }
}
