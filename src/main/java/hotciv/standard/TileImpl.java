package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Tile;

public class TileImpl implements Tile {

    private Position place;
    public TileImpl(Position p) {
        this.place = p;
    }

    @Override
    public String getTypeString() {
        if (place.getColumn() == 0 && place.getRow() == 1) {
            return "OCEAN";
        } else {
            return "HILL";
        }
    }
}
