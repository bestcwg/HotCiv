package hotciv.standard;

import hotciv.framework.*;
import hotciv.utility.*;

import java.util.*;

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
  private int age;
  private int playerTurnsTaken;
  private AgeStrategy ageStrategy;
  private WinnerStrategy winnerStrategy;
  private PerformUnitActionStrategy performUnitActionStrategy;
  private WorldLayoutStrategy worldLayoutStrategy;

  /**
   * Constructor for the GameImplementation class
   * Instantiate starting player and age ,the world map, and create necessary hashmaps
   */
  public GameImpl(AgeStrategy ageStrategy, WinnerStrategy winnerStrategy, PerformUnitActionStrategy performUnitActionStrategy, WorldLayoutStrategy worldLayoutStrategy, String[] worldLayoutString) {
    playerInTurn = Player.RED;
    age = -4000;

    this.ageStrategy = ageStrategy;
    this.winnerStrategy = winnerStrategy;
    this.performUnitActionStrategy = performUnitActionStrategy;
    this.worldLayoutStrategy = worldLayoutStrategy;

    worldMap = worldLayoutStrategy.setUpWorld(worldLayoutString);
    cities = worldLayoutStrategy.setUpCities();
    units = worldLayoutStrategy.setUpUnits();
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
  public boolean moveUnit(Position from, Position to) {
    UnitImpl unitImpl = (UnitImpl) getUnitAt(from);
    // Checks if the unit exists, and that the player in turn is the owner of the player, and that the selected unit has a positive move count
    if (units.containsKey(from) && getUnitAt(from).getOwner() == getPlayerInTurn() && getUnitAt(from).getMoveCount() >= 1 && unitImpl.isMoveable() ) {
      // check that the unit is not moving over a mountain or ocean
      if (getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS) || getTileAt(to).getTypeString().equals(GameConstants.OCEANS)) {
        return false;
      }
      // makes sure units only move 1 tile at a time
      if (-1 <= (from.getColumn() - to.getColumn()) && (from.getColumn() - to.getColumn()) <= 1) {
        if (-1 <= (from.getRow() - to.getRow()) && (from.getRow() - to.getRow()) <= 1) {
          // for handling what to do if unit is at to position
          if (units.containsKey(to)) {
            // if it is the players unit don't move to pos, as player can't have two units on same pos
            if (getUnitAt(from).getOwner() == getUnitAt(to).getOwner()) {
              return false;
            }
            // if it is enemy unit, remove that unit
            units.remove(to);
          }

          // if there is a city that the player doesn't own, capture it
          if (cities.containsKey(to)) {
            if (getCityAt(to).getOwner() != getUnitAt(from).getOwner()) {
              CityImpl city = (CityImpl) getCityAt(to);
              city.changeOwner(getUnitAt(from).getOwner());
              checkForWinner(age, cities);
            }
          }

          // if position to is empty, just move unit to tile
          moveUnitFrom_To(from,to);
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
    playerTurnsTaken++;
    if (playerTurnsTaken == 2) {
      endOfRound();
    }
  }

  /**
   * A method for changing the work force focus in the city
   * @param p the position of the city whose focus
   * should be changed.
   * @param balance a string defining the focus of the work
   * force in a city. Valid values are at least
   * GameConstants.productionFocus
   */
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {
    if(getPlayerInTurn() == cities.get(p).getOwner()) {
      CityImpl city = (CityImpl) cities.get(p);
      city.changeWorkForceFocus(balance);
    }
  }

  /**
   * A Method for changing the production in a city
   * @param p the position of the city
   * @param unitType a string defining the type of unit that the city should produce
   *                 valid units are GameConstants ARCHER,LEGION,SETTLER
   */
  public void changeProductionInCityAt( Position p, String unitType ) {
    if(getPlayerInTurn() == getCityAt(p).getOwner()) {
      if (cities.containsKey(p) && (unitType.equals(GameConstants.ARCHER) || unitType.equals(GameConstants.LEGION) || unitType.equals(GameConstants.SETTLER))) {
        CityImpl city = (CityImpl) getCityAt(p);
        city.changeProduction(unitType);
      }
    }
  }

  public void performUnitActionAt( Position p ) {
    performUnitActionStrategy.action(p, getUnitAt(p), cities, units);
  }

  /**
   * A method that ends the round
   * When a round ends, the move count of units are reset, the cities add and produce their production
   * if there is enough treasure, and the age of the game processes. If the age reaches 3000 BC red player wins
   */
  private void endOfRound() {
    // Reset move count for all units
    resetMoveCountForAllUnits();
    produceProductionForAllCities();
    // increment the age
    age += ageStrategy.calculateAge(getAge());
    playerTurnsTaken = 0;
    checkForWinner(getAge(), cities);
  }

  /**
   * A helper method for handling unit creation. A unit is created in or around the city based on if the tile is empty or not
   * @param cityPosition the position of the city
   * @param city and the actual city object
   */
  private void createUnit(Position cityPosition, City city) {
    // loop though the neighborhood of a city using the provided utility class
    for (Position neighborhoodPosition : Utility.get8neighborhoodOf(cityPosition)) {
      String concreteTile = getTileAt(neighborhoodPosition).getTypeString();
      // if there is no unit at the city center place a unit here
      if (getUnitAt(cityPosition) == null) {
        units.put(cityPosition, new UnitImpl(city.getOwner(), city.getProduction()));
        break;
        // Otherwise, run through the neighborhood to find a legal spot to place the unit
      } else if (getUnitAt(neighborhoodPosition) == null &&
              !concreteTile.equals(GameConstants.MOUNTAINS)
              && !concreteTile.equals(GameConstants.OCEANS)) {
        units.put(neighborhoodPosition, new UnitImpl(city.getOwner(), city.getProduction()));
        break;
      }
    }
  }

  /**
   * A helper method that moves a unit to a new position and
   * removes the old pos from the hashmap of units
   * @param from where the unit is
   * @param to where the unit is going
   */
  private void moveUnitFrom_To(Position from, Position to) {
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    units.put(to,unit);
    units.remove(from);
    unit.retractMoveCount();
  }

  private void resetMoveCountForAllUnits() {
    for (Map.Entry<Position, Unit> u : units.entrySet()) {
      UnitImpl unit = (UnitImpl) u.getValue();
      unit.resetMoveCount();
    }
  }

  private void produceProductionForAllCities() {
    int cityProductionAmount = 6;
    // Loop though all the cities in the HashMap to change production and producce for each
    for (Map.Entry<Position, City> cityEntry : cities.entrySet()) {
      // Typecast to access methods in the impl of city class
      CityImpl realCity = (CityImpl) cityEntry.getValue();

      // Set up descriptive names for values used in the switch case
      realCity.changeTreasury(cityProductionAmount);
      int cityTreasury = realCity.getTreasury();

      // a measure to make sure tests without a production focus doesn't produce null pointer expections
      if (realCity.getProduction() != null) {
        // Set up descriptive names for values used in the switch case
        String cityProduction = realCity.getProduction();
        int costOfUnit = GameConstants.unitCost.get(cityProduction);

        // Switching over the production focus of the city to see if there is enough production to produce it
        switch (cityProduction) {
          case GameConstants.ARCHER:
          case GameConstants.LEGION:
          case GameConstants.SETTLER:
            if (cityTreasury >= costOfUnit) {
              realCity.changeTreasury(-costOfUnit);
              createUnit(cityEntry.getKey(), realCity);
            }
            break;
        }
      }
    }
  }



  /**
   * A helper method to calculate winner depending on
   * which winnerStrategy is in use
   * @param age of current Game
   * @param cities HashMap of cities in game
   */
  private void checkForWinner(int age, HashMap<Position,City> cities) {
    winner = winnerStrategy.calculateWinner(age, cities);
  }
}