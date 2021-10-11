package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.zetaCiv.ZetaCivWinnerStrategy;

public class ZetaCivFactory implements CivFactory {
    private final WinnerStrategy epsilonCivWinnerStrategy;
    private final WinnerStrategy betaCivWinnerStrategy;

    public ZetaCivFactory(WinnerStrategy betaCivWinnerStrategy, WinnerStrategy epsilonCivWinnerStrategy) {
        this.betaCivWinnerStrategy = betaCivWinnerStrategy;
        this.epsilonCivWinnerStrategy = epsilonCivWinnerStrategy;
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
        return new AlphaCivAttackingStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new ZetaCivWinnerStrategy(betaCivWinnerStrategy, epsilonCivWinnerStrategy);
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}