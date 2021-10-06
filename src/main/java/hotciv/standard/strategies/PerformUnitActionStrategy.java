package hotciv.standard.strategies;

import hotciv.framework.*;


public interface PerformUnitActionStrategy {
    /**
     * An interface for controlling the actions units can do in the game, implements concrete strategies
     * for actions
     * @param position the position of the unit
     * @param game the actual game
     */
    void action(Position position, Game game);
}
