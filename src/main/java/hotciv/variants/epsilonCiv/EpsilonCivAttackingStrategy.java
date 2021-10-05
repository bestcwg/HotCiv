package hotciv.variants.epsilonCiv;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.AttackingStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.utility.Utility2;

import java.util.HashMap;

public class EpsilonCivAttackingStrategy implements AttackingStrategy {

    @Override
    public void calculateBattleWinner(Position from, Position to, Game game) {
        GameImpl actualGame = (GameImpl) game;
        HashMap<Position, City> cities = ((GameImpl) game).getCities();
        HashMap<Position, Unit> units = ((GameImpl) game).getUnits();


        boolean isAttackingUnit = units.containsKey(to);
        if (isAttackingUnit) {
            int totalAttackingUnitStrength = getTotalAttackingStrength(from, game);
            int totalDefensiveUnitStrength = getTotalDefensiveStrength(to, game);
            if (totalAttackingUnitStrength >= totalDefensiveUnitStrength) {
                units.remove(to);
            } else {
                units.remove(from);
            }
        }

        boolean isCityAtToTile = cities.containsKey(to) && game.getCityAt(to).getOwner() != game.getUnitAt(from).getOwner();
        if (isCityAtToTile) {
            CityImpl city = (CityImpl) game.getCityAt(to);
            city.changeOwner(game.getUnitAt(from).getOwner());
            // Checks if the player in turn owns all the cities in the game
            actualGame.checkForWinner(game);
        }
    }

    @Override
    public int getTotalAttackingStrength(Position position, Game game) {
        int strength = game.getUnitAt(position).getAttackingStrength();

        strength *= Utility2.getTerrainFactor(game, position);
        return strength;
    }

    @Override
    public int getTotalDefensiveStrength(Position position, Game game) {
        int strength = game.getUnitAt(position).getDefensiveStrength();

        strength *= Utility2.getTerrainFactor(game, position);
        return strength;
    }

}
