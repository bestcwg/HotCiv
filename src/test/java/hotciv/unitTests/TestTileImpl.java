package hotciv.unitTests;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;
import hotciv.standard.TileImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTileImpl {
    TileImpl plains;
    TileImpl mountain;
    TileImpl hill;
    TileImpl forrest;
    TileImpl ocean;

    @BeforeEach
    public void setUp() {
        plains = new TileImpl(GameConstants.PLAINS);
        mountain = new TileImpl(GameConstants.MOUNTAINS);
        hill = new TileImpl(GameConstants.HILLS);
        forrest = new TileImpl(GameConstants.FOREST);
        ocean = new TileImpl(GameConstants.OCEANS);
    }


    @Test
    public void shouldBe1ProductionAnd0FoodForMountainTile(){
        // Given a mountain object
        // When its implemented
        // Then it should have a food production of 0 and production of 1
        assertThat(mountain.getProductionProduction(), is(1));
        assertThat(mountain.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe2ProductionAnd0FoodForHillTile(){
        // Given a hill object
        // When its implemented
        // Then it should have a food production of 0 and production of 3
        assertThat(hill.getProductionProduction(), is(2));
        assertThat(hill.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe3ProductionAnd0FoodForForrestTile(){
        // Given a forrest object
        // When its implemented
        // Then it should have a food production of 0 and production of 3
        assertThat(forrest.getProductionProduction(), is(3));
        assertThat(forrest.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe0ProductionAnd1FoodForOceanTile(){
        // Given an ocean object
        // When its implemented
        // Then it should have a food production of 1 and production of 0
        assertThat(ocean.getProductionProduction(), is(0));
        assertThat(ocean.getFoodProduction(), is(1));
    }

    @Test
    public void shouldBe0ProductionAnd3FoodForPlainTile(){
        // Given a plains object
        // When its implemented
        // Then it should have a food production of 3 and production of 0
        assertThat(plains.getProductionProduction(), is(0));
        assertThat(plains.getFoodProduction(), is(3));
    }

    @Test
    public void shouldBeBattleMultiplierOf2ForHills() {
        assertThat(hill.getBattleMultiplier(), is(2));
    }

    @Test
    public void shouldBeBattleMultiplierOf2ForForest() {
        assertThat(forrest.getBattleMultiplier(), is(2));

    }

    @Test
    public void shouldBeBattleMultiplierOf1ForPlains() {
        assertThat(plains.getBattleMultiplier(), is(1));
    }

    @Test
    public void shouldBeBattleMultiplierOf0ForOceanAndMountain() {
        assertThat(mountain.getBattleMultiplier(), is(0));
        assertThat(ocean.getBattleMultiplier(), is(0));
    }
}
