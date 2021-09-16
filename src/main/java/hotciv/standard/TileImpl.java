package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import java.util.HashMap;

public class TileImpl implements Tile {
    private String tileType;
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
                foodProduce = 0;
                productionProduce = 1;
                break;
            case GameConstants.FOREST:
                foodProduce = 0;
                productionProduce =3;
                break;
            case GameConstants.HILLS:
                foodProduce = 0;
                productionProduce = 2;
                break;
            case GameConstants.OCEANS:
                foodProduce = 1;
                productionProduce = 0;
                break;
            case GameConstants.PLAINS:
                foodProduce = 3;
                productionProduce = 0;

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
