package hotciv.variants.alphaCiv;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.WorldLayoutStrategy;

import java.util.HashMap;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    private HashMap<Position, Tile> worldLayout;
    private HashMap<Position, City> citiesLayout;
    private HashMap<Position, Unit> unitsLayout;
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

    @Override
    public HashMap<Position, City> setUpCities() {
        citiesLayout = new HashMap<>();
        createHashMapForCities();
        return citiesLayout;
    }

    @Override
    public HashMap<Position, Unit> setUpUnits() {
        unitsLayout = new HashMap<>();
        createHashMapForUnits();
        return unitsLayout;
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

    /**
     * A helper method for creating a hashmap of cities
     */
    private void createHashMapForCities() {
        Position redCityPos = new Position(1,1);
        Position blueCityPos = new Position(4,1);

        citiesLayout.put(redCityPos, new CityImpl(Player.RED));
        citiesLayout.put(blueCityPos, new CityImpl(Player.BLUE));
    }

    /**
     * A helper method for creating a hashmap of units
     */
    private void createHashMapForUnits() {
        Position redArcherPos = new Position(2,0);
        Position blueLegionPos = new Position(3,2);
        Position redSettlerPos = new Position(4,3);

        unitsLayout.put(redArcherPos, new UnitImpl(Player.RED, GameConstants.ARCHER));
        unitsLayout.put(blueLegionPos, new UnitImpl(Player.BLUE, GameConstants.LEGION));
        unitsLayout.put(redSettlerPos, new UnitImpl(Player.RED, GameConstants.SETTLER));
    }
}
