package hotciv.standard.strategies;

import hotciv.framework.*;

public interface LegalUnitsStrategy {
    /**
     * A method for checking the legality of a unit
     * @return return true if the unit is legal, and false if it's not
     */
    boolean isLegalUnit(String unitType);
}
