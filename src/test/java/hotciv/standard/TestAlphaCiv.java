package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.factories.AlphaCivFactory;
import hotciv.variants.alphaCiv.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Skeleton class for AlphaCiv test cases

   Updated Aug 2020 for JUnit 5 includes
   Updated Oct 2015 for using Hamcrest matchers

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
public class TestAlphaCiv {
  private Game game;
  private City city;
  private Unit unit;

  private Position archerPos;
  private Position legionPos;
  private Position settlerPos;
  private Position redCityPos;
  private Position blueCityPos;
  private Position position;
  private Position newPos;

  /** Fixture for alphaciv testing. */
  @BeforeEach
  public void setUp() {
    game = new GameImpl(new AlphaCivFactory());
    archerPos = new Position(2,0); // The archers' owner is red
    legionPos = new Position(3,2); // The Legions' owner is blue
    settlerPos = new Position(4,3); // The settler' owner is red
    redCityPos = new Position(1,1);
    blueCityPos = new Position(4,1);
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    // Given a game
    // When the game start
    // Then it is red players turn
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeOceanAt1_0() {
    // Given a game
    // When entering position 1,0
    position = new Position(1, 0);
    // Then the tile at the location is an ocean
    assertThat(game.getTileAt(position).getTypeString(), is(GameConstants.OCEANS));
  }

  @Test
  public void shouldBeHillAt0_1() {
    // Given a game
    // When entering position 0,2
    position = new Position(0, 1);
    // Then the tile at the location is a hill
    assertThat(game.getTileAt(position).getTypeString(), is(GameConstants.HILLS));
  }

  @Test
  public void shouldBeMountainAt2_2() {
    // Given a game
    // When entering position 2,2
    position = new Position(2, 2);
    // Then the tile at the location is a mountain
    assertThat(game.getTileAt(position).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  @Test
  public void shouldBePlainsThroughoutTheMap() {
    // Given a game
    // When entering position 0,0
    Position p = new Position(0,0);
    // Then the tile at the location is a plain
    assertThat(game.getTileAt(p).getTypeString(), is(GameConstants.PLAINS));

    // When entering position 6,6
    Position b = new Position(6,6);
    // Then the tile at the location is a plain
    assertThat(game.getTileAt(b).getTypeString(), is(GameConstants.PLAINS));

    // When entering position 8,4
    Position c = new Position(8,4);
    // Then the tile at the location is a plain
    assertThat(game.getTileAt(c).getTypeString(), is(GameConstants.PLAINS));

    // When entering position 15,15
    Position d = new Position(15,15);
    // Then the tile at the location is a plain
    assertThat(game.getTileAt(d).getTypeString(), is(GameConstants.PLAINS));
  }

  @Test
  public void shouldBeCityAt1_1() {
    // Given a game
    // When entering position 1,1
    // Then at that location there should be a city
    assertThat(game.getCityAt(redCityPos), is(notNullValue()));
  }

  @Test
  public void shouldBeRedCityAt1_1() {
    // Given a game
    // When entering position 1,1
    // Then the city at that location should be owned by red
    assertThat(game.getCityAt(redCityPos), is(notNullValue()));
    assertThat(game.getCityAt(redCityPos).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldBeBlueCityAt4_1() {
    // Given a game
    // When entering position 4,1
    // Then the city at that location should be owned by blue
    assertThat(game.getCityAt(blueCityPos), is(notNullValue()));
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBePopulationOf1InCityAlways() {
    // Given a game
    // When entering a city object
    City city = new CityImpl(Player.RED);
    // Then the size should be 1
    assertThat(city.getSize(),is(1));
  }

  @Test
  public void shouldBeBlueTurnAfterRedTurn() {
    // Given a game
    // When the turns end
    doXEndOfTurn(1);
    // Then it should be blue players turn
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  @Test
  public void shouldBeYear4000BCAtGameStart() {
    // Given a game
    // When the game starts
    // Then the age should be 4000BC (-4000)
    assertThat(game.getAge(), is(-4000));
  }

  @Test
  public void shouldIncrementTimeBy100AfterRoundEnd() {
    // Given a game
    // When the round ends (2 turns)
    assertThat(game.getAge(), is(-4000));
    doXEndOfTurn(2);
    // Then the age should progress 100 years
    assertThat(game.getAge(), is(-3900));
  }

  @Test
  public void shouldBeRedAsWinnerInYear3000BC() {
    // Given a game
    // When the age reaches 3000 BC
    assertThat(game.getAge(), is(-4000));
    assertThat(game.getWinner(), is(nullValue()));
    doXEndOfTurn(20);
    // Then player red should win
    assertThat(game.getAge(), is(-3000));
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void shouldIncreaseTreasuryBy6InEachCityAfterEachRound() {
    // Given a game
    // When the round ends
    doXEndOfTurn(2);
    // Then the treasury of each city should increase by 6
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(6));
    doXEndOfTurn(2);
    // City treasury is 2, as the city will by default produce archers
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(2));

    // Check for blue city as well
    assertThat(game.getCityAt(blueCityPos).getTreasury(), is(2));
  }

  @Test
  public void shouldBeUnitAt2_0() {
    // Given a game
    // When entering position 2,0
    position = new Position(2,0);
    // Then a unit should be at that tile
    assertThat(game.getUnitAt(position), is(notNullValue()));
  }

  @Test
  public void shouldBeAnArcherAt2_0() {
    // Given a game
    // When entering position 2,0
    // The Unit at this position is an archer
    assertThat(game.getUnitAt(archerPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeRedPlayersUnitAt2_0() {
    // Given a game
    // When entering position 2,0
    // Then the unit at this position is owned by player red
    assertThat(game.getUnitAt(archerPos).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldBeAbleToDifferentiateBetweenUnitOwner() {
    // Given a game
    // When there is units of both sides
    unit = new UnitImpl(Player.BLUE, GameConstants.LEGION);
    // Then the game can differentiate
    assertThat(unit.getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBeBluePlayersUnitAt3_2() {
    // Given a Game
    // When entering position 3,2
    // Then the unit at this location is blue
    assertThat(game.getUnitAt(legionPos).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBeAbleToDifferentiateUnits() {
    // Given a game
    // When there is multiple unit classes
    // Then the game can differentiate between them
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(archerPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBePlayerRedSettlerAt4_3() {
    // Given a game
    // When entering a position 4,3
    // Then the unit at the location is a red settler
    assertThat(game.getUnitAt(settlerPos).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(settlerPos).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldNotBeAbleToMoveUnitsOverMountains() {
    // Given a game
    // When trying to move a blue unit in blue players turn over a mountain
    Position mountain = new Position(2,2);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    // Then the move should fail
    assertThat(game.moveUnit(legionPos, mountain), is(false));
  }

  @Test
  public void shouldBeAbleToMoveUnitFrom3_2To4_2() {
    // Given a game
    // When moving a unit with a legal move
    doXEndOfTurn(1);
    newPos = new Position(4,2);
    // Then the move should succeed
    assertThat(game.getUnitAt(legionPos).getTypeString(),is(GameConstants.LEGION));
    assertThat(game.moveUnit(legionPos, newPos), is(true));
    assertThat(game.getUnitAt(newPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(legionPos), is(nullValue()));
  }

  @Test
  public void shouldBeImpossibleForRedToMoveBlueUnits() {
    // Given a game
    // When it is reds turn
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    newPos = new Position(4,2);
    // Then a move of a blue unit should fail
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.moveUnit(legionPos, newPos), is(false));
    assertThat(game.getUnitAt(newPos), is(nullValue()));
  }

  @Test
  public void shouldBeAbleToMoveRedUnitsInRedsTurn() {
    // Given a game
    // When it is reds turn
    newPos = new Position(2,1);
    // Then a move of a red unit should succeed
    assertThat(game.getUnitAt(archerPos).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.moveUnit(archerPos, newPos), is(true));
    assertThat(game.getUnitAt(newPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeAttackingUnitThatWins() {
    // Given a game
    // When a red unit moves to (and attacks) a blue unit
    // Then the red unit wins and occupies the tile
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
    game.moveUnit(archerPos,new Position(2,1));
    doXEndOfTurn(2);
    game.moveUnit(new Position(2,1),legionPos);
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeAbleToChooseArcherAsProductionInRedCity() {
    // Given a game
    // When red city chooses archer as production
    // Then the red city will produce an archer
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeAbleToChooseLegionAsProductionInRedCity() {
    // Given a game
    // When red city chooses legion as production
    // Then the red city will produce a legion
    game.changeProductionInCityAt(redCityPos, GameConstants.LEGION);
    assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.LEGION));
  }

  @Test
  public void shouldBeAbleToChooseSettlerAsProductionInRedCity() {
    // Given a game
    // When red city chooses settler as production
    // Then the red city will produce a settler
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);
    assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.SETTLER));
  }

  @Test
  public void shouldNotBeAbleToChooseBlueCityProductionInRedsTurn() {
    // Given a game
    // When red players turn chooses blue city production
    // Then red player will not be able to change production
    game.changeProductionInCityAt(blueCityPos, GameConstants.LEGION);
    assertThat(game.getCityAt(blueCityPos).getProduction(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeAbleForBlueToChooseProductionInTheirTurn() {
    // Given a game
    doXEndOfTurn(1);
    // When in blue players turn, and choose production is called
    // Then blue city should produce the production
    game.changeProductionInCityAt(blueCityPos, GameConstants.LEGION);
    assertThat(game.getCityAt(blueCityPos).getProduction(), is(GameConstants.LEGION));
  }

  @Test
  public void shouldBeAbleToProduceAUnitWhenTreasuryIs10() {
    // Given a game
    // When production hits 10
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    assertThat(game.getUnitAt(redCityPos), is(nullValue()));
    doXEndOfTurn(4);
    // Then a unit should be created
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBeSubtracting10FromTreasuryAfterCityProduceUnit() {
    // Given a game
    // When unit is produced by a city
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    assertThat(game.getUnitAt(redCityPos), is(nullValue()));
    doXEndOfTurn(4);
    // Then a unit should be created and treasury should be subtracted by 10
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(2));
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldBe10ProductionToProduceAnArcher() {
    // Given a Game
    // When an archer is produced by a city
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    doXEndOfTurn(4);
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.ARCHER));
    doXEndOfTurn(4);
    // then it should cost 10 production
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(4));
  }

  @Test
  public void shouldBe15ProductionToProduceALegion() {
    // Given a game
    // When a legion is produced by a city
    game.changeProductionInCityAt(redCityPos, GameConstants.LEGION);
    doXEndOfTurn(4);
    assertThat(game.getUnitAt(redCityPos), is(nullValue()));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(12));
    doXEndOfTurn(4);
    // Then the production cost is 15
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(9));
  }

  @Test
  public void shouldBe30ProductionToProduceASettler() {
    // Given a game
    // When a settler is produced by a city
    game.changeProductionInCityAt(redCityPos, GameConstants.SETTLER);
    doXEndOfTurn(4);
    assertThat(game.getUnitAt(redCityPos), is(nullValue()));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(12));
    doXEndOfTurn(6);
    // Then it should cost 30 production
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getCityAt(redCityPos).getTreasury(), is(0));
  }

  @Test
  public void shouldBeAbleToSeeAttackingStrengthOfArcher() {
    // Given a game
    // When an archer is created
    // Then it should have an attacking strength of 2
    assertThat(game.getUnitAt(archerPos).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(archerPos).getAttackingStrength(), is(2));
  }

  @Test
  public void shouldBeAbleToSeeDefensiveStrengthOfArcher() {
    // Given a game
    // When an archer is created
    // Then it should have a defensive strength of 3
    assertThat(game.getUnitAt(archerPos).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(archerPos).getDefensiveStrength(), is(3));
  }

  @Test
  public void shouldBeAbleToSeeAttackingStrengthOfLegion() {
    // Given a game
    // When a legion is created
    // Then it should have an attacking strength of 4
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(legionPos).getAttackingStrength(), is(4));
  }

  @Test
  public void shouldBeAbleToSeeDefensiveStrengthOfLegion() {
    // Given a game
    // When a legion is created
    // Then it should have a defensive strength of 2
    assertThat(game.getUnitAt(legionPos).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getUnitAt(legionPos).getDefensiveStrength(), is(2));
  }

  @Test
  public void shouldBeAbleToSeeAttackingStrengthOfSettler() {
    // Given a game
    // When a settler is created
    // Then it should have an attacking strength of 0
    assertThat(game.getUnitAt(settlerPos).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(settlerPos).getAttackingStrength(), is(0));
  }

  @Test
  public void shouldBeAbleToSeeDefensiveStrengthOfSettler() {
    // Given a game
    // When a settler is created
    // Then it should have a defensive strength of 2
    assertThat(game.getUnitAt(settlerPos).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(settlerPos).getDefensiveStrength(), is(3));
  }

  @Test
  public void shouldPlaceUnitAroundCityNeighborhoodIfLastSpotIsOccupied() {
    // Given a game
    // When a unit is produced
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    Position northOfRedCityPos = new Position(0,1);
    Position northEastOfRedCityPos = new Position(0,2);
    Position eastOfRedCityPos = new Position(1,2);
    Position southOfRedCityPos = new Position(2,1);
    Position southWestOfRedCityPos = new Position(2,0);
    Position northWestOfRedCityPos = new Position(0,0);

    // Then it should be created in or around the city
    assertThat(game.getUnitAt(redCityPos), is(nullValue()));
    doXEndOfTurn(4);
    assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(northOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(northEastOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(eastOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(southOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(southWestOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));

    doXEndOfTurn(4);
    assertThat(game.getUnitAt(northWestOfRedCityPos).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldNotBeAbleToMoveUnitsMoreThanOneTile() {
    // Given a game
    // When unit tries to move more than one tile at a time
    // Then unit do not move
    assertThat(game.moveUnit(archerPos, new Position(2,3)), is(false));
    assertThat(game.moveUnit(archerPos, new Position(4,0)), is(false));
  }

  @Test
  public void shouldNotBeAbleToMoveUnitsOverOceans() {
    // Given a game
    // When trying to move a unit over an ocean
    // Then the move should fail
    assertThat(game.moveUnit(archerPos, new Position(1, 0)), is(false));
  }

  @Test
  public void shouldBeAbleToChangeWorkForceInCityToProduction() {
    // Given a game
    // when changing work for focus in red city to production
    // Then it should change work force focus in that city to productionFocus
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.productionFocus);
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
  }

  @Test
  public void shouldBeAbleToChangeWorkForceInCityToGatheringFood() {
    // Given a game
    // When changing work force focus in city to gathering food
    // Then it should change it to foodFocus
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.foodFocus));
    // Testing for blue city as well
    doXEndOfTurn(1);
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.foodFocus);
    assertThat(game.getCityAt(blueCityPos).getWorkforceFocus(), is(GameConstants.foodFocus));
  }

  @Test
  public void shouldResetMoveCountForUnitsAfterRoundEnd() {
    // Given a game
    // When a round has ended
    newPos = new Position(3,0);
    assertThat(game.getUnitAt(archerPos).getMoveCount(), is(1));
    game.moveUnit(archerPos, newPos);
    assertThat(game.getUnitAt(newPos).getMoveCount(), is(0));
    doXEndOfTurn(2);
    // Then a units move count should be reset
    assertThat(game.getUnitAt(newPos).getMoveCount(), is(1));
  }

  @Test
  public void shouldNotBeAbleToMoveAUnitIfMoveCountIs0() {
    // Given a game
    // When a unit who has already moved is trying to move
    newPos = new Position(3,0);
    assertThat(game.getUnitAt(archerPos).getMoveCount(), is(1));
    game.moveUnit(archerPos, newPos);
    assertThat(game.getUnitAt(newPos).getMoveCount(), is(0));
    // Then the move should fail
    assertThat(game.moveUnit(newPos, new Position(4,0)), is(false));
    assertThat(game.getUnitAt(new Position(4,0)), is(nullValue()));
  }

  @Test
  public void shouldNotBeAbleToSpawnAUnitFromACityOnAMountainTile() {
    // Given a game
    // When a city produces a unit
    // Then it should not spawn on a mountain tile
    game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
    doXEndOfTurn(40);
    assertThat(game.getUnitAt(new Position(2,2)), is(nullValue()));
  }

  @Test
  public void shouldChangeBlueCityOwnerToRedIfCaptureByRedUnit() {
    // Given a game
    // When red unit move into blue city
    game.moveUnit(archerPos, new Position(3,0));
    doXEndOfTurn(2);
    game.moveUnit(new Position(3,0), new Position(4,0));
    doXEndOfTurn(2);
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
    game.moveUnit(new Position(4,0), blueCityPos);
    // Then red player captures the city and makes it owned by red player
    assertThat(game.getUnitAt(blueCityPos).getOwner(), is(Player.RED));
    assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldNotBeAbleToMoveUnitOnOtherUnitWithSameOwner() {
    // Given a game
    // When red player move red unit on another red unit
    game.moveUnit(archerPos, new Position(3,1));
    game.moveUnit(settlerPos, new Position(3,2));
    doXEndOfTurn(2);
    // Then move should fail
    assertThat(game.moveUnit(new Position(3,1), new Position(3,2)), is(false));
  }

  @Test
  public void shouldNotBeAbleToChangeOtherPlayerCityProduction() {
    // Given a game
    // When red player tries to change production in red turn, in blue city
    game.changeProductionInCityAt(blueCityPos, GameConstants.LEGION);
    // Then production in blue city should not be changed
    assertThat(game.getCityAt(blueCityPos).getProduction(), is(GameConstants.ARCHER));

    // blue player as well
    doXEndOfTurn(1);
    game.changeProductionInCityAt(redCityPos, GameConstants.LEGION);
    assertThat(game.getCityAt(redCityPos).getProduction(), is(GameConstants.ARCHER));
  }

  @Test
  public void shouldNotBeAbleToChangeOtherPlayerCityWorkForce() {
    // Given a game
    // When red player tries to change work force in red turn, in blue city
    game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.foodFocus);
    // Then work force in blue city should not be changed
    assertThat(game.getCityAt(blueCityPos).getWorkforceFocus(), is(nullValue()));

    // blue player as well
    doXEndOfTurn(1);
    game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.productionFocus);
    assertThat(game.getCityAt(redCityPos).getWorkforceFocus(), is(nullValue()));
  }

  /**
   * A helper method for passing turns to avoid code duplication,
   * and ease of use in test driven development
   * @param x is the amount of turns that should be ended
   */
  public void doXEndOfTurn(int x) {
    for (int i = 1; i <= x; i++) {
      game.endOfTurn();
    }
  }
}
