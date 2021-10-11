package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.alphaCiv.AlphaCivAttackingStrategy;
import hotciv.variants.alphaCiv.AlphaCivWinnerStrategy;
import hotciv.variants.alphaCiv.AlphaCivWorldLayoutStrategy;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;

public class GammaCivFactory implements CivFactory {
    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return new GammaCivPerformUnitActionStrategy();
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
        return new AlphaCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }
}
