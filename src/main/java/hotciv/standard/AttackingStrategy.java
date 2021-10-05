package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface AttackingStrategy {
    void calculateBattleWinner(Position from, Position to, Game game);
    int getDefendingUnitStrenght(Position from, Game game);
}
