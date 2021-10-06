package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.utility.FixedRollStrategy;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;

public class EpsilonCivFactory implements CivFactory {
    private RollStrategy rollStrategy;

    public EpsilonCivFactory(RollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    @Override
    public PerformUnitActionStrategy getUnitActionStrategy() {
        return new AlphaCivPerformUnitActionStrategy();
    }

    @Override
    public AgeStrategy getAgeStrategy() {
        return new AlphaCivAgeStrategy();
    }

    @Override
    public AttackingStrategy getAttackStrategy() {
        return new EpsilonCivAttackingStrategy(rollStrategy);
    }

    @Override
    public WinnerStrategy getWinnerStrategy() {
        return new EpsilonCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy getWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}
