package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;

public interface PerformUnitActionStrategy {
    /**
     * An interface for controlling the actions units can do in the game, implements concrete strategies
     * for actions
     * @param position the position of the unit
     * @param unit the unit itself
     * @param cities A hashmap containing all the cities in the game
     * @param units A hashmap containing all the units in the game
     */
    void action(Position position, Game game);
}
