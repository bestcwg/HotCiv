package hotciv.variants.deltaCiv;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.strategies.WorldLayoutStrategy;

import java.util.HashMap;

public class DeltaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    private HashMap<Position, City> citiesLayout;
    private HashMap<Position, Unit> unitsLayout;
    @Override
    public HashMap<Position, Tile> setUpWorld() {
        // Basically we use a 'data driven' approach - code the
        // layout in a simple semi-visual representation, and
        // convert it to the actual Game representation.
        String[] layout =
                new String[] {
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Conversion...
        HashMap<Position,Tile> theWorld = new HashMap<Position,Tile>();
        String line;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                theWorld.put( p, new TileImpl(type));
            }
        }
        return theWorld;
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
     * A helper method for creating a hashmap of cities
     */
    private void createHashMapForCities() {
        Position redCityPos = new Position(8,12);
        Position blueCityPos = new Position(4,5);

        citiesLayout.put(redCityPos, new CityImpl(Player.RED));
        citiesLayout.put(blueCityPos, new CityImpl(Player.BLUE));
    }

    /**
     *  A helper method for creating a hashmap of units
     */
    private void createHashMapForUnits() {
        Position redSettlerPos = new Position(5,5);
        Position redArcherPos = new Position(3,8);
        Position blueLegionPos = new Position(4,4);

        unitsLayout.put(redSettlerPos, new UnitImpl(Player.RED, GameConstants.SETTLER));
        unitsLayout.put(redArcherPos, new UnitImpl(Player.RED, GameConstants.ARCHER));
        unitsLayout.put(blueLegionPos, new UnitImpl(Player.BLUE, GameConstants.LEGION));
    }
}
