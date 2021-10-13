package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;

public class ThetaCivFactory implements CivFactory {
    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return null;
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return null;
    }

    @Override
    public AttackingStrategy createAttackStrategy() {
        return null;
    }

    @Override
    public WinnerStrategy createWinnerStrategy() {
        return null;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return null;
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return null;
    }

    @Override
    public LegalUnitsStrategy createLegalUnitStrategy() {
        return null;
    }

    @Override
    public CreateUnitStrategy createCreateUnitStrategy() {
        return null;
    }
}
