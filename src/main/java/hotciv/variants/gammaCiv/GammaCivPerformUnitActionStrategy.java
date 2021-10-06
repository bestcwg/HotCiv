package hotciv.variants.gammaCiv;

import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.strategies.PerformUnitActionStrategy;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

public class GammaCivPerformUnitActionStrategy implements PerformUnitActionStrategy {

    @Override
    public void action(Position position, Game game) {
        UnitImpl unit = (UnitImpl) game.getUnitAt(position);
        GameImpl actualGame = (GameImpl) game;

        switch (unit.getTypeString()) {
            // Archer action is fortify, that makes the archer defensive strength double, but cannot move
            // Settler action is settle, create a new city and remove the settler from the game
            case GameConstants.ARCHER:
                unit.fortify();
                break;
            case GameConstants.SETTLER:
                actualGame.createCity(position, new CityImpl(unit.getOwner()));
                actualGame.removeUnit(position);
                break;
        }
    }
}
