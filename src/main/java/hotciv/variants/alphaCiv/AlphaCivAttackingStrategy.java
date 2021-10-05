package hotciv.variants.alphaCiv;

import hotciv.framework.*;
import hotciv.standard.*;
import java.util.HashMap;

public class AlphaCivAttackingStrategy implements AttackingStrategy {
    @Override
    public boolean calculateBattleWinner(Position from, Position to, Game game) {
        HashMap<Position, Unit> units = ((GameImpl) game).getUnits();

        // If attacking another unit, that unit is removed
        boolean isAttackingUnit = units.containsKey(to);
        if (isAttackingUnit) {
            units.remove(to);
        }
        return true;
    }

    @Override
    public int getTotalAttackingStrength(Position position, Game game) {
        return 0;
    }

    @Override
    public int getTotalDefensiveStrength(Position position, Game game) {
        return 0;
    }
}

