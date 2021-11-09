package hotciv.stub;

import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import java.util.*;

/** FakeObject implementation for Game. Base your
 * development of Tools and CivDrawing on this test double,
 * and gradually add EVIDENT TEST = simple code
 * to it, to support your development of all features
 * necessary for a complete CivDrawing and your suite
 * of Tools.
 *
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

public class FakeObjectGame implements Game {

  private Map<Position, Unit> unitMap;
  private Map<Position, City> cityMap;
  private int age = -4000;

  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

  public boolean moveUnit(Position from, Position to) {
    // Using print statements to aid in debugging and development
    System.out.println("-- FakeObjectGame / moveUnit called: " + from + "->" + to);
    StubUnit unit = (StubUnit) getUnitAt(from);
    if (unit == null) return false;
    if (unit.getMoveCount() == 0) return false;
    System.out.println("-- moveUnit found unit at: " + from);
    // Remember to inform game observer on any change on the tiles
    if (getUnitAt(to) != null) {
      unitMap.put(to,null);
      gameObserver.worldChangedAt(to);
    }
    unitMap.put(from, null);
    gameObserver.worldChangedAt(from);
    unitMap.put(to, unit);
    gameObserver.worldChangedAt(to);
    unit.moved();
    return true;
  }

  // === Turn handling ===
  private Player inTurn;
  public void endOfTurn() {
    System.out.println( "-- FakeObjectGame / endOfTurn called." );

    inTurn = (getPlayerInTurn() == Player.RED ?
              Player.BLUE : 
              Player.RED );

    // Fake it for age increments
    if (inTurn == Player.RED) {
      age += 100;
      for (Map.Entry<Position, Unit> u : unitMap.entrySet()) {
        if (u.getValue() != null) {
          StubUnit unit = (StubUnit) u.getValue();
          unit.resetMoveCount();
        }
      }
    }

    gameObserver.turnEnds(inTurn, getAge());
  }
  public Player getPlayerInTurn() { return inTurn; }

  // === Observer handling ===
  protected GameObserver gameObserver;
  // observer list is fake code, only having a single
  // one, suffices for development.
  public void addObserver(GameObserver observer) {
    gameObserver = observer;
  } 

  public FakeObjectGame() {
    defineWorld();
    // Put some units into play
    unitMap = new HashMap<>();
    unitMap.put(new Position(2,0), new StubUnit( GameConstants.ARCHER, Player.RED ));
    unitMap.put(new Position(3,2), new StubUnit( GameConstants.LEGION, Player.BLUE ));
    unitMap.put(new Position(4,2), new StubUnit( GameConstants.SETTLER, Player.RED ));
    unitMap.put(new Position(6,3), new StubUnit( ThetaConstants.SANDWORM, Player.RED ));
    inTurn = Player.RED;

    cityMap = new HashMap<>();
    cityMap.put(new Position(8,8), new StubCity(Player.RED));
    cityMap.put(new Position(11,11), new StubCity(Player.BLUE));
  }

  // A simple implementation to draw the map of DeltaCiv
  protected Map<Position,Tile> world; 
  public Tile getTileAt( Position p ) { return world.get(p); }

  /** define the world.
   */
  protected void defineWorld() {
    world = new HashMap<Position,Tile>();
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        Position p = new Position(r,c);
        world.put( p, new StubTile(GameConstants.PLAINS));
      }
    }
    // Create a little area around the theta unit of special terrain
    world.put(new Position(5,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,2), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,3), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(6,5), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,3), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,4), new StubTile(ThetaConstants.DESERT));
    world.put(new Position(7,5), new StubTile(ThetaConstants.DESERT));
  }

  // TODO: Add more fake object behaviour to test MiniDraw updating
  public City getCityAt( Position p ) { return cityMap.get(p); }
  public Player getWinner() { return null; }
  public int getAge() { return age; }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}  

  public void setTileFocus(Position position) {
    // TODO: setTileFocus implementation pending.
    System.out.println("-- FakeObjectGame / setTileFocus called.");
    System.out.println(" *** IMPLEMENTATION PENDING ***");
    gameObserver.tileFocusChangedAt(position);
  }
}

class StubUnit implements  Unit {
  private String type;
  private Player owner;
  private int moveCount;
  public StubUnit(String type, Player owner) {
    this.type = type;
    this.owner = owner;
    moveCount = 2;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() { return moveCount; }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }
  public void moved() {
    moveCount -= 1;
  }
  public void resetMoveCount() {
    moveCount = 2;
  }
}

class StubCity implements City {
  private Player owner;
  private String production = GameConstants.ARCHER;
  private int treasury = 6;
  private String workForce = GameConstants.productionFocus;

  public StubCity(Player owner) {
    this.owner = owner;
  }

  public Player getOwner() {
    return owner;
  }

  public int getSize() {
    return 1;
  }

  public int getTreasury() {
    return treasury;
  }

  //TODO: Fake-it kode
  public String getProduction() {
    return production;
  }

  public String getWorkforceFocus() {
    return workForce;
  }
}
