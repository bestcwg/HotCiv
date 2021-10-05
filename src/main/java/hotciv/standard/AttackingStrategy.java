package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface AttackingStrategy {
    void calculateBattleWinner(Position from, Position to, Game game);
    int getTotalAttackingStrength(Position position, Game game);
    int getTotalDefensiveStrength(Position position, Game game);
}
