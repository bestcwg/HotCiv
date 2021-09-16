package hotciv.standard;

import hotciv.framework.*;
import hotciv.utility.*;

import java.util.*;
import java.lang.Math.*;

/** Skeleton implementation of HotCiv.
 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

public class GameImpl implements Game {
  private HashMap<Position, City> cities;
  private HashMap<Position, Unit> units;
  private HashMap<Position, Tile> worldMap;
  private Player playerInTurn;
  private Player winner;
  private int age = -4000;
  private int playerTurns;

  /**
   * Constructor for the GameImplementation class
   * Instantiate the world map, and create necessary hashmaps
   */
  public GameImpl() {
    playerInTurn = Player.RED;

    worldMap = new HashMap<>();
    cities = new HashMap<>();
    units = new HashMap<>();

    for (int i = 0; i <= GameConstants.WORLDSIZE-1; i++) {
      for (int j = 0; j <= GameConstants.WORLDSIZE-1; j++){
        worldMap.put(new Position(i,j),new TileImpl(GameConstants.PLAINS));
      }
    }
    createHashMapForCities();
    createHashMapForSpecialTiles();
    createHashMapForUnits();
  }

  /**
   * A method for getting a tile object at a given position
   * @param p the position in the world that must be returned.
   * @return the type of tile (Plains,Ocean,Hill,Mountain)
   */
  public Tile getTileAt(Position p ) { return worldMap.get(p); }

  /**
   * A method for getting a unit object at a given position
   * @param p the position in the world.
   * @return the unit at the given position
   */
  public Unit getUnitAt( Position p ) { return units.get(p); }

  /**
   * A method for getting a city object at a given position
   * @param p the position in the world.
   * @return the city at the given position
   */
  public City getCityAt( Position p ) { return cities.get(p); }

  /**
   * A method for getting the player in turn
   * @return the player Enum who is currently having their turn
   */
  public Player getPlayerInTurn() { return playerInTurn; }

  /**
   * A method for getting the winner of a given game
   * @return a player Enum of the winning player
   */
  public Player getWinner() {
    return winner;
  }

  /**
   * A method for getting the year the game is currently in
   * @return an int representation of the given year, a negative for BC
   * And positive for AD
   */
  public int getAge() { return age; }

  /**
   * A method for moving a unit around the map
   * It validates that the player in turn is moving a unit of their colour
   * and that the move is valid (Do not move over mountain)
   * @param from the position that the unit has now
   * @param to the position the unit should move to
   * @return a boolean value, false if the move failed and true if it succeeds
   */
  public boolean moveUnit( Position from, Position to ) {
    if (units.containsKey(from) && getUnitAt(from).getOwner() == playerInTurn) {
      if (getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS) || getTileAt(to).getTypeString().equals(GameConstants.OCEANS)) {
        return false;
      }
      if (units.containsKey(to)) {
        // if loop for handling moving units to different tiles
        if (getUnitAt(from).getOwner() == getUnitAt(to).getOwner()) {
          // to make sure two units with the same owner cannot stand on the same tile
          return false;
        }
        // For when a unit is attacking another unit
        units.remove(to);
        moveUnitFrom_To(from,to);
        return true;
      }
      // for when a unit is moving to an empty tile
      if (-1 <= (from.getColumn() - to.getColumn()) && (from.getColumn() - to.getColumn()) <= 1) {
        if (-1 <= (from.getRow() - to.getRow()) && (from.getRow() - to.getRow()) <= 1) {
          moveUnitFrom_To(from, to);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * A method for ending the turn of a player
   * A switch case that alternates and assigns player in turn
   * calls endOfRound() method after each player has a turn
   */
  public void endOfTurn() {
    switch (playerInTurn) {
      case RED:
        playerInTurn = Player.BLUE;
        break;
      case BLUE:
        playerInTurn = Player.RED;
        break;
    }
    playerTurns++;
    if (playerTurns == 2) {
      endOfRound();
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {
    if(getPlayerInTurn() == cities.get(p).getOwner()) {
      CityImpl city = (CityImpl) cities.get(p);
      city.changeWorkForceFocus(balance);
    }
  }

  public void changeProductionInCityAt( Position p, String unitType ) {
    if(getPlayerInTurn() == cities.get(p).getOwner()) {
      if (cities.containsKey(p) && (unitType.equals(GameConstants.ARCHER) || unitType.equals(GameConstants.LEGION) || unitType.equals(GameConstants.SETTLER))) {
        CityImpl city = (CityImpl) cities.get(p);
        city.changeProduction(unitType);
      }
    }
  }

  public void performUnitActionAt( Position p ) {}

  /**
   * A helper method for creating a hashmap of cities
   */
  public void createHashMapForCities() {
    Position redCityPos = new Position(1,1);
    Position blueCityPos = new Position(4,1);

    cities.put(redCityPos, new CityImpl(Player.RED));
    cities.put(blueCityPos, new CityImpl(Player.BLUE));
  }

  /**
   * A helper method for creating a hashmap of units
   */
  public void createHashMapForUnits() {
    Position redArcherPos = new Position(2,0);
    Position blueLegionPos = new Position(3,2);
    Position redSettlerPos = new Position(4,3);

    units.put(redArcherPos, new UnitImpl(Player.RED, GameConstants.ARCHER));
    units.put(blueLegionPos, new UnitImpl(Player.BLUE, GameConstants.LEGION));
    units.put(redSettlerPos, new UnitImpl(Player.RED, GameConstants.SETTLER));
  }

  /**
   * A helper method for creating a hashmap of tiles
   */
  public void createHashMapForSpecialTiles() {
    Position oceanTile = new Position(1,0);
    Position hillTile = new Position(0,1);
    Position mountainTile = new Position(2,2);

    worldMap.put(oceanTile, new TileImpl(GameConstants.OCEANS));
    worldMap.put(hillTile, new TileImpl(GameConstants.HILLS));
    worldMap.put(mountainTile, new TileImpl(GameConstants.MOUNTAINS));
  }

  /**
   * A method that ends the round, it will progress the age of the game
   * and increase the treasury of the cities in the game
   */
  public void endOfRound() {
    // Reset move count for all units
    for (Map.Entry<Position, Unit> u : units.entrySet()) {
      UnitImpl unit = (UnitImpl) u.getValue();
      unit.resetMoveCounter();
    }
    // loop through all the cities in the cities hashmap for unit production
    for (Map.Entry<Position, City> c : cities.entrySet()) {
      // typecast to CityImpl to make sure we can access changeTreasury to add production
      CityImpl city = (CityImpl) c.getValue();
      city.changeTreasury(6);
      // a measure to make sure tests don't fail if a production isn't set
      if(city.getProduction() != null) {
        switch (city.getProduction()) {
          case GameConstants.ARCHER:
            // Check if the treasury of the city is enough to produces the city production focus
            if (city.getTreasury() >= 10) {
              // reduce the city's treasure with the amount of production needed for the unit
              city.changeTreasury(-10);
              createUnit(c.getKey(), city);
            }
            break;
          case GameConstants.LEGION:
            // Check if the treasury of the city is enough to produces the city production focus
            if (city.getTreasury() >= 15) {
              // reduce the city's treasure with the amount of production needed for the unit
              city.changeTreasury(-15);
              createUnit(c.getKey(), city);
            }
            break;
          case GameConstants.SETTLER:
            // Check if the treasury of the city is enough to produces the city production focus
            if (city.getTreasury() >= 30) {
              city.changeTreasury(-30);
              // reduce the city's treasure with the amount of production needed for the unit
              createUnit(c.getKey(), city);
            }
        }
      }
    }
    // increment the age
    age += 100;
    playerTurns = 0;
    if (getAge() == -3000) {
      winner = Player.RED;
    }
  }

  private void createUnit(Position c, City city) {
    // loop though the neighborhood of a city using the provided utility class
    for (Position p : Utility.get8neighborhoodOf(c)) {
      // if there is no unit at the city center place a unit here
      if (getUnitAt(c) == null) {
        units.put(c, new UnitImpl(city.getOwner(), city.getProduction()));
        // Otherwise, run through the neighborhood to find a legal spot to place the unit
      } else if (getUnitAt(p) == null) {
        units.put(p, new UnitImpl(city.getOwner(), city.getProduction()));
      }
    }
  }

  /**
   * A helper method that moves a unit to a new position and
   * removes the old pos from the hashmap of units
   * @param from where the unit is
   * @param to where the unit is going
   */
  public void moveUnitFrom_To(Position from, Position to) {
    Unit unit = getUnitAt(from);
    units.put(to,unit);
    units.remove(from);
  }
}