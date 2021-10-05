package hotciv.variants.epsilonCiv;

import hotciv.framework.*;
import hotciv.standard.AttackingStrategy;
import hotciv.standard.GameImpl;
import hotciv.standard.RollStrategy;
import hotciv.utility.Utility2;

import java.util.HashMap;
import java.util.Random;

public class EpsilonCivAttackingStrategy implements AttackingStrategy {
    RollStrategy rollStrategy;
    public EpsilonCivAttackingStrategy(RollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    @Override
    public boolean calculateBattleWinner(Position from, Position to, Game game) {
        HashMap<Position, Unit> units = ((GameImpl) game).getUnits();

        boolean isAttackingUnit = units.containsKey(to);
        if (isAttackingUnit) {
            int totalAttackingUnitStrength = getTotalAttackingStrength(from, game);
            int totalDefensiveUnitStrength = getTotalDefensiveStrength(to, game);
            if (totalAttackingUnitStrength > totalDefensiveUnitStrength) {
                units.remove(to);
                return true;
            } else {
                units.remove(from);
            }
        }
        return false;
    }

    @Override
    public int getTotalAttackingStrength(Position position, Game game) {

        Player owner = game.getUnitAt(position).getOwner();
        int strength = game.getUnitAt(position).getAttackingStrength();
        strength += Utility2.getFriendlySupport(game, position, owner);
        strength *= Utility2.getTerrainFactor(game, position);
        strength *= rollStrategy.roll();
        return strength;
    }

    @Override
    public int getTotalDefensiveStrength(Position position, Game game) {
        Player owner = game.getUnitAt(position).getOwner();
        int strength = game.getUnitAt(position).getDefensiveStrength();
        strength += Utility2.getFriendlySupport(game, position, owner);
        strength *= Utility2.getTerrainFactor(game, position);
        strength *= rollStrategy.roll();
        return strength;
    }
}
