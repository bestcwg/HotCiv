package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;
import hotciv.variants.thetaCiv.*;

public class ThetaCivFactory implements CivFactory {
    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return new ThetaCivPerformUnitActionStrategy(new GammaCivPerformUnitActionStrategy());
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
        return new ThetaCivWorldLayoutStrategy();
    }

    @Override
    public MoveStrategy createMoveStrategy() {
        return new ThetaCivMoveStrategy();
    }

    @Override
    public LegalUnitsStrategy createLegalUnitStrategy() {
        return new ThetaCivLegalUnitStrategy();
    }

    @Override
    public CreateUnitStrategy createCreateUnitStrategy() {
        return new ThetaCivCreateUnitStrategy();
    }
}
