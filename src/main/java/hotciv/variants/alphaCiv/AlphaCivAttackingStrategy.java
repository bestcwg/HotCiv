package hotciv.variants.alphaCiv;

import hotciv.framework.*;
import hotciv.standard.*;


import java.util.HashMap;

public class AlphaCivAttackingStrategy implements AttackingStrategy {
    @Override
    public void calculateBattleWinner(Position from, Position to, Game game) {
        GameImpl actualGame = (GameImpl) game;
        HashMap<Position, City> cities = ((GameImpl) game).getCities();
        HashMap<Position, Unit> units = ((GameImpl) game).getUnits();


        // If attacking another unit, that unit is removed
        boolean isAttackingUnit = units.containsKey(to);
        if (isAttackingUnit) {
            units.remove(to);
        }
        // If unit move into city, that is not the player in turns, the city is lost to the player in turn
        boolean isCityAtToTile = cities.containsKey(to) && game.getCityAt(to).getOwner() != game.getUnitAt(from).getOwner();
        if (isCityAtToTile) {
            CityImpl city = (CityImpl) game.getCityAt(to);
            city.changeOwner(game.getUnitAt(from).getOwner());
            // Checks if the player in turn owns all the cities in the game
            actualGame.checkForWinner(game);
        }
    }
}

