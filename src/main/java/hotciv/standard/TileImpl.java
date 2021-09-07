package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Tile;
import java.util.HashMap;

public class TileImpl implements Tile {

    private Position place;
    private HashMap<Position, String> tiles;

    public TileImpl(Position p) {
        this.place = p;
        this.tiles = new HashMap<Position, String>();
    }

    @Override
    public String getTypeString() {
        createHash();
        if (tiles.containsKey(place)) {
            return tiles.get(place).toString();
        }
        return "PLAIN";
    }
    // spørg om dette er ok
    public void createHash() {
        Position oceanTile = new Position(1,0);
        Position hillTile = new Position(0,1);
        Position mountainTile = new Position(2,2);

        tiles.put(oceanTile, "OCEAN");
        tiles.put(hillTile, "HILL");
        tiles.put(mountainTile, "MOUNTAIN");
    }
}
