package hotciv.variants;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.TileImpl;
import hotciv.standard.WorldLayoutStrategy;

import java.util.HashMap;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    private HashMap<Position, Tile> worldLayout;
    @Override
    public HashMap<Position, Tile> setUpWorld() {
        worldLayout = new HashMap<>();
        for (int i = 0; i <= GameConstants.WORLDSIZE-1; i++) {
            for (int j = 0; j <= GameConstants.WORLDSIZE-1; j++){
                worldLayout.put(new Position(i,j),new TileImpl(GameConstants.PLAINS));
            }
        }
        createHashMapForSpecialTiles();
        return worldLayout;
    }

    /**
     * A helper method for creating a hashmap of tiles
     */
    private void createHashMapForSpecialTiles() {
        Position oceanTile = new Position(1, 0);
        Position hillTile = new Position(0, 1);
        Position mountainTile = new Position(2, 2);

        worldLayout.put(oceanTile, new TileImpl(GameConstants.OCEANS));
        worldLayout.put(hillTile, new TileImpl(GameConstants.HILLS));
        worldLayout.put(mountainTile, new TileImpl(GameConstants.MOUNTAINS));
    }
}
