package hotciv.stub;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

import java.util.UUID;

public class StubTile3 implements Tile {
    private String tile;
    private final String id;
    public StubTile3(String tileType) {
        this.tile = tileType;
        id = UUID.randomUUID().toString();
    }
    @Override
    public String getTypeString() {
        return tile;
    }

    @Override
    public String getId() {
        return id;
    }
}
