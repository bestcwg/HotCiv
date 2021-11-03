package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.DeltaCivAdaptor;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.deltaCiv.DeltaCivWorldLayoutStrategy;

public class DeltaCivAdapterFactory implements CivFactory {
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
        return new DeltaCivAdaptor();
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
