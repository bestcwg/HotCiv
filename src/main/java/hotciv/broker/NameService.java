package hotciv.broker;

import hotciv.framework.City;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import java.util.HashMap;

public class NameService {
    private HashMap<String, Unit> unitMap;
    private HashMap<String, City> cityMap;
    private HashMap<String, Tile> tileMap;

    public NameService() {
        unitMap = new HashMap<>();
        cityMap = new HashMap<>();
        tileMap = new HashMap<>();

    }

    public void putUnit(String id, Unit unit) {
        unitMap.put(id, unit);
    }

    public void putCity(String id, City city) {
        cityMap.put(id, city);
    }

    public void putTile(String id, Tile tile) {
        tileMap.put(id, tile);
    }

    public Unit getUnit(String id) {
        return unitMap.get(id);
    }

    public City getCity(String id) {
        return cityMap.get(id);
    }

    public Tile getTile(String id) {
        return tileMap.get(id);
    }
}
