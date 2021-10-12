package hotciv.standard.strategies;


import hotciv.framework.Game;
import hotciv.framework.Player;

public interface WinnerStrategy {
    /**
     * returns the winner calculated with the given implementation of the strategy
     * Requisite the current age of the game
     * @param game the actual game
     * @return the Player Enum of the winning player, if no winner returns null
     */
    Player calculateWinner(Game game);
    void incrementBattlesWonBy(Player player);
    int checkCountOfBattlesWon(Player player);
}
