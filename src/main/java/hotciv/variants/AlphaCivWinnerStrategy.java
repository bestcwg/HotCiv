package hotciv.variants;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(int age) {
        if (age >= -3000) {
            return Player.RED;
        }
        return null;
    }
}
