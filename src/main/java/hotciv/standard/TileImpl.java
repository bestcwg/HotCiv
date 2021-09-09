package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import java.util.HashMap;

public class TileImpl implements Tile {
    private String tileType;

    /**
     * Constructor for the tile implementation
     * @param tileType the type of the tile (Ocean,Hill,Mountain,Plain)
     */
    public TileImpl(String tileType) {
        this.tileType = tileType;
    }

    @Override
    public String getTypeString() {
        return tileType;
    }
}
