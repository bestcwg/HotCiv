package hotciv.framework;

import java.util.HashMap;

/** Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.

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
public class GameConstants {
  // The age of the game
  public static final int WORLD_AGE = -4000;
  // The size of the world is set permanently to a 16x16 grid 
  public static final int WORLDSIZE = 16;
  // Valid unit types
  public static final String ARCHER    = "archer";
  public static final int[] ARCHER_ATTACK_AND_DEFENCE = {2,3};
  public static final String LEGION    = "legion";
  public static final int[] LEGION_ATTACK_AND_DEFENCE = {4,2};
  public static final String SETTLER   = "settler";
  public static final int[] SETTLER_ATTACK_AND_DEFENCE = {0,3};
  public static final String SANDWORM = "sandworm";
  public static final int[] SANDWORM_ATTACK_AND_DEFENCE = {0,10};
  public static final int MOVECOUNT = 1;
  // Valid terrain types
  public static final String PLAINS    = "plains";
  public static final int[] PLAINS_FOOD_AND_PRODUCTION = {3,0};
  public static final String OCEANS    = "ocean";
  public static final int[] OCEAN_FOOD_AND_PRODUCTION = {1,0};
  public static final String FOREST    = "forest";
  public static final int[] FOREST_FOOD_AND_PRODUCTION = {0,3};
  public static final String HILLS     = "hills";
  public static final int[] HILLS_FOOD_AND_PRODUCTION = {0,2};
  public static final String MOUNTAINS = "mountain";
  public static final int[] MOUNTAIN_FOOD_AND_PRODUCTION = {0,1};
  public static final String DESERT = "desert";
  public static final int[] DESERT_FOOD_AND_PRODUCTION = {0,0};
  // Valid production balance types
  public static final String productionFocus = "hammer";
  public static final String foodFocus = "apple";

  public static final HashMap<String, Integer> unitCost;

  static  {
    HashMap<String, Integer> cost = new HashMap<>();
    cost.put(ARCHER, 10);
    cost.put(LEGION, 15);
    cost.put(SETTLER, 30);
    cost.put(SANDWORM, 30);
    unitCost = cost;
  }
}
