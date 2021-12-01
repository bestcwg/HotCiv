package hotciv.stub;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

public class StubTile3 implements Tile {
    private String tile;
    public StubTile3(String tileType) {
        this.tile = tileType;
    }
    @Override
    public String getTypeString() {
        return tile;
    }
}
