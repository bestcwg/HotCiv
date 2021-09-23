package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Tile;

import java.util.HashMap;

public interface WorldLayoutStrategy {
    HashMap<Position, Tile> setUpWorld();
}
