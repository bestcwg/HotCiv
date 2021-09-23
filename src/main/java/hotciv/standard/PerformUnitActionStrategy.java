package hotciv.standard;

import hotciv.framework.Unit;

public interface PerformUnitActionStrategy {
    /**
     * A method to do an action for a unit, depending on unitType
     * @param unit of which unit to perform action on
     */
    void action(Unit unit);
}
