package hotciv.variants.thetaCiv;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.strategies.CreateUnitStrategy;
import hotciv.framework.GameConstants;
import hotciv.utility.Utility;
import hotciv.variants.alphaCiv.AlphaCivCreateUnitStrategy;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;

public class ThetaCivCreateUnitStrategy implements CreateUnitStrategy {
    private CreateUnitStrategy alphaCivCreateUnitStrategy;

    public ThetaCivCreateUnitStrategy(AlphaCivCreateUnitStrategy alphaCivCreateUnitStrategy) {
        this.alphaCivCreateUnitStrategy = alphaCivCreateUnitStrategy;
    }

    @Override
    public void createUnit(Position cityPosition, City city, Game game) {
        switch (game.getCityAt(cityPosition).getProduction()) {
            case GameConstants.SANDWORM:
                alphaCivCreateUnitStrategy.createUnit(cityPosition, city, game);
            default:
                alphaCivCreateUnitStrategy.createUnit(cityPosition, city, game);
        }
    }
}
