package hotciv.variants.alphaCiv;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.standard.strategies.CreateUnitStrategy;
import hotciv.utility.Utility;

public class AlphaCivCreateUnitStrategy implements CreateUnitStrategy {
    @Override
    public void createUnit(Position cityPosition, City city, Game game) {
        GameImpl game1 = (GameImpl) game;
        // loop though the neighborhood of a city using the provided utility class
        for (Position neighborhoodPosition : Utility.get8neighborhoodOf(cityPosition)) {
            String concreteTile = game.getTileAt(neighborhoodPosition).getTypeString();
            boolean isNotImpassableTile = !concreteTile.equals(GameConstants.MOUNTAINS) &&
                                          !concreteTile.equals(GameConstants.OCEANS);

            // if there is no unit at the city center place a unit here
            if (game.getUnitAt(cityPosition) == null) {
                game1.getUnits().put(cityPosition, new UnitImpl(city.getOwner(), city.getProduction()));
                if (game1.getGameObserver() != null) {
                    game1.getGameObserver().worldChangedAt(cityPosition);
                }
                break;

                // Otherwise, run through the neighborhood to find a legal spot to place the unit
            } else if (game.getUnitAt(neighborhoodPosition) == null && isNotImpassableTile) {
                game1.getUnits().put(neighborhoodPosition, new UnitImpl(city.getOwner(), city.getProduction()));
                if (game1.getGameObserver() != null) {
                    game1.getGameObserver().worldChangedAt(neighborhoodPosition);
                }
                break;
            }
        }
    }
}
