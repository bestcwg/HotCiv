package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

public class TileImpl implements Tile {
    private final String tileType;
    private int foodProduce;
    private int productionProduce;

    /**
     * Constructor for the tile implementation
     * @param tileType the type of the tile (Ocean,Hill,Mountain,Plain)
     */
    public TileImpl(String tileType) {
        this.tileType = tileType;
        switch (tileType){
            case GameConstants.MOUNTAINS:
                foodProduce = GameConstants.MOUNTAIN_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.MOUNTAIN_FOOD_AND_PRODUCTION[1];
                break;
            case GameConstants.FOREST:
                foodProduce = GameConstants.FOREST_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.FOREST_FOOD_AND_PRODUCTION[1];
                break;
            case GameConstants.HILLS:
                foodProduce = GameConstants.HILLS_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.HILLS_FOOD_AND_PRODUCTION[1];
                break;
            case GameConstants.OCEANS:
                foodProduce = GameConstants.OCEAN_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.OCEAN_FOOD_AND_PRODUCTION[1];
                break;
            case GameConstants.PLAINS:
                foodProduce = GameConstants.PLAINS_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.PLAINS_FOOD_AND_PRODUCTION[1];
                break;
            case GameConstants.DESERT:
                foodProduce = GameConstants.DESERT_FOOD_AND_PRODUCTION[0];
                productionProduce = GameConstants.DESERT_FOOD_AND_PRODUCTION[1];
        }
    }

    @Override
    public String getTypeString() {
        return tileType;
    }

    public int getFoodProduction() {
        return foodProduce;
    }

    public int getProductionProduction() {
        return productionProduce;
    }

}
