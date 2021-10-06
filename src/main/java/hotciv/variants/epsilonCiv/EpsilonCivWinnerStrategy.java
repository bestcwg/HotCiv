package hotciv.variants.epsilonCiv;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.strategies.WinnerStrategy;

public class EpsilonCivWinnerStrategy implements WinnerStrategy {
    private int redPlayerBattlesWon;
    private int bluePlayerBattlesWon;
    @Override
    public Player calculateWinner(Game game) {
        if (redPlayerBattlesWon == 3) {
            return Player.RED;
        }
        if (bluePlayerBattlesWon == 3) {
            return Player.BLUE;
        }
        return null;
    }

    @Override
    public void incrementBattlesWonBy(Player player) {
        switch (player) {
            case RED:
                redPlayerBattlesWon++;
                break;
            case BLUE:
                bluePlayerBattlesWon++;
                break;
        }
    }
}
