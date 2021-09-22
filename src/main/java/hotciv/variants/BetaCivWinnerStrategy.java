package hotciv.variants;

import hotciv.framework.Player;
import hotciv.standard.WinnerStrategy;

public class BetaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(int age) {
        return Player.RED;
    }
}
