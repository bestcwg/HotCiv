package hotciv.standard;

import hotciv.standard.strategies.*;

public interface CivFactory {
    PerformUnitActionStrategy getUnitActionStrategy();
    AgeStrategy getAgeStrategy();
    AttackingStrategy getAttackStrategy();
    WinnerStrategy getWinnerStrategy();
    WorldLayoutStrategy getWorldLayoutStrategy();
}
