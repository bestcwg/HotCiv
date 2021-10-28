package hotciv.standard.strategies;

import hotciv.framework.*;

public interface CreateUnitStrategy {

    /**
     * A method for creating units for the game
     * @param cityPosition the position of the city producing the unit
     * @param city the concrete city object
     * @param game the game object
     */
    void createUnit(Position cityPosition, City city , Game game);
}
