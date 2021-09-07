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

  /** Fixture for alphaciv testing. */
  @BeforeEach
  public void setUp() {
    game = new GameImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    // TODO: reenable the assert below to get started...
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeOceanAt1_0() {
    Position p = new Position(1, 0);
    assertThat(game.getTileAt(p).getTypeString(), is("OCEAN"));
  }

  @Test
  public void shouldBeHillAt0_1() {
    Position p = new Position(0, 1);
    assertThat(game.getTileAt(p).getTypeString(), is("HILL"));
  }

  @Test
  public void shouldBeMountainAt2_2() {
    Position p = new Position(2, 2);
    assertThat(game.getTileAt(p).getTypeString(), is("MOUNTAIN"));
  }

  @Test
  public void shouldBePlainsThoughoutTheMap() {
    Position p = new Position(0,0);
    assertThat(game.getTileAt(p).getTypeString(), is("PLAIN"));

    Position b = new Position(6,6);
    assertThat(game.getTileAt(b).getTypeString(), is("PLAIN"));

    Position c = new Position(8,4);
    assertThat(game.getTileAt(c).getTypeString(), is("PLAIN"));

    Position d = new Position(16,16);
    assertThat(game.getTileAt(d).getTypeString(), is("PLAIN"));
  }
}
