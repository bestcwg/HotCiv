package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;

public interface WorldLayoutStrategy {
    /**
     * A abstract method for setting up world layouts. implement a concrete strategy
     * @return a hashmap of positions as keys, tiles as values, contains the entire world map
     */
    HashMap<Position, Tile> setUpWorld();

    /**
     * A abstract method for setting up the city layouts. implement a concrete strategy
     * @return a hashmap of positions as keys, cities as values, contains the starting cities
     */
    HashMap<Position, City> setUpCities();

    /**
     * A abstract method for setting up the unit layouts. implement a concrete strategy
     * @return a hashmap of positions as keys, units as values, contains the starting units
     */
    HashMap<Position, Unit> setUpUnits();
}
