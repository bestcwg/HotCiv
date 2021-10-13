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


public class ThetaCivCreateUnitStrategy implements CreateUnitStrategy {
    private CreateUnitStrategy alphaCivCreateUnitStrategy;

    public ThetaCivCreateUnitStrategy(AlphaCivCreateUnitStrategy alphaCivCreateUnitStrategy) {
        this.alphaCivCreateUnitStrategy = alphaCivCreateUnitStrategy;
    }

    @Override
    public void createUnit(Position cityPosition, City city, Game game) {
        switch (game.getCityAt(cityPosition).getProduction()) {
            case GameConstants.ARCHER:
            case GameConstants.LEGION:
            case GameConstants.SETTLER:
                alphaCivCreateUnitStrategy.createUnit(cityPosition, city, game);
            case GameConstants.SANDWORM:
                GameImpl game1 = (GameImpl) game;
                // loop though the neighborhood of a city using the provided utility class
                for (Position neighborhoodPosition : Utility.get8neighborhoodOf(cityPosition)) {
                    String concreteTile = game.getTileAt(neighborhoodPosition).getTypeString();
                    boolean isDesert = concreteTile.equals(GameConstants.DESERT);
                    boolean isDesertCity = game.getTileAt(cityPosition).getTypeString().equals(GameConstants.DESERT);

                    // if there is no unit at the city center place a unit here
                    if (game.getUnitAt(cityPosition) == null && isDesertCity) {
                        game1.getUnits().put(cityPosition, new UnitImpl(city.getOwner(), city.getProduction()));
                        break;

                        // Otherwise, run through the neighborhood to find a legal spot to place the unit
                    } else if (game.getUnitAt(neighborhoodPosition) == null && isDesert) {
                        game1.getUnits().put(neighborhoodPosition, new UnitImpl(city.getOwner(), city.getProduction()));
                        break;
                    }
                }
        }
    }
}
