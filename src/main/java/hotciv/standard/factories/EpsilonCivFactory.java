package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.*;
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
