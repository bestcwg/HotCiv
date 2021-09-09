package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

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
  private HashMap<Position, Tile> tiles;
  private Player playerInTurn = Player.RED;
  private int age = -4000;
  private int playerTurns;

  public GameImpl() {
    tiles = new HashMap<>();
    cities = new HashMap<>();
    units = new HashMap<>();

    for (int i = 0; i <= GameConstants.WORLDSIZE-1; i++) {
      for (int j = 0; j <= GameConstants.WORLDSIZE-1; j++){
        tiles.put(new Position(i,j),new TileImpl(GameConstants.PLAINS));
      }
    }
    createHashMapForCities();
    createHashMapForSpecialTiles();
    createHashMapForUnits();
  }

  public Tile getTileAt(Position p ) { return tiles.get(p); }

  public Unit getUnitAt( Position p ) { return units.get(p); }

  public City getCityAt( Position p ) { return cities.get(p); }

  public Player getPlayerInTurn() { return playerInTurn; }

  public Player getWinner() { return Player.RED; }

  public int getAge() { return age; }

  public boolean moveUnit( Position from, Position to ) {
    if (units.containsKey(from) && getUnitAt(from).getOwner() == playerInTurn) {
      if (getTileAt(to).getTypeString() == GameConstants.MOUNTAINS) {
        return false;
      }
      Unit unit = getUnitAt(from);
      units.put(to,unit);
      units.remove(from);
      return true;
    }
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
      for (Map.Entry<Position,City> city : cities.entrySet()) {
        city.getValue().addTreasury(6);
      }
      age += 100;
      playerTurns = 0;
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {}

  public void performUnitActionAt( Position p ) {}

  public void createHashMapForCities() {
    Position redCityPos = new Position(1,1);
    Position blueCityPos = new Position(4,1);

    cities.put(redCityPos, new CityImpl(Player.RED));
    cities.put(blueCityPos, new CityImpl(Player.BLUE));
  }

  public void createHashMapForUnits() {
    Position redArcherPos = new Position(2,0);
    Position blueLegionPos = new Position(3,2);
    Position redSettlerPos = new Position(4,3);

    units.put(redArcherPos, new UnitImpl(Player.RED, GameConstants.ARCHER));
    units.put(blueLegionPos, new UnitImpl(Player.BLUE, GameConstants.LEGION));
    units.put(redSettlerPos, new UnitImpl(Player.RED, GameConstants.SETTLER));
  }

  public void createHashMapForSpecialTiles() {
    Position oceanTile = new Position(1,0);
    Position hillTile = new Position(0,1);
    Position mountainTile = new Position(2,2);

    tiles.put(oceanTile, new TileImpl(GameConstants.OCEANS));
    tiles.put(hillTile, new TileImpl(GameConstants.HILLS));
    tiles.put(mountainTile, new TileImpl(GameConstants.MOUNTAINS));
  }
}
