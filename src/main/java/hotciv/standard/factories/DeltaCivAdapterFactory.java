package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.DeltaCivAdaptor;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.deltaCiv.DeltaCivWorldLayoutStrategy;

public class DeltaCivAdapterFactory implements CivFactory {
    private CivFactory deltaCiv = new DeltaCivFactory();

    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return deltaCiv.createUnitActionStrategy();
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return deltaCiv.createAgeStrategy();
    }

    @Override
    public AttackingStrategy createAttackStrategy() {
        return deltaCiv.createAttackStrategy();
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return deltaCiv.createWinnerStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DeltaCivAdaptor();
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return deltaCiv.createMoveStrategy();
    }

    @Override
    public LegalUnitsStrategy createLegalUnitStrategy() {
        return deltaCiv.createLegalUnitStrategy();
    }

    @Override
    public CreateUnitStrategy createCreateUnitStrategy() {
        return deltaCiv.createCreateUnitStrategy();
    }
}
