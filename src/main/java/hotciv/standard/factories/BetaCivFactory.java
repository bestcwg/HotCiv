package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.betaCiv.BetaCivAgeStrategy;
import hotciv.variants.betaCiv.BetaCivWinnerStrategy;

public class BetaCivFactory implements CivFactory {
    @Override
    public PerformUnitActionStrategy getUnitActionStrategy() {
        return new AlphaCivPerformUnitActionStrategy();
    }

    @Override
    public AgeStrategy getAgeStrategy() {
        return new BetaCivAgeStrategy();
    }

    @Override
    public AttackingStrategy getAttackStrategy() {
        return new AlphaCivAttackingStrategy();
    }

    @Override
    public WinnerStrategy getWinnerStrategy() {
        return new BetaCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy getWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}
