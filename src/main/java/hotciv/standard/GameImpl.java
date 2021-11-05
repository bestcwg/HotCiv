package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.strategies.*;
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
  private CivFactory civFactory;
  private HashMap<Position, City> cities;
  private HashMap<Position, Unit> units;
  private HashMap<Position, Tile> worldMap;
  private Player playerInTurn;
  private Player winner;
  private int age;
  private int numberOfPlayers;
  private int playerTurnsTaken;
  private AgeStrategy ageStrategy;
  private WinnerStrategy winnerStrategy;
  private PerformUnitActionStrategy performUnitActionStrategy;
  private WorldLayoutStrategy worldLayoutStrategy;
  private AttackingStrategy attackingStrategy;
  private MoveStrategy moveStrategy;
  private LegalUnitsStrategy legalUnitsStrategy;
  private CreateUnitStrategy createUnitStrategy;
  private int roundCounter;

  /**
   * Constructor for the GameImplementation class
   * Instantiate starting player and age ,the world map, and create necessary hashmaps
   */
  public GameImpl(CivFactory civFactory) {
    this.civFactory = civFactory;
    playerInTurn = Player.RED;
    age = GameConstants.WORLD_AGE;
    numberOfPlayers = 2;
    roundCounter = 1;

    ageStrategy = civFactory.createAgeStrategy();
    winnerStrategy = civFactory.createWinnerStrategy();
    performUnitActionStrategy = civFactory.createUnitActionStrategy();
    worldLayoutStrategy = civFactory.createWorldLayoutStrategy();
    attackingStrategy = civFactory.createAttackStrategy();
    moveStrategy = civFactory.createMoveStrategy();
    legalUnitsStrategy = civFactory.createLegalUnitStrategy();
    createUnitStrategy = civFactory.createCreateUnitStrategy();

    worldMap = worldLayoutStrategy.setUpWorld();
    cities = worldLayoutStrategy.setUpCities();
    units = worldLayoutStrategy.setUpUnits();

    checkForWinner(this);
  }

  //region GetterMethods

  /**
   * A method for getting a tile object at a given position
   * @param tilePosition the position in the world that must be returned.
   * @return the type of tile (Plains,Ocean,Hill,Mountain)
   */
  public Tile getTileAt(Position tilePosition ) { return worldMap.get(tilePosition); }

  /**
   * A method for getting a unit object at a given position
   * @param unitPosition the position in the world.
   * @return the unit at the given position
   */
  public Unit getUnitAt( Position unitPosition ) { return units.get(unitPosition); }

  /**
   * A method for getting a city object at a given position
   * @param cityPosition the position in the world.
   * @return the city at the given position
   */
  public City getCityAt( Position cityPosition ) { return cities.get(cityPosition); }

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
   * A method for getting all the cities
   * @return A hashmap of all the cities
   */
  public HashMap<Position,City> getCities() {
    return cities;
  }

  /**
   * A method for getting all unites
   * @return a hashmap of all units
   */
  public HashMap<Position,Unit> getUnits() {
    return units;
  }

  /**
   * A method for getting the world map
   * @return a hashmap of tiles
   */
  public HashMap<Position,Tile> getWorldMap() {
    return worldMap;
  }

  /**
   * A method for getting the amount of rounds played
   * @return an int representing the amount of rounds played.
   */
  public int getRoundCounter() {
    return roundCounter;
  }

  //endregion

  //region Move Unit Methods + Helper
   /**
   * A method for moving a unit around the map
   * It validates that the player in turn is moving a unit of their colour
   * and that the move is valid (Do not move over mountain)
   * @param from the position that the unit has now
   * @param to the position the unit should move to
   * @return a boolean value, false if the move failed and true if it succeeds
   */
  public boolean moveUnit( Position from, Position to ) {
    if (!moveStrategy.isValidMove(from, to, this)) {
      return false;
    }
    attackEnemyUnitIfAtToTile(from,to);
    boolean attackUnitLost = !units.containsKey(from);
    if (attackUnitLost) {
      return false;
    }
    boolean isEnemyCityAtToTile = cities.containsKey(to) && playerInTurn != getCityAt(to).getOwner();
    if (isEnemyCityAtToTile) {
      CityImpl city = (CityImpl) getCityAt(to);
      city.changeOwner(playerInTurn);
    }
    makeActualMove(from, to);
    checkForWinner(this);
    return true;
  }

  private void attackEnemyUnitIfAtToTile(Position from, Position to) {
    boolean isEnemyAtToTile = units.containsKey(to);
    if (isEnemyAtToTile) {
      boolean battleWon = attackingStrategy.calculateBattleWinner(from, to, this);
      if (battleWon) {
        units.remove(to);
        winnerStrategy.incrementBattlesWonBy(playerInTurn);
      } else {
        units.remove(from);
      }
    }
  }

  /**
   * A helper method that moves a unit to a new position and
   * removes the old pos from the hashmap of units
   * @param from where the unit is
   * @param to where the unit is going
   */
  private void makeActualMove(Position from, Position to) {
    UnitImpl unit = (UnitImpl) getUnitAt(from);
    units.put(to,unit);
    units.remove(from);
    unit.retractMoveCount();
  }

  //endregion

  //region Methods for turn and round ending

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
    if (playerTurnsTaken == numberOfPlayers) {
      endOfRound();
    }
  }

  /**
   * A method that ends the round
   * When a round ends, the move count of units are reset, the cities add and produce their production
   * if there is enough treasure, and the age of the game processes. If the age reaches 3000 BC red player wins
   */
  private void endOfRound() {
    resetMoveCountForAllUnits();
    produceProductionForAllCities();
    age += ageStrategy.calculateAge(getAge());
    playerTurnsTaken = 0;
    roundCounter++;
    checkForWinner(this);
  }

  /**
   * A helper method for resetting moveCount for all units
   */
  private void resetMoveCountForAllUnits() {
    for (Map.Entry<Position, Unit> u : units.entrySet()) {
      UnitImpl unit = (UnitImpl) u.getValue();
      unit.resetMoveCount();
    }
  }

  /**
   * A helper method for producing the selected production
   * in all cities
   */
  private void produceProductionForAllCities() {
    int cityProductionAmount = 6;
    for (Map.Entry<Position, City> cityEntry : cities.entrySet()) {
      // Typecast to access methods in the impl of city class
      CityImpl realCity = (CityImpl) cityEntry.getValue();

      realCity.changeTreasury(cityProductionAmount);
      int cityTreasury = realCity.getTreasury();

      // a measure to make sure tests without a production focus doesn't produce null pointer exceptions
      if (realCity.getProduction() != null && legalUnitsStrategy.isLegalUnit(realCity.getProduction())) {
        String cityProduction = realCity.getProduction();
        int costOfUnit = GameConstants.unitCost.get(cityProduction);

        if (cityTreasury >= costOfUnit) {
          realCity.changeTreasury(-costOfUnit);
          createUnitStrategy.createUnit(cityEntry.getKey(), realCity, this);
        }
      }
    }
  }

  //endregion

  //region City Production and Focus

  /**
   * A method for changing the work force focus in the city
   * @param cityPosition the position of the city whose focus
   * should be changed.
   * @param productionFocus a string defining the focus of the work
   * force in a city. Valid values are at least
   * GameConstants.productionFocus
   */
  public void changeWorkForceFocusInCityAt( Position cityPosition, String productionFocus ) {
    boolean playerOwnsCity = getPlayerInTurn() == cities.get(cityPosition).getOwner();
    if(playerOwnsCity) {
      CityImpl city = (CityImpl) cities.get(cityPosition);
      city.changeWorkForceFocus(productionFocus);
    }
  }

  /**
   * A Method for changing the production in a city
   * @param cityPosition the position of the city
   * @param unitType a string defining the type of unit that the city should produce
   *                 valid units are GameConstants ARCHER,LEGION,SETTLER
   */
  public void changeProductionInCityAt( Position cityPosition, String unitType ) {
    boolean playerOwnsCity = getPlayerInTurn() == cities.get(cityPosition).getOwner();
    boolean isUnit = (legalUnitsStrategy.isLegalUnit(unitType));

    if(cities.containsKey(cityPosition) && isUnit) {
      if (playerOwnsCity && isUnit) {
        CityImpl city = (CityImpl) getCityAt(cityPosition);
        city.changeProduction(unitType);
      }
    }
  }

  //endregion

  public void performUnitActionAt( Position unitPosition ) {
    performUnitActionStrategy.action(unitPosition, this);
  }

  @Override
  public void addObserver(GameObserver observer) {

  }

  @Override
  public void setTileFocus(Position position) {

  }

  /**
   * A helper method to calculate winner depending on
   * which winnerStrategy is in use
   * @param game the actual game
   */
  public void checkForWinner(Game game) {
    winner = winnerStrategy.calculateWinner(game);
  }

  //region MutatorMethods

  /**
   * A method for creating a city
   * @param cityPosition position of the city
   * @param city object
   */
  public void createCity(Position cityPosition, City city) {
    cities.put(cityPosition, city);
  }

  /**
   * A method for removing a unit at a position
   * @param unitPosition position of the unit
   */
  public void removeUnit(Position unitPosition) {
    units.remove(unitPosition);
  }
  //endregion
}