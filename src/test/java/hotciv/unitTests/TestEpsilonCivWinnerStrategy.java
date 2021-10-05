package hotciv.unitTests;

import hotciv.standard.WinnerStrategy;
import hotciv.variants.epsilonCiv.EpsilonCivWinnerStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCivWinnerStrategy {
    private WinnerStrategy winnerStrategy;
    @BeforeEach
    public void setUp() {
        winnerStrategy = new EpsilonCivWinnerStrategy();
    }

    @Test
    public void testStuff() {
        assertThat(null, is(nullValue()));
    }
}
