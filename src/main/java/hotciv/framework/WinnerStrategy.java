package hotciv.framework;

public interface WinnerStrategy {
    /**
     * returns the winner calculated with the given implementation of the strategy
     * Requisite the current age of the game
     * @param age the age of the game
     * @return the Player Enum of the winning player, if no winner returns null
     */
    Player calculateWinner(int age);
}
