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
        this.tiles = new HashMap<>();
        for (int i = 0; i <= GameConstants.WORLDSIZE-1; i++) {
            for (int j = 0; j <= GameConstants.WORLDSIZE-1; j++){
                tiles.put(new Position(i,j),GameConstants.PLAINS);
            }
        }
        createHashSpecialTiles();
    }

    @Override
    public String getTypeString() {
        return tiles.get(place);
    }

    // spørg om dette er ok
    public void createHashSpecialTiles() {
        Position oceanTile = new Position(1,0);
        Position hillTile = new Position(0,1);
        Position mountainTile = new Position(2,2);

        tiles.put(oceanTile, GameConstants.OCEANS);
        tiles.put(hillTile, GameConstants.HILLS);
        tiles.put(mountainTile, GameConstants.MOUNTAINS);
    }
}
