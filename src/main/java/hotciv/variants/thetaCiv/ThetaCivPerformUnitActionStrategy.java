package hotciv.variants.thetaCiv;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.strategies.PerformUnitActionStrategy;
import hotciv.utility.Utility;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;

public class ThetaCivPerformUnitActionStrategy implements PerformUnitActionStrategy {
    private final GammaCivPerformUnitActionStrategy gammaCiv;

    public ThetaCivPerformUnitActionStrategy(GammaCivPerformUnitActionStrategy gammaCivPerformUnitActionStrategy) {
        this.gammaCiv = gammaCivPerformUnitActionStrategy;
    }

    @Override
    public void action(Position position, Game game) {
        UnitImpl unit = (UnitImpl) game.getUnitAt(position);
        GameImpl actualGame = (GameImpl) game;

        switch (unit.getTypeString()) {
            case GameConstants.ARCHER:
            case GameConstants.SETTLER:
                gammaCiv.action(position, game);
                break;
            case GameConstants.SANDWORM:
                for (Position neighborhoodPosition : Utility.get8neighborhoodOf(position)) {
                    if (game.getUnitAt(neighborhoodPosition) != null){
                        boolean isEnemy = game.getUnitAt(neighborhoodPosition).getOwner() != unit.getOwner();
                        if (isEnemy) {
                            actualGame.getUnits().remove(neighborhoodPosition);
                        }
                    }
                }
                break;
        }
    }
}
