package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import java.util.HashMap;

public class TileImpl implements Tile {

    private Position place;
    private HashMap<Position, String> tiles;

    public TileImpl(Position p) {
        this.place = p;
        this.tiles = new HashMap<Position, String>();
        createHash();
    }

    @Override
    public String getTypeString() {
        if (tiles.containsKey(place)) {
            return tiles.get(place).toString();
        }
        return GameConstants.PLAINS;
    }
    // spørg om dette er ok
    public void createHash() {
        Position oceanTile = new Position(1,0);
        Position hillTile = new Position(0,1);
        Position mountainTile = new Position(2,2);

        tiles.put(oceanTile, GameConstants.OCEANS);
        tiles.put(hillTile, GameConstants.HILLS);
        tiles.put(mountainTile, GameConstants.MOUNTAINS);
    }
}
