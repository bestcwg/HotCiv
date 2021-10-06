package hotciv.standard;

import hotciv.standard.strategies.*;

public interface CivFactory {

    /**
     * A factory method for setting up unit action strategies
     * @return the unitAction strategy for the chosen factory
     */
    PerformUnitActionStrategy getUnitActionStrategy();

    /**
     * A factory method for setting up age strategy
     * @return the age strategy for the chosen factory
     */
    AgeStrategy getAgeStrategy();

    /**
     * A factory method for setting up unit attack strategies
     * @return the attack strategy for the chosen factory
     */
    AttackingStrategy getAttackStrategy();

    /**
     * A factory method for setting up winner strategies
     * @return the winner strategy for the chosen factory
     */
    WinnerStrategy getWinnerStrategy();

    /**
     * A factory method for setting up world layout strategies
     * @return the world layout strategy for the chosen factory
     */
    WorldLayoutStrategy getWorldLayoutStrategy();
}
