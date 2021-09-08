package hotciv.standard;

import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

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
  private Position p;

  /** Fixture for alphaciv testing. */
  @BeforeEach
  public void setUp() {
    game = new GameImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeOceanAt1_0() {
    p = new Position(1, 0);
    assertThat(game.getTileAt(p).getTypeString(), is(GameConstants.OCEANS));
  }

  @Test
  public void shouldBeHillAt0_1() {
    p = new Position(0, 1);
    assertThat(game.getTileAt(p).getTypeString(), is(GameConstants.HILLS));
  }

  @Test
  public void shouldBeMountainAt2_2() {
    p = new Position(2, 2);
    assertThat(game.getTileAt(p).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  @Test
  public void shouldBePlainsThoughoutTheMap() {
    Position p = new Position(0,0);
    assertThat(game.getTileAt(p).getTypeString(), is(GameConstants.PLAINS));

    Position b = new Position(6,6);
    assertThat(game.getTileAt(b).getTypeString(), is(GameConstants.PLAINS));

    Position c = new Position(8,4);
    assertThat(game.getTileAt(c).getTypeString(), is(GameConstants.PLAINS));

    Position d = new Position(15,15);
    assertThat(game.getTileAt(d).getTypeString(), is(GameConstants.PLAINS));
  }

  @Test
  public void shouldBeCityAt1_1() {
    p = new Position(1,1);
    assertThat(game.getCityAt(p), is(notNullValue()));
  }
  @Test
  public void shouldBeRedCityAt1_1() {
    p = new Position(1,1);
    assertThat(game.getCityAt(p), is(notNullValue()));
    assertThat(game.getCityAt(p).getOwner(), is(Player.RED));
  }
  @Test
  public void shouldBeBlueCityAt1_4() {
    p = new Position(1,4);
    assertThat(game.getCityAt(p), is(notNullValue()));
    assertThat(game.getCityAt(p).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBePopulationOf1InCityAlways() {
    City city = new CityImpl(Player.RED);

    assertThat(city.getSize(),is(1));
  }

  @Test
  public void shouldBeBlueTurnAfterRedTurn() {
    doXEndOfTurn(1);
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  @Test
  public void shouldBeYear4000BCAtGameStart() {
    assertThat(game.getAge(), is(-4000));
  }

  @Test
  public void shouldIncrementTimeBy100AfterRoundEnd() {
    assertThat(game.getAge(), is(-4000));
    doXEndOfTurn(2);
    assertThat(game.getAge(), is(-3900));
  }

  @Test
  public void shouldBeRedAsWinnerInYear3000BC() {
    assertThat(game.getAge(), is(-4000));
    doXEndOfTurn(20);
    assertThat(game.getAge(), is(-3000));
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void shouldBeUnitAt0_2() {
    p = new Position(0,2);

    assertThat(game.getUnitAt(p), is(notNullValue()));
  }







  public void doXEndOfTurn(int x) {
    for (int i = 1; i <= x; i++) {
      game.endOfTurn();
    }
  }
}
