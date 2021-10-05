package hotciv.variants.alphaCiv;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.WinnerStrategy;

import java.util.HashMap;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(Game game) {
        if (game.getAge() >= -3000) {
            return Player.RED;
        }
        return null;
    }

    @Override
    public void incrementBattlesWonBy(Player player) {

    }
}
