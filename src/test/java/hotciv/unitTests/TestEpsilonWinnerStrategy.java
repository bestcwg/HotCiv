package hotciv.unitTests;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.EpsilonCivFactory;
import hotciv.standard.strategies.AgeStrategy;
import hotciv.standard.strategies.WinnerStrategy;
import hotciv.utility.FixedRollStrategy;
import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonWinnerStrategy {
    private WinnerStrategy winnerStrategy;
    @BeforeEach
    public void setUp() {
        winnerStrategy = new EpsilonCivWinnerStrategy();
    }

    @Test
    public void shouldWinAfterWinning3Battles() {
        winnerStrategy.incrementBattlesWonBy(Player.BLUE);
        winnerStrategy.incrementBattlesWonBy(Player.BLUE);
        winnerStrategy.incrementBattlesWonBy(Player.BLUE);
        assertThat(winnerStrategy.calculateWinner(new GameImpl(new EpsilonCivFactory(new FixedRollStrategy()))), is(Player.BLUE));
    }
}
