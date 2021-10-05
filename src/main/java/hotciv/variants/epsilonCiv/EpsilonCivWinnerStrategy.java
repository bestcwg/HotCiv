package hotciv.variants.epsilonCiv;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.WinnerStrategy;

import java.util.HashMap;

public class EpsilonCivWinnerStrategy implements WinnerStrategy {
    @Override
    public Player calculateWinner(Game game) {
        return Player.RED;
    }
}
