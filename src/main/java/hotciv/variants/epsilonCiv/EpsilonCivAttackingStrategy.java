package hotciv.variants.epsilonCiv;

import hotciv.framework.*;
import hotciv.standard.strategies.AttackingStrategy;
import hotciv.standard.strategies.RollStrategy;
import hotciv.utility.Utility2;

public class EpsilonCivAttackingStrategy implements AttackingStrategy {
    RollStrategy rollStrategy;
    public EpsilonCivAttackingStrategy(RollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    @Override
    public boolean calculateBattleWinner(Position from, Position to, Game game) {
        int totalAttackingUnitStrength = getTotalAttackingStrength(from, game);
        int totalDefensiveUnitStrength = getTotalDefensiveStrength(to, game);

        if (totalAttackingUnitStrength > totalDefensiveUnitStrength) {
            return true;
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
