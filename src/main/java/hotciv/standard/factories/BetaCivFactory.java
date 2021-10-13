package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.betaCiv.BetaCivAgeStrategy;
import hotciv.variants.betaCiv.BetaCivWinnerStrategy;

public class BetaCivFactory implements CivFactory {
    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return new AlphaCivPerformUnitActionStrategy();
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new BetaCivAgeStrategy();
    }

    @Override
    public AttackingStrategy createAttackStrategy() {
        return new AlphaCivAttackingStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return new BetaCivWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayoutStrategy();
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return new AlphaCivMoveStrategy();
    }

    @Override
    public LegalUnitsStrategy createLegalUnitStrategy() {
        return new AlphaCivLegalUnitStrategy();
    }

    @Override
    public CreateUnitStrategy createCreateUnitStrategy() {
        return new AlphaCivCreateUnitStrategy();
    }
}
