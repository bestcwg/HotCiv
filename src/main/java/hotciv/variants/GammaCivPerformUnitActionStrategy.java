package hotciv.variants;

import hotciv.standard.CityImpl;
import hotciv.standard.PerformUnitActionStrategy;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class GammaCivPerformUnitActionStrategy implements PerformUnitActionStrategy {

    @Override
    public void action(Position position, Unit unit, HashMap<Position, City> cities) {
        UnitImpl u = (UnitImpl) unit;

        switch (unit.getTypeString()) {
            case GameConstants.ARCHER:
                u.fortify();
                break;
            case GameConstants.SETTLER:
                cities.put(position, new CityImpl(u.getOwner()));
                break;
        }
    }
}
