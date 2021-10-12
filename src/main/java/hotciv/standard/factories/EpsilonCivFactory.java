package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;

public class EpsilonCivFactory implements CivFactory {
    private final RollStrategy rollStrategy;

    public EpsilonCivFactory(RollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return new AlphaCivPerformUnitActionStrategy();
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new AlphaCivAgeStrategy();
    }

    @Override
    public AttackingStrategy createAttackStrategy() {
        return new EpsilonCivAttackingStrategy(rollStrategy);
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new EpsilonCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}
