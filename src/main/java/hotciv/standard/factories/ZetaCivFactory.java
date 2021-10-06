package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivPerformUnitActionStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.zetaCiv.ZetaCivWinnerStrategy;

public class ZetaCivFactory implements CivFactory {
    private WinnerStrategy epsilonCivWinnerStrategy;
    private WinnerStrategy betaCivWinnerStrategy;

    public ZetaCivFactory(WinnerStrategy betaCivWinnerStrategy, WinnerStrategy epsilonCivWinnerStrategy) {
        this.betaCivWinnerStrategy = betaCivWinnerStrategy;
        this.epsilonCivWinnerStrategy = epsilonCivWinnerStrategy;
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
        return new AlphaCivAttackingStrategy();
    }

    @Override
    public WinnerStrategy getWinnerStrategy() {
        return new ZetaCivWinnerStrategy(betaCivWinnerStrategy, epsilonCivWinnerStrategy);
    }

    @Override
    public WorldLayoutStrategy getWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}