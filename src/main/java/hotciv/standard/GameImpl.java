package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;

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
  private Player playerInTurn = Player.RED;
  private int age = -4000;
  private int playerTurns;

  public Tile getTileAt( Position p ) {
    Tile tile = new TileImpl(p);
    return tile;
  }
  public Unit getUnitAt( Position p ) {
    createHashMapForUnits();
    return units.get(p);
  }

  public City getCityAt( Position p ) {
    createHashForCities();
    return cities.get(p);
  }

  public Player getPlayerInTurn() { return playerInTurn; }

  public Player getWinner() { return Player.RED; }

  public int getAge() { return age; }

  public boolean moveUnit( Position from, Position to ) {
    return false;
  }

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
      age += 100;
      playerTurns = 0;
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {}

  public void performUnitActionAt( Position p ) {}

  public void createHashForCities() {
    cities = new HashMap<>();
    Position redCityPos = new Position(1,1);
    Position blueCityPos = new Position(1,4);

    City redCity = new CityImpl(Player.RED);
    City blueCity = new CityImpl(Player.BLUE);

    cities.put(redCityPos, redCity);
    cities.put(blueCityPos, blueCity);
  }

  public void createHashMapForUnits() {
    units = new HashMap<>();
    Position redUnitPos = new Position(0,2);
    Position blueUnitPos = new Position(3,2);

    Unit redUnit = new UnitImpl(Player.RED, GameConstants.ARCHER);
    Unit blueUnit = new UnitImpl(Player.BLUE, GameConstants.LEGION);

    units.put(redUnitPos, redUnit);
    units.put(blueUnitPos, blueUnit);
  }

}
