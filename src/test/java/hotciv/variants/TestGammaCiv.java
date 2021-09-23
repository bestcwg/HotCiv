package hotciv.variants;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivWinnerStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGammaCiv {
    private Game game;
    private Position archer;
    private Position settlerPos;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new AlphaCivAgeStrategy(), new AlphaCivWinnerStrategy(), new GammaCivPerformUnitActionStrategy(), new AlphaCivWorldLayoutStrategy());
        archer = new Position(2,0);
        settlerPos = new Position(4,3);
    }

    @Test
    public void shouldBeDoubleDefenceStatsForRedArcherInFortifyAt2_0() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When perform unit action on archer
        assertThat(game.getUnitAt(archer).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(archer);
        // Then archer should double defence stats
        assertThat(game.getUnitAt(archer).getDefensiveStrength(),is(6));
    }

    @Test
    public void shouldNotBeAbleToMoveFortifiedUnits() {
        //Given a game with the GammaCivPerformUnitActionStrategy
        // When a unit is fortified
        // Then it should not be able to move
        game.performUnitActionAt(archer);
        assertThat(game.moveUnit(archer, new Position(3,0)), is(false));
    }

    @Test
    public void shouldBe3DefenceWhenFortifyIsRevoked() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When perform unit action on archer who is already fortified
        game.performUnitActionAt(archer);
        assertThat(game.getUnitAt(archer).getDefensiveStrength(),is(6));
        game.performUnitActionAt(archer);
        // Then archer should have 3 defence
        assertThat(game.getUnitAt(archer).getDefensiveStrength(),is(3));
    }

    @Test
    public void shouldBeAbleToMoveArcherAfterFortifiedIsRevoked() {
        // Given a game with the GammaCivPerformUnitActionStrategy
        // When fortify is revoked
        // Then the unit should be able to move
        Position newPos = new Position(3,0);
        game.performUnitActionAt(archer);
        assertThat(game.moveUnit(archer, newPos), is(false));
        game.performUnitActionAt(archer);
        assertThat(game.moveUnit(archer, newPos), is(true));
        assertThat(game.getUnitAt(newPos).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldBePlacedACityIfSettlerPerformUnitAction() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When settler perform unit action
        // Then city is placed at the same position
        game.performUnitActionAt(settlerPos);
        assertThat(game.getCityAt(settlerPos),is(notNullValue()));
    }

    @Test
    public void shouldRemoveSettlerAfterPerformUnitAction() {
        // Given a game with the GammaCivPerformUnitActionStrategy
        // When a settler performs its unit action
        // Then it should be removed
        game.performUnitActionAt(settlerPos);
        assertThat(game.getUnitAt(settlerPos), is(nullValue()));
    }

    @Test
    public void shouldBePopulationSizeOf1WhenCreatingCityWithSettler() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When settler perform unit action
        // Then size of city should be 1
        game.performUnitActionAt(settlerPos);
        assertThat(game.getCityAt(settlerPos).getSize(),is(1));
    }

    @Test
    public void shouldBeRedPlayerWhoOwnTheCityWhenCreatedWithSettler() {
        // Given a game with GammaCivPerformUnitActionStrategy
        // When red settler perform unit action
        // Then the city owner should be red
        game.performUnitActionAt(settlerPos);
        assertThat(game.getCityAt(settlerPos).getOwner(),is(Player.RED));
    }
}
