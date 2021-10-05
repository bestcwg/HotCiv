package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface AttackingStrategy {
    /**
     * A method for calculating the winner of a unit battle
     * @param from the attacking unit
     * @param to the defending unit
     * @param game the actual game
     * @return
     */
    boolean calculateBattleWinner(Position from, Position to, Game game);

    /**
     * A helper method that adds the supporting factors to the unit attacking strength
     * @param position the position of the unit
     * @param game the game object
     * @return returns the total value of the units attacking strength
     */
    int getTotalAttackingStrength(Position position, Game game);

    /**
     * A helper method that adds the supporting factors to the units defensive strength
     * @param position the position of the unit
     * @param game the game object
     * @return return the total value of the units defensive strength
     */
    int getTotalDefensiveStrength(Position position, Game game);
}
