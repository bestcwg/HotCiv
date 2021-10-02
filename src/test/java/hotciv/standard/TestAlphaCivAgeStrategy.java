package hotciv.standard;

import hotciv.framework.*;

import hotciv.variants.alphaCiv.AlphaCivAgeStrategy;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestAlphaCivAgeStrategy {
    private AgeStrategy ageStrategy;
    @BeforeEach
    public void setUp() {
        ageStrategy = new AlphaCivAgeStrategy();
    }

    @Test
    public void shouldReturn100ByGivingAge3500() {
        assertThat(ageStrategy.calculateAge(3500),is(100));
    }

    @Test
    public void shouldReturn100ByGivingAge3100() {
        assertThat(ageStrategy.calculateAge(3100),is(100));
    }
}
