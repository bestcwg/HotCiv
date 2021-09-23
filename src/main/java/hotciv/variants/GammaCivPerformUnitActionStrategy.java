package hotciv.variants;

import hotciv.standard.PerformUnitActionStrategy;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

public class GammaCivPerformUnitActionStrategy implements PerformUnitActionStrategy {
    @Override
    public void action(Unit unit) {
        UnitImpl u = (UnitImpl) unit;
        u.fortify();
    }
}
