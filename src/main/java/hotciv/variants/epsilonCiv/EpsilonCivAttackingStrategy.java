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
        int unitAttackStrength = game.getUnitAt(position).getAttackingStrength();

        return calculateTotalStrength(unitAttackStrength, game, position);
    }

    @Override
    public int getTotalDefensiveStrength(Position position, Game game) {
        int unitDefendStrength = game.getUnitAt(position).getDefensiveStrength();

        return calculateTotalStrength(unitDefendStrength, game, position);
    }

    private int calculateTotalStrength(int strength, Game game, Position position) {
        Player player = game.getUnitAt(position).getOwner();

        strength += Utility2.getFriendlySupport(game, position, player);
        strength *= Utility2.getTerrainFactor(game, position);
        strength *= rollStrategy.roll();

        return strength;
    }
}
