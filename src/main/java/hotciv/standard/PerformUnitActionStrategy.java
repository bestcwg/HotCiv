package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;

public interface PerformUnitActionStrategy {
    /**
     * A method to do an action for a unit, depending on unitType
     * @param unit of which unit to perform action on
     */
    void action(Position position, Unit unit, HashMap<Position,City> cities, HashMap<Position,Unit> units);
}
