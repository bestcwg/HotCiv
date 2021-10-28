package hotciv.standard;

import hotciv.standard.strategies.*;

public interface CivFactory {

    /**
     * A factory method for setting up unit action strategies
     * @return the unitAction strategy for the chosen factory
     */
    PerformUnitActionStrategy createUnitActionStrategy();

    /**
     * A factory method for setting up age strategy
     * @return the age strategy for the chosen factory
     */
    AgeStrategy createAgeStrategy();

    /**
     * A factory method for setting up unit attack strategies
     * @return the attack strategy for the chosen factory
     */
    AttackingStrategy createAttackStrategy();

    /**
     * A factory method for setting up winner strategies
     * @return the winner strategy for the chosen factory
     */
    WinnerStrategy createWinnerStrategy();

    /**
     * A factory method for setting up world layout strategies
     * @return the world layout strategy for the chosen factory
     */
    WorldLayoutStrategy createWorldLayoutStrategy();

    /**
     * A factory method for setting the move stategy for the game
     * @return the move strategy for the chosen factory
     */
    MoveStrategy createMoveStrategy();

    /**
     * A factory method for setting the legal units of a game
     * @return the legal units' strategy for the chosen factory
     */
    LegalUnitsStrategy createLegalUnitStrategy();

    /**
     * A factory method for setting the create unit strategy of the game
     * @return the create unit strategy for the chosen factory
     */
    CreateUnitStrategy createCreateUnitStrategy();
}
