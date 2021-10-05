package hotciv.unitTests;

import hotciv.framework.GameConstants;
import hotciv.standard.TileImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTileImpl {

    @Test
    public void shouldBe1ProductionAnd0FoodForMountainTile(){
        // Given a mountain object
        // When its implemented
        // Then it should have a food production of 0 and production of 1
        TileImpl mountain = new TileImpl(GameConstants.MOUNTAINS);
        assertThat(mountain.getProductionProduction(), is(1));
        assertThat(mountain.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe2ProductionAnd0FoodForHillTile(){
        // Given a hill object
        // When its implemented
        // Then it should have a food production of 0 and production of 3
        TileImpl hill = new TileImpl(GameConstants.HILLS);
        assertThat(hill.getProductionProduction(), is(2));
        assertThat(hill.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe3ProductionAnd0FoodForForrestTile(){
        // Given a forrest object
        // When its implemented
        // Then it should have a food production of 0 and production of 3
        TileImpl forrest = new TileImpl(GameConstants.FOREST);
        assertThat(forrest.getProductionProduction(), is(3));
        assertThat(forrest.getFoodProduction(), is(0));
    }

    @Test
    public void shouldBe0ProductionAnd1FoodForOceanTile(){
        // Given an ocean object
        // When its implemented
        // Then it should have a food production of 1 and production of 0
        TileImpl ocean = new TileImpl(GameConstants.OCEANS);
        assertThat(ocean.getProductionProduction(), is(0));
        assertThat(ocean.getFoodProduction(), is(1));
    }

    @Test
    public void shouldBe0ProductionAnd3FoodForPlainTile(){
        // Given a plains object
        // When its implemented
        // Then it should have a food production of 3 and production of 0
        TileImpl plains = new TileImpl(GameConstants.PLAINS);
        assertThat(plains.getProductionProduction(), is(0));
        assertThat(plains.getFoodProduction(), is(3));
    }
}
