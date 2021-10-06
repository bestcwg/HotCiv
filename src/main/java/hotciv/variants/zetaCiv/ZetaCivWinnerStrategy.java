package hotciv.variants.zetaCiv;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.standard.WinnerStrategy;

public class ZetaCivWinnerStrategy implements WinnerStrategy {
    WinnerStrategy betaCivWinnerStrategy, epsilonCivWinnerStrategy, currentState;

    public ZetaCivWinnerStrategy(WinnerStrategy betaCivWinnerStrategy, WinnerStrategy epsilonCivWinnerStrategy) {
        this.betaCivWinnerStrategy = betaCivWinnerStrategy;
        this.epsilonCivWinnerStrategy = epsilonCivWinnerStrategy;
        this.currentState = null;
    }

    @Override
    public Player calculateWinner(Game game) {
        GameImpl gameImpl = (GameImpl) game;
        if(gameImpl.getRoundCounter() < 20) {
            currentState = betaCivWinnerStrategy;
        } else {
            currentState = epsilonCivWinnerStrategy;
        }
        return currentState.calculateWinner(game);
    }

    @Override
    public void incrementBattlesWonBy(Player player) {
        if (currentState == epsilonCivWinnerStrategy) {
            epsilonCivWinnerStrategy.incrementBattlesWonBy(player);
        }
    }
}
