package hotciv.variants;

import hotciv.standard.CityImpl;
import hotciv.standard.PerformUnitActionStrategy;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class GammaCivPerformUnitActionStrategy implements PerformUnitActionStrategy {

    @Override
    public void action(Position position, Unit unit, HashMap<Position, City> cities, HashMap<Position,Unit> units) {
        UnitImpl u = (UnitImpl) unit;

        switch (unit.getTypeString()) {
            // Archer action is fortify, that makes the archer defensive strength double, but cannot move
            // Settler action is settle, create a new city and remove the settler from the game
            case GameConstants.ARCHER:
                u.fortify();
                break;
            case GameConstants.SETTLER:
                cities.put(position, new CityImpl(u.getOwner()));
                units.remove(position);
                break;
        }
    }
}
