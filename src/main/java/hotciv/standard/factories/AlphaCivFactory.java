package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;

public class AlphaCivFactory implements CivFactory {
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
        return new AlphaCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return new AlphaCivMoveStrategy();
    }
}
