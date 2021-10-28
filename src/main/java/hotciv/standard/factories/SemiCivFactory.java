package hotciv.standard.factories;

import hotciv.standard.CivFactory;
import hotciv.standard.strategies.*;
import hotciv.variants.alphaCiv.AlphaCivCreateUnitStrategy;
import hotciv.variants.alphaCiv.AlphaCivLegalUnitStrategy;
import hotciv.variants.alphaCiv.AlphaCivMoveStrategy;
import hotciv.variants.betaCiv.BetaCivAgeStrategy;
import hotciv.variants.deltaCiv.DeltaCivWorldLayoutStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivAttackingStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import hotciv.variants.gammaCiv.GammaCivPerformUnitActionStrategy;

public class SemiCivFactory implements CivFactory {
    private final RollStrategy rollStrategy;

    public SemiCivFactory(RollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    @Override
    public PerformUnitActionStrategy createUnitActionStrategy() {
        return new GammaCivPerformUnitActionStrategy();
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new BetaCivAgeStrategy();
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
        return new DeltaCivWorldLayoutStrategy();
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
